package pt.ul.fc.css.soccernow.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
/**
 * Entidade que representa um jogo de futsal da FCUL. Contém o seu id (gerado automáticamente), campeonato,
 * plantel casa, plantel visitante, arbitro principal e secundários, data e local de jogo, e se foi completado.
 */
@Entity
public class Jogo {


    public enum Turno {
        MANHA,
        TARDE,
        NOITE;

        public boolean equalsString(String value) {
            if (value == null) return false;
            return this.name().equalsIgnoreCase(value);
        }

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "equipa_casa_id") // Foreign key column in Jogo
    private Equipa equipaCasa;

    @ManyToOne
    @JoinColumn(name = "plantel_casa_id", referencedColumnName = "id")
    private Plantel plantelCasa;

    @ManyToOne
    @JoinColumn(name = "equipa_visitante_id") // Foreign key column in Jogo
    private Equipa equipaVisitante;

    @ManyToOne
    @JoinColumn(name = "plantel_visitante_id", referencedColumnName = "id")
    private Plantel plantelVisitante;

    @ManyToOne
    private Arbitro arbitroMain;

    @ManyToMany
    private List<Arbitro> arbitros = new ArrayList<>();

    private LocalDate dataJogo;

    @ManyToOne
    private Local local;

    private boolean completado;

    @Enumerated(EnumType.STRING)
    private Turno turno;

    @ManyToMany
    private List<Jogador> golosCasa = new ArrayList<>();

    @ManyToMany
    private List<Jogador> golosVisitante = new ArrayList<>();

    @ManyToMany
    private List<Jogador> cartoesAmarelos = new ArrayList<>();

    @ManyToMany
    private List<Jogador> cartoesVermelhosDiretos = new ArrayList<>();

    public Jogo() {}

    public Jogo(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }
    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
    
    public Equipa getEquipaCasa() {
        return equipaCasa;
    }

    public void setEquipaCasa(Equipa equipaCasa) {   
        this.equipaCasa = equipaCasa;
    }

    public Equipa getEquipaVisitante() {
        return equipaVisitante;
    }

    public void setEquipaVisitante(Equipa equipaVisitante) {
        this.equipaVisitante = equipaVisitante;
    }

    public Plantel getPlantelCasa() {
        return plantelCasa;
    }

    public void setPlantelCasa(Plantel plantelCasa) {
        this.plantelCasa = plantelCasa;
    }

    public Plantel getPlantelVisitante() {
        return plantelVisitante;
    }

    public void setPlantelVisitante(Plantel plantelVisitante) {
        this.plantelVisitante = plantelVisitante;
    }


    public List<Jogador> getGolosCasa() {
        return golosCasa;
    }

    public void setGolosCasa(List<Jogador> golosCasa) {
        this.golosCasa = golosCasa;
    }

    public List<Jogador> getGolosVisitante() {
        return golosVisitante;
    }

    public void setGolosVisitante(List<Jogador> golosVisitante) {
        this.golosVisitante = golosVisitante;
    }

    public Arbitro getArbitroMain() {
        return arbitroMain;
    }

    public void setArbitroMain(Arbitro arbitroMain) {
        this.arbitroMain = arbitroMain;
        if(!this.arbitros.contains(arbitroMain)) {
            this.arbitros.add(arbitroMain);
        }
    }

    public List<Arbitro> getArbitros() {
        return arbitros;
    }

    public void setArbitros(List<Arbitro> arbitros) {
        for (Arbitro a : arbitros) {
            if (!this.arbitros.contains(a)) {
                this.arbitros.add(a);
            }
        }

    }

    public List<Jogador> getCartoesAmarelos() {
        return cartoesAmarelos;
    }

    public void setCartoesAmarelos(List<Jogador> cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
    }

    public List<Jogador> getCartoesVermelhosDiretos() {
        return cartoesVermelhosDiretos;
    }

    public void setCartoesVermelhosDiretos(List<Jogador> cartoesVermelhosDiretos) {
        this.cartoesVermelhosDiretos = cartoesVermelhosDiretos;
    }

    public LocalDate getDataJogo() {
        return dataJogo;
    }

    public void setDataJogo(LocalDate dataJogo) {
        this.dataJogo = dataJogo;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public boolean getCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public void addGoloCasa(Jogador jogador) {
        this.golosCasa.add(jogador);
    }

    public void removeGoloCasa(Jogador jogador) {
        this.golosCasa.remove(jogador);

    }

    public void addGoloVisitante(Jogador jogador) {
        this.golosVisitante.add(jogador);
    }

    public void removeGoloVisitante(Jogador jogador) {
        this.golosVisitante.remove(jogador);

    }

    public void addArbitro(Arbitro arbitro) {
        if (!this.arbitros.contains(arbitro)) {
            this.arbitros.add(arbitro);
        } 
    }

    public void removeArbitros(List<Arbitro> arbitros) {
        for (Arbitro a : arbitros) {
            removeArbitro (a);
        }
    }

    public void removeArbitro (Arbitro arbitro) {
        this.arbitros.remove(arbitro);
        if(this.arbitroMain != null && this.arbitroMain.equals(arbitro)) {
            this.arbitroMain = null;
        }
    }

    public void addCartaoAmarelo(Jogador jogador) {         
        this.cartoesAmarelos.add(jogador);
 
    }

    public void removeCartaoAmarelo(Jogador jogador) {
        this.cartoesAmarelos.remove(jogador);
    }

    public void addCartaoVermelhoDireto(Jogador jogador) {
        this.cartoesVermelhosDiretos.add(jogador);
    }

    public void removeCartaoVermelhoDireto(Jogador jogador) {
        this.cartoesVermelhosDiretos.remove(jogador);
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

}
