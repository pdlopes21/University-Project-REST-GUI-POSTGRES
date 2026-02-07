package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import pt.ul.fc.di.css.javafxexample.dto.LocalDto;

public class Local {

    private Long id;
    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty morada = new SimpleStringProperty();
    private SimpleListProperty<Jogo> jogos = new SimpleListProperty<>();

    public Local(Long id, String nome, String morada, List<Jogo> jogos) {
        this.id = id;
        setNome(nome);
        setMorada(morada);
        if(jogos != null && !jogos.isEmpty()) {
            this.jogos.setAll(jogos);
        }
    }

    public Local(Long id, String nome, String morada) {
        this(id, nome, morada, new ArrayList<>());
    }

    public Local(LocalDto local) {
        this(local.getId(), local.getNome(), local.getMorada(), new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimpleStringProperty getNomeProperty() {
        return nome;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getMorada() {
        return morada.get();
    }

    public SimpleStringProperty getMoradaProperty() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada.set(morada);
    }

    public SimpleListProperty<Jogo> getJogosProperty() {
        return jogos;
    }

    public List<Jogo> getJogos() {
        return jogos.get();
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos.setAll(jogos);
    }

}
