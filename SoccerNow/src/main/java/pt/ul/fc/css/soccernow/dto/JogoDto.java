package pt.ul.fc.css.soccernow.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.*;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Jogo.Turno;

public class JogoDto {

    private Long id;
    private CampeonatoEssentialDetails campeonato;
    private EquipaEssentialDetails equipaCasa;
    private EquipaEssentialDetails equipaVisitante;
    private PlantelDto plantelCasa;
    private PlantelDto plantelVisitante;
    private ArbitroEssentialDetails arbitroMain;
    private List<ArbitroEssentialDetails> arbitros = new ArrayList<>();
    private LocalDate dataJogo;
    
    private Turno turno;
    private LocalEssentialDetails local;
    private boolean completado;

    private List<JogadorEssentialDetails> golosCasa = new ArrayList<>();
    private List<JogadorEssentialDetails> golosVisitante = new ArrayList<>();
    private List<JogadorEssentialDetails> cartoesAmarelos = new ArrayList<>();
    private List<JogadorEssentialDetails> cartoesVermelhosDiretos = new ArrayList<>();

    /**
     * Dto do jogo com o seu id (gerado automáticamente), detalhes de
     * Campeonato, de Equipas (Visitante e Casa), de Plantéis (Visitante e
     * Casa); a data e hora do jogo, o lugar, o estado de completude, os golos
     * das equipas e os cartões das equipas.
     */
    public JogoDto() {
    }

