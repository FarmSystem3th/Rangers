package dongguk.rangers.domain.user;

import dongguk.rangers.domain.user.dto.MyPageResponseDto;
import dongguk.rangers.domain.user.entity.User;
import dongguk.rangers.domain.user.kakao.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private Long findUserIdByJwt(String token) {
        return jwtTokenProvider.getUserIdFromJwt(token);
    }

    public MyPageResponseDto getUserProfile(String token) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new MyPageResponseDto(
                user.getNickname(),
                user.getRole(),
                user.getEmail(),
                user.getBirthday(),
                user.getBirthyear()
        );
    }

    // 닉네임 수정하기
    @Transactional
    public void updateNickname(String token, String newNickname) {
        Long userId = findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateNickName(newNickname);
    }

    // 이메일 수정하기
    @Transactional
    public void updateEmail(String token, String newEmail) {
        Long userId = findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateEmail(newEmail);
    }

    // 생년월일 수정하기
    @Transactional
    public void updateBirthday(String token, String birthday, String birthyear) {
        Long userId = findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateBirthday(birthday, birthyear);
    }

}