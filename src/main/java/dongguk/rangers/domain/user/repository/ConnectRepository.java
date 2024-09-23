package dongguk.rangers.domain.user.repository;

import dongguk.rangers.domain.user.entity.Connect;
import dongguk.rangers.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long> {
    List<Connect> findByGuard(User guard);
    List<Connect> findByDependant(User dependant);
}
