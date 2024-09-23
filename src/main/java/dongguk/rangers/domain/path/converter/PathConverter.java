package dongguk.rangers.domain.path.converter;

import dongguk.rangers.domain.path.entity.Path;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.GuardianMainResponseDTO;
import dongguk.rangers.domain.path.entity.PathState;
import dongguk.rangers.domain.path.dto.PathDTO.DangerCntResponseDTO;
import dongguk.rangers.domain.user.entity.User;

import java.time.LocalDateTime;

public class PathConverter {

    public static Path toPath(PathRequestDTO pathRequestDTO) {
        return Path.builder()
                .userId(pathRequestDTO.getUserId())
                .start(pathRequestDTO.getStart())
                .end(pathRequestDTO.getEnd())
                .startTime(LocalDateTime.now())  // 현재 시간으로 설정
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
                .startTime(path.getStartTime())
                .endTime(path.getEndTime())  // 도착 시간이 없으면 null로 반환
                .build();
    }

    public static DangerCntResponseDTO toDangerCountResponseDTO(Path path) {
        return DangerCntResponseDTO.builder()
                .pathId(path.getPathId())
                .dangerCnt(path.getDangerCnt())
                .build();
    }

    public static GuardianMainResponseDTO toGuardianMainResponse(Path path, User guardian, User dependant) {
        return GuardianMainResponseDTO.builder()
                .guardianNickname(guardian.getNickname()) // 보호자 닉네임 설정
                .dependantNickname(dependant.getNickname())   // 피부양자 닉네임 설정
                .start(path.getStart())         // 경로 시작점
                .end(path.getEnd())             // 경로 도착점
                .dangerCnt(path.getDangerCnt()) // 위험 구역 개수
                .startTime(path.getStartTime()) // 경로 시작 시간 포함
                .endTime(path.getEndTime())     // 경로 종료 시간 포함
                .build();
    }
}
