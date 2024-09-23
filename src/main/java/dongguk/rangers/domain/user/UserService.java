package dongguk.rangers.domain.user;

import dongguk.rangers.domain.user.dto.MyPageResponseDto;
import dongguk.rangers.domain.user.entity.Connect;
import dongguk.rangers.domain.user.entity.Role;
import dongguk.rangers.domain.user.entity.User;
import dongguk.rangers.domain.user.kakao.jwt.JwtTokenProvider;
import dongguk.rangers.domain.user.repository.ConnectRepository;
import dongguk.rangers.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConnectRepository connectRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private Long findUserIdByJwt(String token) {
        return jwtTokenProvider.getUserIdFromJwt(token);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    // 역할 등록
    @Transactional
    public void updateRole(String token, Role role) {
        Long userId = findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateRole(role);
    }


    // 프로필 정보 조회
    public MyPageResponseDto getUserProfile(String token) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new MyPageResponseDto(
                user.getNickname(),
                user.getRole(),
                user.getEmail()
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

    @Transactional
    public void generateCodeForDependant(String token) {
        Long userId = findUserIdByJwt(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 피부양자일 경우에만 코드 생성
        if (user.getRole() == Role.DEPENDANT) {
            String randomCode = UUID.randomUUID().toString();
            user.updateCodeId(randomCode);
        }
    }

    @Transactional
    public void connectDependantToGuard(String token, String dependantCode) {
        Long guardId = findUserIdByJwt(token);
        User guard = userRepository.findById(guardId)
                .orElseThrow(() -> new IllegalArgumentException("Guard not found"));

        // 입력된 코드로 피부양자 찾기
        User dependant = userRepository.findByCodeId(dependantCode)
                .orElseThrow(() -> new IllegalArgumentException("Dependant not found"));

        // Connect 엔티티로 연결 생성
        Connect connection = new Connect(null, guard, dependant);
        connectRepository.save(connection);

        // 보호자의 codeId도 피부양자의 codeId로 설정
        guard.updateCodeId(dependant.getCodeId());
    }

}