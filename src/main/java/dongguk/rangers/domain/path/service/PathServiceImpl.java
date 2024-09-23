package dongguk.rangers.domain.path.service;


import dongguk.rangers.domain.path.converter.DangerConverter;
import dongguk.rangers.domain.path.converter.PathConverter;
import dongguk.rangers.domain.path.converter.SafeConverter;
import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;
import dongguk.rangers.domain.path.dto.SafeDTO.SafeResponseDTO;
import dongguk.rangers.domain.path.entity.Danger;
import dongguk.rangers.domain.path.entity.Path;
import dongguk.rangers.domain.path.entity.PathState;
import dongguk.rangers.domain.path.entity.Safe;
import dongguk.rangers.domain.path.repository.DangerRepository;
import dongguk.rangers.domain.path.repository.PathRepository;
import dongguk.rangers.domain.path.repository.SafeRepository;
import dongguk.rangers.domain.user.entity.Connect;
import dongguk.rangers.domain.user.entity.User;
import dongguk.rangers.domain.user.repository.ConnectRepository;
import dongguk.rangers.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.DangerCntResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.DangerCntRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.GuardianMainResponseDTO;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PathServiceImpl implements PathService {

    private final PathRepository pathRepository;
    private final DangerRepository dangerRepository;
    private final SafeRepository safeRepository;
    private final UserRepository userRepository;
    private final ConnectRepository connectRepository;

    public PathResponseDTO savePath(PathRequestDTO pathRequestDTO) {
        Path path = PathConverter.toPath(pathRequestDTO);
        path = pathRepository.save(path);

        return PathConverter.toSavePathResponse(path);
    }

    // userId로 경로를 조회하는 메소드 구현
    @Override
    public PathResponseDTO getPathByUserId(Long userId) {
        // 가장 최신의 경로를 가져옴
        Path path = pathRepository.findTop1ByUserIdOrderByStartTimeDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 경로를 찾을 수 없습니다."));

        return PathConverter.toSavePathResponse(path);
    }

    public List<DangerResponseDTO> getAllDangerousZones() {
        List<Danger> zones = dangerRepository.findAll();
        return zones.stream()
                .map(DangerConverter::toDangerResponseDTO)
                .collect(Collectors.toList());
    }

    public List<SafeResponseDTO> getAllSafeZones() {
        List<Safe> zones = safeRepository.findAll();
        return zones.stream()
                .map(SafeConverter::toSafeResponseDTO)
                .collect(Collectors.toList());
    }

    public PathResponseDTO completePath(Long pathId) {
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new RuntimeException("Path not found"));

        // 도착 시간 기록 및 상태 변경
        path.updateState(PathState.COMPLETED, LocalDateTime.now());
        Path updatedPath = pathRepository.save(path);

        return PathConverter.toSavePathResponse(updatedPath);
    }

    public DangerCntResponseDTO updateDangerCnt(DangerCntRequestDTO dangerCntRequestDTO) {
        Path path = pathRepository.findById(dangerCntRequestDTO.getPathId())
                .orElseThrow(() -> new IllegalArgumentException("경로를 찾을 수 없습니다."));

        path.updateDangerCnt(dangerCntRequestDTO.getDangerCnt());
        pathRepository.save(path);

        return PathConverter.toDangerCountResponseDTO(path);
    }
    @Override
    public List<GuardianMainResponseDTO> getGuardianMainBoard(Long userId) {
        // 보호자 정보를 가져옴
        User guardian = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("보호자를 찾을 수 없습니다."));

        // Connect 테이블에서 보호자와 연결된 피부양자 정보를 조회
        List<Connect> connections = connectRepository.findByGuard(guardian);

        if (connections.isEmpty()) {
            throw new IllegalArgumentException("연결된 피부양자를 찾을 수 없습니다.");
        }

        // 피부양자들과 각각의 경로 정보를 조회하여 DTO로 변환
        return connections.stream()
                .flatMap(connection -> {
                    User dependant = connection.getDependant();
                    // 피부양자의 경로 정보를 조회
                    List<Path> paths = pathRepository.findByUserId(dependant.getUserId());

                    return paths.stream()
                            .map(path -> PathConverter.toGuardianMainResponse(path, guardian, dependant));
                })
                .collect(Collectors.toList());
    }

}
