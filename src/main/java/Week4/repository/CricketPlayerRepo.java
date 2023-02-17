package Week4.repository;

import Week4.model.CricketPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CricketPlayerRepo extends JpaRepository<CricketPlayer,Integer> {
    @Query(value = "select * from Cricket_players c where c.teamID =:tid",nativeQuery = true)
    List<CricketPlayer> findByTeamID(@Param("tid") int teamID);
}
