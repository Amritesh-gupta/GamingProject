package Week4.repository;

import Week4.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepo extends JpaRepository<Series,Integer> {

}
