package pt.ul.fc.css.soccernow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Plantel;

@Repository
public interface PlantelRepository extends JpaRepository<Plantel, Long> {
    
    //Método não testado
    List<Plantel> findAllByEquipa(Equipa equipa);
}
