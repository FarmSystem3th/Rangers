package dongguk.rangers.domain.path.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Path")
@NoArgsConstructor
@AllArgsConstructor
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String start;

    @Column(nullable = false)
    private String end;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PathState state;
}