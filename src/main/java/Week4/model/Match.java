package Week4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Matches")

public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MatchID")
    private int matchID;

    @ManyToOne
    @JoinColumn(name = "TeamA_ID")
    private Team teamA_ID;

    @ManyToOne
    @JoinColumn(name = "TeamB_ID")
    private Team teamB_ID;

    @ManyToOne
    @JoinColumn(name = "Winner_ID")
    private Team winnerTeamID;

    @JsonProperty("teamA_ID")
    private void unpackNested1(int team_ID) {
        this.teamA_ID = new Team();
        this.teamA_ID.setTeamID(team_ID);
    }

    @JsonProperty("teamB_ID")
    private void unpackNested2(int team_ID) {
        this.teamB_ID = new Team();
        this.teamB_ID.setTeamID(team_ID);
    }

    @JsonProperty("winner_ID")
    private void unpackNested3(int team_ID) {
        this.winnerTeamID = new Team();
        this.winnerTeamID.setTeamID(team_ID);
    }

}
