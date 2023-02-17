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
@Table(name = "Series_matches")

public class SeriesMatches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Series_match_ID")
    private int series_match_ID;

    @ManyToOne
    @JoinColumn(name = "SeriesID")
    private Series seriesID;

    @ManyToOne
    @JoinColumn(name = "MatchID")
    private Match matchID;

    @JsonProperty("seriesID")
    private void unpackNested1(int series_ID) {
        this.seriesID = new Series();
        this.seriesID.setSeriesID(series_ID);
    }

    @JsonProperty("matchID")
    private void unpackNested2(int match_ID) {
        this.matchID = new Match();
        this.matchID.setMatchID(match_ID);
    }
}
