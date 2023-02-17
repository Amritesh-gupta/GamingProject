package Week4.repository;

import Week4.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepo extends JpaRepository<Team,Integer> {

    @Query(value = "select * from Teams t where t.GameID =:gid",nativeQuery = true)
    List<Team> findByGameID(@Param("gid") int gameID);
}
