package Week4.repository;

import Week4.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepo extends JpaRepository<Match,Integer> {
    @Modifying
    @Query(value = "UPDATE Matches m set m.Winner_ID =:winnerID where m.MatchID =:matchID", nativeQuery = true)
    void updateWinnerID(@Param("winnerID") int winnerID,@Param("matchID") int matchID);

}
