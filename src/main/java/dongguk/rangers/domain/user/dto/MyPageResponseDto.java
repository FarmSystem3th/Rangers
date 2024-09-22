package dongguk.rangers.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPageResponseDto {
    private String nickname;
    private Enum role;
    private String email;
    private String birthday;
    private String birthyear;
}
