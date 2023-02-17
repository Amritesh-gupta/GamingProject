package Week4.service.Impl;

import Week4.model.Series;
import Week4.repository.SeriesRepo;
import Week4.service.SeriesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SeriesServiceImpl implements SeriesService {

    private SeriesRepo seriesRepo;

    public SeriesServiceImpl(SeriesRepo seriesRepo) {
        this.seriesRepo = seriesRepo;
    }

    @Override
    public void addSeries(Series series) {
        seriesRepo.save(series);
    }

    @Override
    public void addAllSeries(ArrayList<Series> series) {
        seriesRepo.saveAll(series);
    }

    @Override
    public Optional<Series> getSeriesByID(int seriesID) {
        return seriesRepo.findById(seriesID);
    }

}
