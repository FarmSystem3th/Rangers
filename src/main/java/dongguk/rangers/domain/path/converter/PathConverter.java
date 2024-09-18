package dongguk.rangers.domain.path.converter;

import dongguk.rangers.domain.path.entity.Path;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.entity.PathState;

import java.time.LocalDateTime;

public class PathConverter {

    public static Path toPath(PathRequestDTO pathRequestDTO) {
        return Path.builder()
                .userId(pathRequestDTO.getUserId())
                .start(pathRequestDTO.getStart())
                .end(pathRequestDTO.getEnd())
                .timestamp(LocalDateTime.now())  // 현재 시간으로 설정
                .state(PathState.PROGRESS)       // 기본 상태를 PROGRESS로 설정
                .build();
    }

    public static PathResponseDTO toSavePathResponse(Path path) {
        return PathResponseDTO.builder()
                .pathId(path.getPathId())
                .userId(path.getUserId())
                .start(path.getStart())
                .end(path.getEnd())
                .state(path.getState().name())
                .timestamp(path.getTimestamp())
                .build();
    }
}
