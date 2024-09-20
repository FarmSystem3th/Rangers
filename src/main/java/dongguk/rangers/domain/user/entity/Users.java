package dongguk.rangers.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String nickname;

    // 카카오 로그인을 사용할 때 필요한 Builder
    @Builder
    public Users(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

}