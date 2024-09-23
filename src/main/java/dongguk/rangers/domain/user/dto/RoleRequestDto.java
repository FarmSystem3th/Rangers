package dongguk.rangers.domain.user.dto;

import dongguk.rangers.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleRequestDto {
    private Role role;
}

