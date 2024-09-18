package dongguk.rangers.domain.path.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DangerDTO {

    @Data
    @Builder
    public static class DangerResponseDTO {
        private Long zoneId;
        private String zoneName;
        private double latitude;
        private double longitude;
    }
}
