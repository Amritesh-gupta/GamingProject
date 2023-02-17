package Week4.repository;

import Week4.model.TeamStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TeamStatRepo extends JpaRepository<TeamStat, Integer> {
    @Query(value = "select * from Team_score_card t where t.teamID =:tid and t.MatchID =:mid",nativeQuery = true)
    TeamStat findByTeamIDAndMatchID(@Param("tid") int teamID, @Param("mid") int matchID);

    @Query(value = "select * from Team_score_card t where t.MatchID =:mid",nativeQuery = true)
    ArrayList<TeamStat> findByMatchID(@Param("mid") int matchID);
}