    public JogoDto(Jogo jogo) {
        this.id = jogo.getId();
        if (jogo.getCampeonato() != null) {
            this.campeonato = new CampeonatoEssentialDetails(jogo.getCampeonato());
        }

        if (jogo.getEquipaCasa() != null) {
            this.equipaCasa = new EquipaEssentialDetails(jogo.getEquipaCasa());
        }

        if (jogo.getEquipaVisitante() != null) {
            this.equipaVisitante = new EquipaEssentialDetails(jogo.getEquipaVisitante());
        }

        if (jogo.getPlantelCasa() != null) {
            this.plantelCasa = new PlantelDto(jogo.getPlantelCasa());
        }

        if (jogo.getPlantelVisitante() != null) {
            this.plantelVisitante = new PlantelDto(jogo.getPlantelVisitante());
        }

        if (jogo.getArbitroMain() != null) {
            this.arbitroMain = new ArbitroEssentialDetails(jogo.getArbitroMain());
        }
        if (jogo.getArbitros() != null) {
            for (Arbitro arbitro : jogo.getArbitros()) {
                this.arbitros.add(new ArbitroEssentialDetails(arbitro));
            }
        }
        this.dataJogo = jogo.getDataJogo();
        this.turno = jogo.getTurno();
        this.local = new LocalEssentialDetails(jogo.getLocal());

        this.completado = jogo.getCompletado();
        if (jogo.getGolosCasa() != null) {
            for (Jogador jogador : jogo.getGolosCasa()) {
                this.golosCasa.add(new JogadorEssentialDetails(jogador));
            }
        }
        if (jogo.getGolosVisitante() != null) {
            for (Jogador jogador : jogo.getGolosVisitante()) {
                this.golosVisitante.add(new JogadorEssentialDetails(jogador));
            }
        }
        if (jogo.getCartoesAmarelos() != null) {
            for (Jogador jogador : jogo.getCartoesAmarelos()) {
                this.cartoesAmarelos.add(new JogadorEssentialDetails(jogador));
            }
        }
        if (jogo.getCartoesVermelhosDiretos() != null) {
            for (Jogador jogador : jogo.getCartoesVermelhosDiretos()) {
                this.cartoesVermelhosDiretos.add(new JogadorEssentialDetails(jogador));
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampeonatoEssentialDetails getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(CampeonatoEssentialDetails campeonato) {
        this.campeonato = campeonato;
    }

    public PlantelDto getPlantelCasa() {
        return plantelCasa;
    }

    public void setPlantelCasa(PlantelDto plantelCasa) {
        this.plantelCasa = plantelCasa;
    }

    public void setEquipaCasa(EquipaEssentialDetails equipaCasa) {
        this.equipaCasa = equipaCasa;
    }

    public EquipaEssentialDetails getEquipaCasa() {
        return equipaCasa;
    }

    public EquipaEssentialDetails getEquipaVisitante() {
        return equipaVisitante;
    }

    public void setEquipaVisitante(EquipaEssentialDetails equipaVisitante) {
        this.equipaVisitante = equipaVisitante;
    }

    public PlantelDto getPlantelVisitante() {
        return plantelVisitante;
    }

    public void setPlantelVisitante(PlantelDto plantelVisitante) {
        this.plantelVisitante = plantelVisitante;
    }

    public List<JogadorEssentialDetails> getGolosCasa() {
        return golosCasa;
    }

    public void setGolosCasa(List<JogadorEssentialDetails> golosCasa) {
        this.golosCasa = golosCasa;
    }

    public List<JogadorEssentialDetails> getGolosVisitante() {
        return golosVisitante;
    }

    public void setGolosVisitante(List<JogadorEssentialDetails> golosVisitante) {
        this.golosVisitante = golosVisitante;
    }

    public ArbitroEssentialDetails getArbitroMain() {
        return arbitroMain;
    }

    public void setArbitroMain(ArbitroEssentialDetails arbitroMain) {
        this.arbitroMain = arbitroMain;
        if (!this.arbitros.contains(arbitroMain)) {
            addArbitro(arbitroMain);
        }
    }

    public List<ArbitroEssentialDetails> getArbitros() {
        return arbitros;
    }

    public void setArbitros(List<ArbitroEssentialDetails> arbitros) {
        this.arbitros = arbitros;
    }

    public List<JogadorEssentialDetails> getCartoesAmarelos() {
        return cartoesAmarelos;
    }

    public void setCartoesAmarelos(List<JogadorEssentialDetails> cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
    }

    public List<JogadorEssentialDetails> getCartoesVermelhosDiretos() {
        return cartoesVermelhosDiretos;
    }

    public void setCartoesVermelhosDiretos(List<JogadorEssentialDetails> cartoesVermelhosDiretos) {
        this.cartoesVermelhosDiretos = cartoesVermelhosDiretos;
    }

    public LocalDate getDataJogo() {
        return dataJogo;
    }

    public void setDataJogo(LocalDate dataJogo) {
        this.dataJogo = dataJogo;
    }

    public LocalEssentialDetails getLocal() {
        return local;
    }

    public void setLocal(LocalEssentialDetails local) {
        this.local = local;
    }

    public boolean getCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    // Add a Jogador to golosCasa
    public void addGoloCasa(JogadorEssentialDetails jogador) {
        this.golosCasa.add(jogador);
    }

    // Remove a Jogador from golosCasa
    public void removeGoloCasa(JogadorEssentialDetails jogador) {
        this.golosCasa.remove(jogador);
    }

    // Add a Jogador to golosVisitante
    public void addGoloVisitante(JogadorEssentialDetails jogador) {
        this.golosVisitante.add(jogador);
    }

    // Remove a Jogador from golosVisitante
    public void removeGoloVisitante(JogadorEssentialDetails jogador) {
        this.golosVisitante.remove(jogador);
    }

    // Add an Arbitro to the list
    public void addArbitro(ArbitroEssentialDetails arbitro) {
        this.arbitros.add(arbitro);
    }

    // Remove an Arbitro from the list
    public void removeArbitro(ArbitroEssentialDetails arbitro) {
        this.arbitros.remove(arbitro);
    }

    // Add a Jogador to cartoesAmarelos
    public void addCartaoAmarelo(JogadorEssentialDetails jogador) {
        this.cartoesAmarelos.add(jogador);
    }

    // Remove a Jogador from cartoesAmarelos
    public void removeCartaoAmarelo(JogadorEssentialDetails jogador) {
        this.cartoesAmarelos.remove(jogador);
    }

    // Add a Jogador to cartoesVermelhosDiretos
    public void addCartaoVermelhoDireto(JogadorEssentialDetails jogador) {
        this.cartoesVermelhosDiretos.add(jogador);
    }

    // Remove a Jogador from cartoesVermelhosDiretos
    public void removeCartaoVermelhoDireto(JogadorEssentialDetails jogador) {
        this.cartoesVermelhosDiretos.remove(jogador);
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    

}
