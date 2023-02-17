package Week4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Series")

public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seriesID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Number_of_matches")
    private int numberOfMatches;

    @Column(name = "Number_of_overs")
    private int numnberOfOvers;
}
