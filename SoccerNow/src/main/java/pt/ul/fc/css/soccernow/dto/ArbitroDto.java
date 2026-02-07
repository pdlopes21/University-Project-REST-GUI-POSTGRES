package pt.ul.fc.css.soccernow.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.css.soccernow.entities.Arbitro;
import pt.ul.fc.css.soccernow.entities.Jogo;

public class ArbitroDto {

    private Long id;
    private String nome;
    private String username;
    private boolean certificado;
    private List<JogoEssentialDetails> jogosMarcados = new ArrayList<>();
    private List<JogoEssentialDetails> jogosCompletos = new ArrayList<>();
    private int cartoes;
    private boolean softDeleted = false;

    /**
     * Dto que representa um árbitro com o seu id (gerado automáticamente), nome
     * e se tem certificado ou não.
     */
    public ArbitroDto() {
    }

    public ArbitroDto(Arbitro arbitro) {
        this.id = arbitro.getId();
        this.nome = arbitro.getNome();
        this.username = arbitro.getUsername();
        this.certificado = arbitro.getCertificado();
        if (arbitro.getJogos() != null) {
            for (Jogo jogo : arbitro.getJogos()) {
                if(jogo.getCompletado()) {
                    this.jogosCompletos.add(new JogoEssentialDetails(jogo));
                } else {
                    this.jogosMarcados.add(new JogoEssentialDetails(jogo));
                }
            }
        }
        this.cartoes = arbitro.getCartoes();
        this.softDeleted = arbitro.isSoftDeleted();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getCertificado() {
        return certificado;
    }

    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }

    public List<JogoEssentialDetails> getJogosMarcados() {
        return jogosMarcados;
    }

    public void setJogosMarcados(List<JogoEssentialDetails> jogosMarcados) {
        this.jogosMarcados = jogosMarcados;
    }

    public List<JogoEssentialDetails> getJogosCompletos() {
        return jogosCompletos;
    }

    public void setJogosCompletos(List<JogoEssentialDetails> jogosCompletos) {
        this.jogosCompletos = jogosCompletos;
    }

    public int getCartoes() {
        return cartoes;
    }

    public void setCartoes(int cartoes) {
        this.cartoes = cartoes;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }   

}
