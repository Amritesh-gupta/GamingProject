package Week4.repository;

import Week4.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayersRepo extends JpaRepository<Player,Integer> {
    List<Player> findByCountry(String country);
//    List<Player> findByGame(String game);
}
