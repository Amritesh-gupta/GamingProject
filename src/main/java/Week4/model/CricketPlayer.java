package Week4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CricketPlayers")
public class CricketPlayer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cricket_players_ID")
    private int cricketPlayerID;

    @ManyToOne
    @JoinColumn(name = "PlayerID")
    private Player playerID;

    @ManyToOne
    @JoinColumn(name = "TeamID")
    private Team teamID;

    @Column(name = "Role")
    private String role;

    @Column(name = "TotalRunsScored")
    private int totalRunsScored;

    @Column(name = "NumberOfCenturies")
    private int numberOfCenturies;

    @Column(name = "NumberOfMatchesPlayed")
    private int numberOfMatchesPlayed;

    @Column(name = "AvgStrikeRate")
    private double avgStrikeRate;

    @JsonProperty("playerID")
    private void unpackNested(int player_ID) {
        this.playerID = new Player();
        this.playerID.setPlayerID(player_ID);
    }

    @JsonProperty("teamID")
    private void unpackNested2(int team_ID) {
        this.teamID = new Team();
        this.teamID.setTeamID(team_ID);
    }

}

