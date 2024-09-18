package dongguk.rangers.domain.path.converter;

import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;
import dongguk.rangers.domain.path.entity.Danger;

public class DangerConverter {
    public static DangerResponseDTO toDangerResponseDTO(Danger danger) {
        return DangerResponseDTO.builder()
                .zoneId(danger.getZoneId())
                .zoneName(danger.getZoneName())
                .latitude(danger.getLatitude())
                .longitude(danger.getLongitude())
                .build();
    }
}
