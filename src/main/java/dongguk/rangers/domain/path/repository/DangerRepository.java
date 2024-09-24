package dongguk.rangers.domain.path.repository;

import dongguk.rangers.domain.path.entity.Danger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangerRepository extends JpaRepository<Danger, Long> {

    @Query("SELECT d FROM Danger d WHERE d.latitude BETWEEN :startLat AND :endLat AND d.longitude BETWEEN :startLng AND :endLng")
    List<Danger> findDangerZonesInRange(
            @Param("startLat") double startLat,
            @Param("endLat") double endLat,
            @Param("startLng") double startLng,
            @Param("endLng") double endLng
    );
}
