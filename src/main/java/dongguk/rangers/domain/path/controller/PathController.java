package dongguk.rangers.domain.path.controller;

import dongguk.rangers.domain.path.dto.PathDTO.GuardianMainResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.GuardianIdRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.DangerCntResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.DangerCntRequestDTO;
import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;
import dongguk.rangers.domain.path.dto.SafeDTO.SafeResponseDTO;
import dongguk.rangers.domain.path.service.PathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/path")
@Tag(name = "길찾기 관련 컨트롤러")
public class PathController {

    private final PathService pathService;

    @Operation(summary = "길찾기 시작", description = "사용자가 입력한 출발 장소와 도착 장소를 저장합니다.")
    @PostMapping("/save")
    public ResponseEntity<PathResponseDTO> savePath(@RequestBody @Validated PathRequestDTO pathRequestDTO) {
        PathResponseDTO responseDTO = pathService.savePath(pathRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "위험 구역 조회", description = "모든 위험 구역 정보를 조회합니다.")
    @GetMapping("/danger")
    public ResponseEntity<List<DangerResponseDTO>> getAllDangerousZones() {
        List<DangerResponseDTO> zones = pathService.getAllDangerousZones();
        return ResponseEntity.ok(zones);
    }

    @Operation(summary = "안전 시설 조회", description = "모든 안전 시설 정보를 조회합니다.")
    @GetMapping("/safe")
    public ResponseEntity<List<SafeResponseDTO>> getAllSafeZones() {
        List<SafeResponseDTO> zones = pathService.getAllSafeZones();
        return ResponseEntity.ok(zones);
    }

    @Operation(summary = "길찾기 종료", description = "안내를 완료로 설정하고 도착 시간을 기록합니다.")
    @PatchMapping("/complete/{pathId}")
    public ResponseEntity<PathResponseDTO> completePath(@PathVariable Long pathId) {
        PathResponseDTO responseDTO = pathService.completePath(pathId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "위험 구역 개수 업데이트", description = "경로에 대해 위험 구역 개수를 업데이트합니다.")
    @PatchMapping("/update/danger-cnt")
    public ResponseEntity<DangerCntResponseDTO> updateDangerCount(@RequestBody @Validated DangerCntRequestDTO dangerCntRequestDTO) {
        DangerCntResponseDTO responseDTO = pathService.updateDangerCnt(dangerCntRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "보호자 메인 화면 조회", description = "보호자와 연결된 피부양자의 경로 정보들을 조회합니다.")
    @GetMapping("/board")
    public ResponseEntity<List<GuardianMainResponseDTO>> getGuardianMainBoard(@RequestBody GuardianIdRequestDTO guardianIdRequestDTO) {
        List<GuardianMainResponseDTO> response = pathService.getGuardianMainBoard(guardianIdRequestDTO.getUserId());
        return ResponseEntity.ok(response);
    }
}
