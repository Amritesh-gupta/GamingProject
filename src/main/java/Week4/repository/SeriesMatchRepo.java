package Week4.repository;

import Week4.model.SeriesMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface SeriesMatchRepo extends JpaRepository<SeriesMatches,Integer> {
    @Query(value = "select * from Series_matches s where s.SeriesID =:sid",nativeQuery = true)
    ArrayList<SeriesMatches> findBySeriesID(@Param("sid") int seriesDID);
}
