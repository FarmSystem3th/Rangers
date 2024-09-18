package dongguk.rangers.domain.path.converter;

import dongguk.rangers.domain.path.dto.SafeDTO.SafeResponseDTO;
import dongguk.rangers.domain.path.entity.Safe;

public class SafeConverter {
    public static SafeResponseDTO toSafeResponseDTO(Safe safe) {
        return SafeResponseDTO.builder()
                .zoneId(safe.getZoneId())
                .zoneName(safe.getZoneName())
                .latitude(safe.getLatitude())
                .longitude(safe.getLongitude())
                .build();
    }
}
