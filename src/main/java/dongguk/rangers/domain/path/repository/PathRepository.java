package dongguk.rangers.domain.path.repository;

import dongguk.rangers.domain.path.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends JpaRepository<Path, Long> {
}