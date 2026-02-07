package pt.ul.fc.css.soccernow.dto;

import java.util.*;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.css.soccernow.entities.Local;

public class LocalDto {

    private Long id;
    private String nome;
    private String morada;

    private List<JogoEssentialDetails> jogos = new ArrayList<>();

    public LocalDto() {
    }

    public LocalDto(Local local) {
        this.id = local.getId();
        this.nome = local.getNome();
        this.morada = local.getMorada();
        if (local.getJogos() != null) {
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
