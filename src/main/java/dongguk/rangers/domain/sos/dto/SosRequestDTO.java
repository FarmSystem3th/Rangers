package dongguk.rangers.domain.sos.dto;

import lombok.Data;

@Data
public class SosRequestDTO {
    private Long dependantId;         // 피부양자 아이디
    private String currentLocation;   // 피부양자의 현재 위치
    private String trackingLink;      // 실시간 위치 확인 링크
}
