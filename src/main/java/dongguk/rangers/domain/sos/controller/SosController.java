package dongguk.rangers.domain.sos.controller;

import dongguk.rangers.domain.sos.dto.SosRequestDTO;
import dongguk.rangers.domain.sos.service.SosService;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.service.PathService;
import dongguk.rangers.domain.user.entity.User;
import dongguk.rangers.domain.user.repository.ConnectRepository;
import dongguk.rangers.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/sos")
@Tag(name = "SOS 알림 관련 컨트롤러", description = "피부양자가 SOS 신호시 현위치 및 관련 정보를 보호자에게 알림")
public class SosController {

    private final SosService sosService;
    private final PathService pathService;
    private final ConnectRepository connectRepository;
    private final UserRepository userRepository;

    @Operation(summary = "SOS 알림 보내기", description = "피부양자의 위치를 보호자에게 이메일로 보냅니다.")
    @PostMapping
    public ResponseEntity<String> sendSosAlert(@RequestBody @Validated SosRequestDTO sosRequestDTO) {
        try {
            // 프론트에서 받은 dependantId로 경로 정보 가져오기
            Long dependantId = sosRequestDTO.getDependantId();
            PathResponseDTO pathResponseDTO = pathService.getPathByUserId(dependantId);

            // 피부양자의 정보를 조회
            Optional<User> dependantOpt = userRepository.findById(dependantId);
            if (dependantOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피부양자 정보를 찾을 수 없습니다.");
            }
            User dependant = dependantOpt.get();


            // 보호자 정보를 조회
            var connectOpt = connectRepository.findByDependant(dependant);
            if (connectOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("보호자 정보를 찾을 수 없습니다.");
            }
            User guardian = connectOpt.get().getGuard();

            String guardianEmail = guardian.getEmail();
            String dependantName = dependant.getNickname();

            // 경로 정보에서 출발 위치와 도착 위치 가져오기
            String startLocation = pathResponseDTO.getStart();
            String endLocation = pathResponseDTO.getEnd();

            // 이메일 제목
            String subject = String.format("🚨%s님이 위험 신호를 보냈습니다.🚨", dependantName);

            // 이메일 본문
            String body = String.format(
                    "%s님이 %s에서 %s로 가는 도중 현재 %s에서 위험 신호를 보냈습니다.\n실시간 위치를 확인하시겠습니까? %s",
                    dependantName,
                    startLocation,
                    endLocation,
                    sosRequestDTO.getCurrentLocation(),
                    sosRequestDTO.getTrackingLink()
            );

            // 보호자 이메일로 위험 알림 전송
            sosService.sendDangerAlert(guardianEmail, subject, body);

            return ResponseEntity.status(HttpStatus.OK).body("알림이 성공적으로 전송되었습니다.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알림 전송 실패: " + e.getMessage());
        }
    }
}
