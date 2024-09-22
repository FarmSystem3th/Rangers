package dongguk.rangers.domain.user;

import dongguk.rangers.domain.user.dto.MyPageResponseDto;
import dongguk.rangers.domain.user.dto.NicknameRequestDto;
import dongguk.rangers.domain.user.dto.EmailRequestDto;
import dongguk.rangers.domain.user.dto.BirthdayRequestDto;
import dongguk.rangers.domain.user.kakao.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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

    // 나의 마이페이지 보기
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> viewMyPage(@RequestHeader("Authorization") String authorizationHeader) {
        String token = getToken(authorizationHeader);
        MyPageResponseDto userProfile = userService.getUserProfile(token);
        return ResponseEntity.ok(userProfile);
    }

    // 닉네임 수정
    @PutMapping("/users/nickname")
    public ResponseEntity<Void> updateNickname(@RequestHeader("Authorization") String auth,
                                               @RequestBody NicknameRequestDto nicknameRequestDto) {
        String token = getToken(auth);
        userService.updateNickname(token, nicknameRequestDto.getNickname());
        return ResponseEntity.ok().build();
    }

    // 이메일 수정
    @PutMapping("/users/email")
    public ResponseEntity<Void> updateEmail(@RequestHeader("Authorization") String auth,
                                            @RequestBody EmailRequestDto emailRequestDto) {
        String token = getToken(auth);
        userService.updateEmail(token, emailRequestDto.getNewEmail());
        return ResponseEntity.ok().build();
    }

    // 생년월일 수정
    @PutMapping("/users/birthday")
    public ResponseEntity<Void> updateBirthday(@RequestHeader("Authorization") String auth,
                                               @RequestBody BirthdayRequestDto birthdayRequestDto) {
        String token = getToken(auth);
        userService.updateBirthday(token, birthdayRequestDto.getBirthday(), birthdayRequestDto.getBirthyear());
        return ResponseEntity.ok().build();
    }
}
