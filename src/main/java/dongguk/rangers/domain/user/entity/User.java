package dongguk.rangers.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(nullable = true, unique = true)
    private String codeId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;



    @Builder
    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateRole(Role role) { this.role = role; }

    public void updateCodeId(String codeId) { this.codeId = codeId; }



    // 보호자 역할일 때의 연결들
    @OneToMany(mappedBy = "guard")
    private List<Connect> guardConnections;

    // 피부양자 역할일 때의 연결들
    @OneToMany(mappedBy = "dependant")
    private List<Connect> dependantConnections;


}