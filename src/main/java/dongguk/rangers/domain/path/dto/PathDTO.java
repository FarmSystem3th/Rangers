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
    }

    @Data
    @Builder
    public static class PathResponseDTO {
        private Long pathId;
        private Long userId;
        private String start;
        private String end;
        private String state;
        private LocalDateTime timestamp;
    }
}