package Week4.service.Impl;

import Week4.model.SeriesMatches;
import Week4.repository.SeriesMatchRepo;
import Week4.service.SeriesMatchesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SeriesMatchServiceImpl implements SeriesMatchesService {

    private SeriesMatchRepo seriesMatchRepo;

    public SeriesMatchServiceImpl(SeriesMatchRepo seriesMatchRepo) {
        this.seriesMatchRepo = seriesMatchRepo;
    }

    @Override
    public void addSeriesMatch(SeriesMatches series) {
        seriesMatchRepo.save(series);
    }

    @Override
    public void addAllSeriesMatches(ArrayList<SeriesMatches> series) {
        seriesMatchRepo.saveAll(series);
    }

    @Override
    public Optional<SeriesMatches> getSeriesMatchesByID(int seriesMatchID) {
        return seriesMatchRepo.findById(seriesMatchID);
    }

    @Override
    public ArrayList<SeriesMatches> getAllMatchesBySeriesID(int seriesID) {
        return seriesMatchRepo.findBySeriesID(seriesID);
    }
}
