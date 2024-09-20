package dongguk.rangers.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    // 보호자는 아이디, 피부양자는 코드
    private String codeId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    // 카카오 로그인을 사용할 때 필요한 Builder
    @Builder
    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }


    // 보호자 역할일 때의 연결들
    @OneToMany(mappedBy = "guard")
    private List<Connect> guardConnections;

    // 피부양자 역할일 때의 연결들
    @OneToMany(mappedBy = "dependant")
    private List<Connect> dependantConnections;


}