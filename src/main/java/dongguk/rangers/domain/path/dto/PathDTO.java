package dongguk.rangers.domain.path.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathRequestDto {
    private Long userId;
    private String start;
    private String end;
}
