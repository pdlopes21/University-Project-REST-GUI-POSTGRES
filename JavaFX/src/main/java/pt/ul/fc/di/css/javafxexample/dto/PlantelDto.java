package pt.ul.fc.di.css.javafxexample.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.Jogador;
import pt.ul.fc.di.css.javafxexample.model.Jogador.Posicao;
import pt.ul.fc.di.css.javafxexample.model.Plantel;


/**
 * Dto do plantel com o seu id (gerado automáticamente), detalhes da equipa e os
 * detailhes dos jogadores do plantel.
 */
public class PlantelDto {

    private Long id;
    private EquipaEssentialDetails equipa;
    private List<JogadorEssentialDetails> jogadores = new ArrayList<>();
    private boolean locked = false;

    public PlantelDto() {
    }

    public PlantelDto(Plantel plantel) {
        this.id = plantel.getId();
        this.equipa = new EquipaEssentialDetails(plantel.getEquipa());
        if(plantel.getJogadores() != null && !plantel.getJogadores().isEmpty()) {
            for (Jogador jogador : plantel.getJogadores()) {
                this.jogadores.add(new JogadorEssentialDetails(jogador));
            }
        }
        this.locked = plantel.isLocked();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipaEssentialDetails getEquipa() {
        return equipa;
    }

    public void setEquipa(EquipaEssentialDetails equipa) {
        this.equipa = equipa;
    }

    public List<JogadorEssentialDetails> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<JogadorEssentialDetails> jogadores) {
        this.jogadores = jogadores;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long nGRs() {
        return this.jogadores.stream().filter(j -> j.getPosicao() == Posicao.GR).count();
    }

    public boolean temGR() {
        boolean gr = false;
        if (nGRs() == 1) {
            gr = true;
        }
        return gr;
    }

    public void addJogador(JogadorEssentialDetails jogador) {
        this.jogadores.add(jogador);
    }

    public void removeJogador(JogadorEssentialDetails jogador) {
        this.jogadores.remove(jogador);
    }

    public void removeJogador(Jogador jogador) {
        for (JogadorEssentialDetails j : this.jogadores) {
            if (Objects.equals(j.getId(), jogador.getId())) {
                this.jogadores.remove(j);
                break;
            }
        }
    }

    public void removeJogador(JogadorDto jogador) {
        for (JogadorEssentialDetails j : this.jogadores) {
            if (Objects.equals(j.getId(), jogador.getId())) {
                this.jogadores.remove(j);
                break;
            }
        }
    }

    public JogadorEssentialDetails jogadorGR() {
        for (JogadorEssentialDetails jogador : this.jogadores) {
            if (jogador.getPosicao() == Posicao.GR) {
                return jogador;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantelDto)) return false;
        PlantelDto that = (PlantelDto) o;
        return locked == that.locked && Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "Plantel" + id + ": " + equipa.getNome();
    }
}
