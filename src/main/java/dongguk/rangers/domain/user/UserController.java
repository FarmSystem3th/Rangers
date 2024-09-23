package dongguk.rangers.domain.user;

import dongguk.rangers.domain.user.dto.*;
import dongguk.rangers.domain.user.entity.Role;
import dongguk.rangers.domain.user.kakao.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "User API", description = "User 관련 API")
public class UserController {

    private final UserService userService;

    // JWT 토큰을 Authorization 헤더에서 추출하는 메서드
    private static String getToken(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new RuntimeException("JWT Token is missing");
        }
        return auth.substring(7);
    }

    @Operation(summary = "역할 등록", description = "사용자의 역할을 등록하고 피부양자인 경우 코드를 생성합니다.")
    @PostMapping("/role")
    public ResponseEntity<Void> registerRoleAndGenerateCode(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader("Authorization") String auth,
            @RequestBody RoleRequestDto roleRequestDto) {
        String token = getToken(auth);
        userService.updateRole(token, roleRequestDto.getRole());
        if (roleRequestDto.getRole() == Role.DEPENDANT) { // 피부양자라면 코드 생성
            userService.generateCodeForDependant(token);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "보호자-피부양자 연결", description = "보호자와 피부양자 계정을 연결합니다.")
    @PostMapping("/connect")
    public ResponseEntity<Void> connectGuardToDependant(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader("Authorization") String auth,
            @RequestBody ConnectRequestDto connectRequestDto) {
        String token = getToken(auth);
        userService.connectDependantToGuard(token, connectRequestDto.getDependantCode());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내 정보 불러오기", description = "사용자의 프로필 정보를 가져옵니다.")
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> viewMyPage(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = getToken(authorizationHeader);
        MyPageResponseDto userProfile = userService.getUserProfile(token);
        return ResponseEntity.ok(userProfile);
    }

    @Operation(summary = "닉네임 수정", description = "사용자의 닉네임을 수정합니다.")
    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickname(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader("Authorization") String auth,
            @RequestBody NicknameRequestDto nicknameRequestDto) {
        String token = getToken(auth);
        userService. updateNickname(token, nicknameRequestDto.getNickname());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 수정", description = "사용자의 이메일을 수정합니다.")
    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(
            @Parameter(description = "JWT 인증 토큰", required = true)
            @RequestHeader("Authorization") String auth,
            @RequestBody EmailRequestDto emailRequestDto) {
        String token = getToken(auth);
        userService.updateEmail(token, emailRequestDto.getNewEmail());
        return ResponseEntity.ok().build();
    }

}
