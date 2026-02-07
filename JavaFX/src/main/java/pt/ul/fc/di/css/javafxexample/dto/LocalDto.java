package pt.ul.fc.di.css.javafxexample.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.Local;

public class LocalDto {

    private Long id;
    private String nome;
    private String morada;

    private List<JogoEssentialDetails> jogos = new ArrayList<>();

    public LocalDto() {
    }

    public LocalDto(Long id, String nome, String morada) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
    }

    public LocalDto(Long id, String nome, String morada, List<JogoEssentialDetails> jogos) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.jogos = jogos;
    }

    public LocalDto(Local local) {
        this.id = local.getId();
        this.nome = local.getNome();
        this.morada = local.getMorada();
        if(local.getJogos() != null && !local.getJogos().isEmpty()) {
            this.jogos = local.getJogos().stream()
                    .map(JogoEssentialDetails::new)
                    .toList();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public List<JogoEssentialDetails> getJogos() {
        return jogos;
    }

    public void setJogos(List<JogoEssentialDetails> jogos) {
        this.jogos = jogos;
    }

    
    
}
