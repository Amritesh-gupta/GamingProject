package Week4.service;

import Week4.model.Series;

import java.util.ArrayList;
import java.util.Optional;

public interface SeriesService {
    void addSeries(Series series);
    void addAllSeries(ArrayList<Series> series);
    Optional<Series> getSeriesByID(int seriesID);
}
