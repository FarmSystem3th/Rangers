package dongguk.rangers.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BirthdayRequestDto {
    private String birthday;
    private String birthyear;
}
