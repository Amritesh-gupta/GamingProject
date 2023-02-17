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
@Table(name = "Batsman_score_card")
@IdClass(ScorecardID.class)

public class BatsmanStat {
    @Id
    @ManyToOne
    @JoinColumn(name = "PlayerID")
    private Player playerID;
    @Id
    @ManyToOne
    @JoinColumn(name = "MatchID")
    private Match matchID;



    @Column(name = "Runs")
    private int runs;
    @Column(name = "Balls")
    private int ballsPlayed;

    @Column(name = "Number_of_sixes")
    private int numberOfSixes;

    @Column(name = "Number_of_fours")
    private int numberOfFours;

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
