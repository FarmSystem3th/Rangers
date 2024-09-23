package dongguk.rangers.domain.user.kakao;

import dongguk.rangers.domain.user.UserService;
import dongguk.rangers.domain.user.kakao.response.LoginSuccessResponse;
import dongguk.rangers.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Kakao Login API", description = "Kakao 로그인 관련 API")
public class KakaoController {

    private final UserService userService;
    private final KakaoService kakaoService;

    // TODO: 테스트용, 나중에 지워야 함
    @Value("${kakao.get_code_path}")
    private String getCodePath;
    @Value("${kakao.client_id}")
    private String client_id;
    @Value(("${kakao.redirect_uri}"))
    private String redirect_uri;

    @Operation(summary = "카카오 로그인 페이지 테스트용", description = "카카오 로그인 페이지로 이동합니다.")
    @GetMapping("/kakao/login")
    public String loginPage(Model model) {
        String location = getCodePath + client_id + "&redirect_uri=" + redirect_uri;
        model.addAttribute("location", location);
        return "login";
    }

    @Operation(summary = "카카오 로그인 콜백", description = "카카오 로그인 후 서버로 인증 코드를 전달받고, 로그인 처리를 진행합니다.")
    @GetMapping("/kakao/callback")
    public ResponseEntity<LoginSuccessResponse> callback(
            @Parameter(description = "카카오에서 전달한 인증 코드", required = true)
            @RequestParam("code") String code) {
        try {
            LoginSuccessResponse userResponse = kakaoService.kakaoLogin(code);
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
