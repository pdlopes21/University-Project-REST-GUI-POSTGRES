package pt.ul.fc.css.soccernow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.soccernow.entities.Equipa;

@Repository
public interface EquipaRepository extends JpaRepository<Equipa, Long> {

    Optional<Equipa> findByNome(String nome);

    @Query("SELECT e FROM Equipa e WHERE SIZE(e.jogadores) < 5")
    List<Equipa> findEquipasWithLessThanFiveJogadores();

}
