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
@Table(name = "Teams")

public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TeamID")
    private int teamID;

    @Column(name = "Name")
    private String Name;

    @ManyToOne
    @JoinColumn(name = "GameID")
    private Game gameID;

    @JsonProperty("gameID")
    private void unpackNested(int game_ID) {
        this.gameID = new Game();
        this.gameID.setGameID(game_ID);
    }

}
