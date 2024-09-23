package dongguk.rangers.domain.path.repository;

import dongguk.rangers.domain.path.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PathRepository extends JpaRepository<Path, Long> {
    // userId로 가장 최신의 Path 하나를 조회
    Optional<Path> findTop1ByUserIdOrderByStartTimeDesc(Long userId);

    List<Path> findByUserId(Long userId);  // 사용자 ID로 경로를 찾는 메소드
}