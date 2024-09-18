package dongguk.rangers.domain.path.repository;

import dongguk.rangers.domain.path.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PathRepository extends JpaRepository<Path, Long> {
    Optional<Path> findByUserId(Long userId);  // userId로 경로를 찾는 메소드 정의
}