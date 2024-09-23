package dongguk.rangers.domain.user.repository;

import dongguk.rangers.domain.user.entity.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long> {
}
