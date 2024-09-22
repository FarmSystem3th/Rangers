package dongguk.rangers.domain.user.kakao;

import dongguk.rangers.domain.user.entity.User;
import dongguk.rangers.domain.user.kakao.jwt.JwtTokenProvider;
import dongguk.rangers.domain.user.UserRepository;
import dongguk.rangers.domain.user.kakao.jwt.UserAuthentication;
import dongguk.rangers.domain.user.kakao.response.KakaoTokenResponseDto;
import dongguk.rangers.domain.user.kakao.response.KakaoUserInfoResponseDto;
import dongguk.rangers.domain.user.kakao.response.LoginSuccessResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @Value("${kakao.client_secret}")
    private String client_secret;
    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    @Transactional
    public LoginSuccessResponse kakaoLogin(String code) {
        String accessToken = getAccessToken(code);
        KakaoUserInfoResponseDto userInfo = getUserInfo(accessToken);
        Long userId = getUserByEmail(userInfo.getKakaoAccount().email).getUserId();
        return getTokenByUserId(userId, accessToken);
    }

    public String getAccessToken(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", client_id)
                        .queryParam("redirect_uri", redirect_uri)
                        .queryParam("code", code)
                        .queryParam("client_secret", client_secret)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    //log.error("액세스 토큰 발급 시 4xx 에러 발생: {}", clientResponse.statusCode());
                    return clientResponse.createException().flatMap(Mono::error);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    //log.error("액세스 토큰 발급 시 5xx 에러 발생: {}", clientResponse.statusCode());
                    return clientResponse.createException().flatMap(Mono::error);
                })
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        assert kakaoTokenResponseDto != null;
        return kakaoTokenResponseDto.getAccessToken();
    }



    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST).get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        if (!isDuplicateEmail(userInfo.getKakaoAccount().email)) {
            User newUser = User.builder()
                    .email(userInfo.getKakaoAccount().email)
                    .nickname(userInfo.getKakaoAccount().getProfile().nickName)
                    .build();
            userRepository.save(newUser);
        }

        return userInfo;
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일이 없습니다."));
    }

    public LoginSuccessResponse getTokenByUserId(Long userId, String kakaoAccessToken) {
        UserAuthentication userAuthentication = new UserAuthentication(
                userId,
                null,
                null,
                kakaoAccessToken);

        String accessToken = jwtTokenProvider.generateToken(userAuthentication);

        User users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not Found User"));


        return new LoginSuccessResponse(userId, accessToken, kakaoAccessToken);
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}