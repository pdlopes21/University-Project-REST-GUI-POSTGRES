package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import pt.ul.fc.css.soccernow.entities.Jogador.Posicao;

/**
 * Entidade que representa uma plante de futsal da FCUL. Contém o seu id (gerado automáticamente), equipa,
 * lista de jogadores, e jogo.
 */
@Entity
public class Plantel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Equipa equipa;

    @ManyToMany
    private List<Jogador> jogadores = new ArrayList<>();

    private boolean locked = false;


    public Plantel() {}

    public Plantel(Equipa equipa) {
        this.equipa = equipa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Jogador> getGRs () {
        List<Jogador> grs = new ArrayList<>();
        for (Jogador jogador : this.jogadores) {
            if (jogador.getPosicao() == Posicao.GR) {
                grs.add(jogador);
            }
        }
        return grs;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public long nGRs () {
        return this.jogadores.stream().filter(j -> j.getPosicao() == Posicao.GR).count();
    }

    public boolean temGR () {
        boolean gr = false;
        if (nGRs() == 1) {
            gr = true;
        }
        return gr;
    }

    public void addJogador (Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public void removeJogador (Jogador jogador) {
        this.jogadores.remove(jogador);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
