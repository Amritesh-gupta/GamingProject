package Week4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GameID")
    private int gameID;

    @Column(name = "Name")
    private String name;

    @Column(name = "RequiredPlayersPerTeam")
    private int requiredPlayersPerTeam;

}
