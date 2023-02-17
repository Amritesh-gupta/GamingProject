package Week4.repository;

import Week4.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<Game,Integer> {
    Game findByName(String name);
}
