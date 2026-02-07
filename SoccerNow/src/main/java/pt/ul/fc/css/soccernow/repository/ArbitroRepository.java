package pt.ul.fc.css.soccernow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.soccernow.entities.Arbitro;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {

    Optional<Arbitro> findByUsername(String username);
    
    Optional<Arbitro> findByNome(String nome);

    Optional<List<Arbitro>> findAllByNome(String nome);


}
