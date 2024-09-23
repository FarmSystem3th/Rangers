package dongguk.rangers.domain.user;

import dongguk.rangers.domain.user.dto.*;
import dongguk.rangers.domain.user.entity.Role;
import dongguk.rangers.domain.user.kakao.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    // JWT 토큰을 Authorization 헤더에서 추출하는 메서드
    private static String getToken(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new RuntimeException("JWT Token is missing");
        }
        return auth.substring(7);
    }

    // 역할 등록
    @PostMapping("/role")
    public ResponseEntity<Void> registerRoleAndGenerateCode(@RequestHeader("Authorization") String auth,
                                                            @RequestBody RoleRequestDto roleRequestDto) {
        String token = getToken(auth);
        userService.updateRole(token, roleRequestDto.getRole());
        if (roleRequestDto.getRole() == Role.DEPENDANT) { // 피부양자라면 코드 생성
            userService.generateCodeForDependant(token);
        }
        return ResponseEntity.ok().build();
    }

    // 보호자 피부양자 연결
    @PostMapping("/connect")
    public ResponseEntity<Void> connectGuardToDependant(@RequestHeader("Authorization") String auth,
                                                        @RequestBody ConnectRequestDto connectRequestDto) {
        String token = getToken(auth);
        userService.connectDependantToGuard(token, connectRequestDto.getDependantCode());
        return ResponseEntity.ok().build();
    }

    // 내 정보 불러오기
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> viewMyPage(@RequestHeader("Authorization") String authorizationHeader) {
        String token = getToken(authorizationHeader);
        MyPageResponseDto userProfile = userService.getUserProfile(token);
        return ResponseEntity.ok(userProfile);
    }

    // 닉네임 수정
    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickname(@RequestHeader("Authorization") String auth,
                                               @RequestBody NicknameRequestDto nicknameRequestDto) {
        String token = getToken(auth);
        userService.updateNickname(token, nicknameRequestDto.getNickname());
        return ResponseEntity.ok().build();
    }

    // 이메일 수정
    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(@RequestHeader("Authorization") String auth,
                                            @RequestBody EmailRequestDto emailRequestDto) {
        String token = getToken(auth);
        userService.updateEmail(token, emailRequestDto.getNewEmail());
        return ResponseEntity.ok().build();
    }

}
