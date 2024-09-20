package dongguk.rangers.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "User")
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
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    // 보호자 역할일 때의 연결들
    @OneToMany(mappedBy = "guard")
    private List<Connect> guardConnections;

    // 피부양자 역할일 때의 연결들
    @OneToMany(mappedBy = "dependant")
    private List<Connect> dependantConnections;
}