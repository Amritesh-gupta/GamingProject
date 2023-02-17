package Week4.repository;

import Week4.model.BatsmanStat;
import Week4.model.BowlerStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface BowlerStatRepo extends CrudRepository<BowlerStat,Integer> {
    @Modifying
    @Transactional
    @Query(value = "Update Bowler_score_card b set b.Overs =:overs, b.Maiden_overs =:maidenOvers, b.Runs =:runs,b.Wickets =:wickets " +
            "where b.MatchID =:matchID and b.PlayerID =:playerID",nativeQuery = true)
    void updateBowlerStat(@Param("overs")double overs, @Param("maidenOvers")int maidenOvers, @Param("runs")int runs,
                           @Param("wickets")int wickets, @Param("matchID")int matchID, @Param("playerID")int playerID);


    //    @Query("select * from BatsmanScoreCard where MatchID = 3 and PlayerID = 3")
    @Query(value = "select * from Bowler_score_card b where b.PlayerID =:pid and b.MatchID =:mid",nativeQuery = true)
   BowlerStat findByPlayerIDAndMatchID(@Param("pid") int playerID, @Param("mid") int matchID);

    @Query(value = "select * from Bowler_score_card b where b.MatchID =:mid",nativeQuery = true)
    ArrayList<BowlerStat> findByMatchID(@Param("mid") int matchID);


}
