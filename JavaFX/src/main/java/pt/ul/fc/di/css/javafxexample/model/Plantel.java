package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import pt.ul.fc.di.css.javafxexample.api.EquipaApiClient;
import pt.ul.fc.di.css.javafxexample.dto.PlantelDto;

public class Plantel {


    private Long id;
    private SimpleObjectProperty<Equipa> equipa = new SimpleObjectProperty<>();
    private SimpleListProperty<Jogador> jogadores = new SimpleListProperty<>();
    private boolean locked = false;

    public Plantel(Long id, Equipa equipa, List<Jogador> jogadores) {
        this.id = id;
        setEquipa(equipa);
        if(jogadores != null && !jogadores.isEmpty()) {
            setJogadores(jogadores);
        }
    }

    public Plantel(Long id, Equipa equipa) {
        this(id, equipa, new ArrayList<>());
    }

    public Plantel(PlantelDto dto)  {
        this(dto.getId(), null, new ArrayList<>());
        try {
            Equipa equipaRep = new Equipa(EquipaApiClient.getEquipaById(dto.getEquipa().getId()));
            setEquipa(equipaRep);
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }



    public SimpleObjectProperty<Equipa> getEquipaProperty() {
        return equipa;
    }

    public SimpleListProperty<Jogador> getJogadoresProperty() {
        return jogadores;
    }



    public Equipa getEquipa() {
        return equipa.get();
    }

    public void setEquipa(Equipa equipa) {
        this.equipa.set(equipa);
    }


    
    public List<Jogador> getJogadores() {
        return jogadores.get();
    }
    
    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores.setAll(jogadores);
    }

    

    public void addJogador(Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador) {
        this.jogadores.remove(jogador);
    }

    @Override
    public String toString() {
        return "Plantel" + id + ", " + equipa.get();
    }


}
