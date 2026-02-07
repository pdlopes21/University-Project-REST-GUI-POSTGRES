package pt.ul.fc.di.css.javafxexample.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.dto.JogoDto;

public class Jogo {

    private Long id;
    private SimpleObjectProperty<Campeonato> campeonato = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Equipa> equipaCasa = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Plantel> plantelCasa = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Equipa> equipaVisitante = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Plantel> plantelVisitante = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Arbitro> arbitroMain = new SimpleObjectProperty<>();
    private SimpleListProperty<Arbitro> arbitros = new SimpleListProperty<>();
    private SimpleObjectProperty<LocalDate> dataJogo = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Local> local = new SimpleObjectProperty<>();
    private Boolean completado;
    private Turno turno;
    private SimpleListProperty<Jogador> golosCasa = new SimpleListProperty<>();
    private SimpleListProperty<Jogador> golosVisitante = new SimpleListProperty<>();
    private SimpleListProperty<Jogador> cartoesAmarelos = new SimpleListProperty<>();
    private SimpleListProperty<Jogador> cartoesVermelhosDiretos = new SimpleListProperty<>();

   public Jogo(Long id, Campeonato campeonato, Equipa equipaCasa, Equipa equipaVisitante,
        Plantel plantelCasa, Plantel plantelVisitante, Arbitro arbitroMain,
        LocalDate dataJogo, Local local, boolean completado, Turno turno, List<Jogador> golosCasa, List<Jogador> golosVisitante,
        List<Jogador> cartoesAmarelos, List<Jogador> cartoesVermelhosDiretos, List<Arbitro> arbitros) {

            this.id = id;
            setCampeonato(campeonato);
            setEquipaCasa(equipaCasa);
            setEquipaVisitante(equipaVisitante);
            setPlantelCasa(plantelCasa);
            setPlantelVisitante(plantelVisitante);
            setArbitroMain(arbitroMain);
            setDataJogo(dataJogo);
            setLocal(local);
            setTurno(turno);
            setCompletado(completado);
            if(golosCasa != null && !golosCasa.isEmpty()) {
                setGolosCasa(golosCasa);
            }
            if(golosVisitante != null && !golosVisitante.isEmpty()) {
                setGolosVisitante(golosVisitante);
            }
            if(cartoesAmarelos != null && !cartoesAmarelos.isEmpty()) {
                setCartoesAmarelos(cartoesAmarelos);
            }
            if(cartoesVermelhosDiretos != null && !cartoesVermelhosDiretos.isEmpty()) {
                setCartoesVermelhosDiretos(cartoesVermelhosDiretos);
            }
            if(arbitros != null && !arbitros.isEmpty()) {
                setArbitros(arbitros);
            } 
    }


