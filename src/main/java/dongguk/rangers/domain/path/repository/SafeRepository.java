package dongguk.rangers.domain.path.repository;

import dongguk.rangers.domain.path.entity.Safe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeRepository extends JpaRepository<Safe, Long> {
}
