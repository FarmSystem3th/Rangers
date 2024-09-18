package dongguk.rangers.domain.path.dto;

import lombok.Builder;
import lombok.Data;

public class SafeDTO {
    @Data
    @Builder
    public static class SafeResponseDTO {
        private Long zoneId;
        private String zoneName;
        private double latitude;
        private double longitude;
    }
}
