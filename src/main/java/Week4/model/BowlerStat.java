package Week4.model;


import Week4.compositeKey.ScorecardID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Bowler_score_card")
@IdClass(ScorecardID.class)

public class BowlerStat {

    @Id
    @ManyToOne
    @JoinColumn(name = "PlayerID")
    private Player playerID;
    @Id
    @ManyToOne
    @JoinColumn(name = "MatchID")
    private Match matchID;


    @Column(name = "Overs")
    private double numberOfOvers;

    @Column(name = "Maiden_overs")
    private int numberOfMaidenOvers;

    @Column(name = "Runs")
    private int runsCost;

    @Column(name = "Wickets")
    private int numberOfWickets;

    @JsonProperty("playerID")
    private void unpackNested1(int player_ID) {
        this.playerID = new Player();
        this.playerID.setPlayerID(player_ID);
    }

    @JsonProperty("matchID")
    private void unpackNested2(int match_ID) {
        this.matchID = new Match();
        this.matchID.setMatchID(match_ID);
    }

}
