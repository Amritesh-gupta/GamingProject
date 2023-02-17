package Week4.service;

import Week4.model.SeriesMatches;

import java.util.ArrayList;
import java.util.Optional;

public interface SeriesMatchesService {
    void addSeriesMatch(SeriesMatches series);
    void addAllSeriesMatches(ArrayList<SeriesMatches> series);
    Optional<SeriesMatches> getSeriesMatchesByID(int seriesMatchID);
    ArrayList<SeriesMatches> getAllMatchesBySeriesID(int seriesID);
}
