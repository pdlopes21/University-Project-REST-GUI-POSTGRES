package pt.ul.fc.css.soccernow.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    
}
