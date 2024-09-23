package dongguk.rangers.domain.user.repository;

import dongguk.rangers.domain.user.entity.Connect;
import dongguk.rangers.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long> {
    Optional<Connect> findByDependant(User dependant);
}
