package dongguk.rangers.domain.path.repository;

import dongguk.rangers.domain.path.entity.Danger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DangerRepository extends JpaRepository<Danger, Long> {
}
