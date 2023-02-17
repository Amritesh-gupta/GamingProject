package Week4.repository;

import Week4.model.BatsmanStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface BatsmanStatRepo extends CrudRepository<BatsmanStat,Integer> {

    @Transactional
    @Modifying
    @Query(value = "Update Batsman_score_card b set b.Runs =:runs, b.Balls =:balls, b.Number_of_sixes =:sixes,b.Number_of_fours =:fours " +
            "where b.MatchID =:matchID and b.PlayerID =:playerID",nativeQuery = true)
    void updateBatsmanStat(@Param("runs")int runs, @Param("balls")int balls, @Param("sixes")int sixes,
                           @Param("fours")int fours, @Param("matchID")int matchID, @Param("playerID")int playerID);


//    @Query("select * from BatsmanScoreCard where MatchID = 3 and PlayerID = 3")
    @Query(value = "select * from Batsman_score_card b where b.PlayerID =:pid and b.MatchID =:mid",nativeQuery = true)
    BatsmanStat findByPlayerIDAndMatchID(@Param("pid") int playerID, @Param("mid") int matchID);

    @Query(value = "select * from Batsman_score_card b where b.MatchID =:mid",nativeQuery = true)
    ArrayList<BatsmanStat> findByMatchID(@Param("mid") int matchID);
}