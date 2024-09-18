package dongguk.rangers.domain.path.controller;

import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;
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

    @Operation(summary = "경로 저장", description = "사용자가 입력한 출발 장소와 도착 장소를 저장합니다.")
    @PostMapping("/save")
    public ResponseEntity<PathResponseDTO> savePath(@RequestBody @Validated PathRequestDTO pathRequestDTO) {
        PathResponseDTO responseDTO = pathService.savePath(pathRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "위험 구역 조회", description = "모든 위험 구역 정보를 조회합니다.")
    @GetMapping("/zones")
    public ResponseEntity<List<DangerResponseDTO>> getAllDangerousZones() {
        List<DangerResponseDTO> zones = pathService.getAllDangerousZones();
        return ResponseEntity.ok(zones);
    }
}