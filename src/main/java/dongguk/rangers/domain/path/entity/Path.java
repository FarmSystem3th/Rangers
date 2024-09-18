package dongguk.rangers.domain.path.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Path")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String start;

    @Column(nullable = false)
    private String end;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PathState state;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = true)
    private LocalDateTime endTime;

    public void updateState(PathState newState, LocalDateTime newEndTime) {
        this.state = newState;  // 상태 업데이트
        this.endTime = newEndTime;  // 도착 시간 업데이트
    }
}