    public Jogo(Long id, Campeonato campeonato, Equipa equipaCasa, Equipa equipaVisitante,
                 LocalDate dataJogo, Local local, Turno turno) {

        this(id, campeonato, equipaCasa, equipaVisitante, null, null,
                null, dataJogo, local, false, turno, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Jogo(JogoDto dto) {
        this(dto.getId(), null, null, null,
                null, null, null,
                dto.getDataJogo(), null, dto.getCompletado(), dto.getTurno(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                null);

        try {
            Equipa equipaCasaRep = new Equipa(EquipaApiClient.getEquipaById(dto.getEquipaCasa().getId()));
            setEquipaCasa(equipaCasaRep);
            Equipa equipaVisitanteRep = new Equipa(EquipaApiClient.getEquipaById(dto.getEquipaVisitante().getId()));
            setEquipaVisitante(equipaVisitanteRep);
            if(dto.getArbitroMain() != null) {
                setArbitroMain(new Arbitro(dto.getArbitroMain().getId(), dto.getArbitroMain().getNome(), null));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public SimpleObjectProperty<Campeonato> getCampeonatoProperty() {
        return campeonato;
    }

    public SimpleObjectProperty<Equipa> getEquipaCasaProperty() {
        return equipaCasa;
    }

    public SimpleObjectProperty<Equipa> getEquipaVisitanteProperty() {
        return equipaVisitante;
    }

    public SimpleObjectProperty<Plantel> getPlantelCasaProperty() {
        return plantelCasa;
    }

    public SimpleObjectProperty<Plantel> getPlantelVisitanteProperty() {
        return plantelVisitante;
    }

    public SimpleObjectProperty<Arbitro> getArbitroMainProperty() {
        return arbitroMain;
    }

    public SimpleListProperty<Arbitro> getArbitrosProperty() {
        return arbitros;
    }

    public SimpleObjectProperty<LocalDate> getDataJogoProperty() {
        return dataJogo;
    }   

    public SimpleObjectProperty<Local> getLocalProperty() {
        return local;
    }

    public SimpleListProperty<Jogador> getGolosCasaProperty() {
        return golosCasa;
    }

    public SimpleListProperty<Jogador> getGolosVisitanteProperty() {
        return golosVisitante;
    }

    public SimpleListProperty<Jogador> getCartoesAmarelosProperty() {
        return cartoesAmarelos;
    }

    public SimpleListProperty<Jogador> getCartoesVermelhosDiretosProperty() {
        return cartoesVermelhosDiretos;
    }

    

    public List<Arbitro> getArbitros() {
        return arbitros.get();
    }

    public void setArbitros(List<Arbitro> arbitros) {
        this.arbitros.setAll(arbitros);
    }



    public Campeonato getCampeonato() {
        return campeonato.get();
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato.set(campeonato);
    }



    public Equipa getEquipaCasa() {
        return equipaCasa.get();
    }

    public void setEquipaCasa(Equipa equipaCasa) {
        this.equipaCasa.set(equipaCasa);
    }



    public Equipa getEquipaVisitante() {
        return equipaVisitante.get();
    }

    public void setEquipaVisitante(Equipa equipaVisitante) {
        this.equipaVisitante.set(equipaVisitante);
    }



    public Plantel getPlantelCasa() {
        return plantelCasa.get();
    }

    public void setPlantelCasa(Plantel plantelCasa) {
        this.plantelCasa.set(plantelCasa);
    }

    

    public Plantel getPlantelVisitante() {
        return plantelVisitante.get();
    }

    public void setPlantelVisitante(Plantel plantelVisitante) {
        this.plantelVisitante.set(plantelVisitante);
    }



    public Arbitro getArbitroMain() {
        return arbitroMain.get();
    }

    public void setArbitroMain(Arbitro arbitroMain) {
        this.arbitroMain.set(arbitroMain);
    }



    public LocalDate getDataJogo() {
        return dataJogo.get();
    }

    public void setDataJogo(LocalDate dataJogo) {
        this.dataJogo.set(dataJogo);
    }


    public Local getLocal() {
        return local.get();
    }

    public void setLocal(Local local) {
        this.local.set(local);
    }



    public Boolean getCompletado() {
        return completado;
    } 

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }



    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }



    public List<Jogador> getGolosCasa() {
        return golosCasa.get();
    }

    public void setGolosCasa(List<Jogador> golosCasa) {
        this.golosCasa.setAll(golosCasa);
    }



    public List<Jogador> getGolosVisitante() {
        return golosVisitante.get();
    }

    public void setGolosVisitante(List<Jogador> golosVisitante) {
        this.golosVisitante.setAll(golosVisitante);
    }



    public List<Jogador> getCartoesAmarelos() {
        return cartoesAmarelos.get();
    }

    public void setCartoesAmarelos(List<Jogador> cartoesAmarelos) {
        this.cartoesAmarelos.setAll(cartoesAmarelos);
    }



    public List<Jogador> getCartoesVermelhosDiretos() {
        return cartoesVermelhosDiretos.get();
    }

    public void setCartoesVermelhosDiretos(List<Jogador> cartoesVermelhosDiretos) {
        this.cartoesVermelhosDiretos.setAll(cartoesVermelhosDiretos);
    }


    

    public void addArbitro(Arbitro arbitro) {
        this.arbitros.add(arbitro);
    }

    public void removeArbitro(Arbitro arbitro) {
        this.arbitros.remove(arbitro);
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




    public void addCartaoAmarelo(Jogador jogador) {
        this.cartoesAmarelos.add(jogador);
    }



    public void removeCartaoAmarelo(Jogador jogador) {
        this.cartoesAmarelos.remove(jogador);
    }


    
    public void addCartaoVermelho(Jogador jogador) {
        this.cartoesVermelhosDiretos.add(jogador);
    }

    public void removeCartaoVermelho(Jogador jogador) {
        this.cartoesVermelhosDiretos.remove(jogador);
    }

    @Override
    public String toString() {
        return "Jogo " + id + ": " + equipaCasa.get().getNome() + " vs " + equipaVisitante.get().getNome();
    }


}

