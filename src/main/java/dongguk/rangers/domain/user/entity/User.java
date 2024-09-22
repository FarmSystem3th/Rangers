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
    @Column(nullable = true)
    private Role role;

    @Column(nullable = true)
    // 보호자는 아이디, 피부양자는 코드
    private String codeId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String birthday;

    @Column(nullable = true)
    private String birthyear;

    @Builder
    public User(String email, String nickname, String birthday, String birthyear) {
        this.email = email;
        this.nickname = nickname;
        this.birthday = birthday;
        this.birthyear = birthyear;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateBirthday(String birthday, String birthyear) {
        this.birthday = birthday;
        this.birthyear = birthyear;
    }

    // 보호자 역할일 때의 연결들
    @OneToMany(mappedBy = "guard")
    private List<Connect> guardConnections;

    // 피부양자 역할일 때의 연결들
    @OneToMany(mappedBy = "dependant")
    private List<Connect> dependantConnections;


}