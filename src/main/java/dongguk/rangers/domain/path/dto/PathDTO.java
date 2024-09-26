package dongguk.rangers.domain.path.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PathDTO {

    @Data
    @Builder
    public static class PathRequestDTO {
        private Long userId;
        private String start;
        private String end;
        private double startLatitude;
        private double startLongitude;
        private double endLatitude;
        private double endLongitude;
    }

    @Data
    @Builder
    public static class DangerCntRequestDTO {
        private Long pathId;
        private int dangerCnt;  // 위험 구역 개수
    }

    @Data
    @Builder
    public static class PathResponseDTO {
        private Long pathId;
        private Long userId;
        private String start;
        private String end;
        private String state;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int dangerCnt;
    }

    @Data
    @Builder
    public static class DangerCntResponseDTO {
        private Long pathId;
        private int dangerCnt;  // 위험 구역 개수
    }

    @Data
    @Builder
    public static class GuardianMainResponseDTO {
        private String guardianNickname; // 보호자 닉네임 추가
        private String dependantNickname;   // 피부양자 닉네임
        private String start;      // 경로 시작점
        private String end;        // 경로 도착점
        private int dangerCnt;     // 위험 구역 개수
        private LocalDateTime startTime;  // 경로 시작 시간
        private LocalDateTime endTime;    // 경로 종료 시간
    }
}
