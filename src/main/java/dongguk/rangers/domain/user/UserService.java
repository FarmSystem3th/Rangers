package dongguk.rangers.domain.user;

import dongguk.rangers.domain.user.entity.Users;
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

    // 닉네임 수정하기
    @Transactional
    public void newNickName(String token, String newNickname) {
        Long userId = findUserIdByJwt(token);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.updateNickName(newNickname);
    }

}