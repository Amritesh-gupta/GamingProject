package Week4.model;

import Week4.compositeKey.ScorecardID;
import Week4.compositeKey.TeamScorecardID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Team_score_card")
@IdClass(TeamScorecardID.class)

public class TeamStat {
    @Id
    @ManyToOne
    @JoinColumn(name = "TeamID")
    private Team teamID;
    @Id
    @ManyToOne
    @JoinColumn(name = "MatchID")
    private Match matchID;


    @Column(name = "Overs")
    private double numberOfOvers;

    @Column(name = "Runs")
    private int runsCost;

    @Column(name = "Wickets")
    private int numberOfWickets;

    @JsonProperty("teamID")
    private void unpackNested1(int team_ID) {
        this.teamID = new Team();
        this.teamID.setTeamID(team_ID);
    }

    @JsonProperty("matchID")
    private void unpackNested2(int match_ID) {
        this.matchID = new Match();
        this.matchID.setMatchID(match_ID);
    }
}
