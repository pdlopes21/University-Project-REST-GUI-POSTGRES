package pt.ul.fc.di.css.javafxexample.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pt.ul.fc.di.css.javafxexample.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.di.css.javafxexample.model.Equipa;
import pt.ul.fc.di.css.javafxexample.model.Jogador;
import pt.ul.fc.di.css.javafxexample.model.Jogador.EstatisticasJogador;
import pt.ul.fc.di.css.javafxexample.model.Jogador.Posicao;


/**
 * Dto do Jogador com o seu id (gerado automáticamente), nome, posicao, lista de
 * equipas simplificada e estatísticas -a última ainda por implementar-
 */
public class JogadorDto {

    private Long id;
    private String nome;
    private String username;
    private Posicao posicao;
    private List<EquipaEssentialDetails> equipas = new ArrayList<>();
    //Como EstatisticasJogador não contém referências a outras entidades, podemos usar diretamente a classe EstatisticasJogador
    // em vez de criar uma nova classe DTO para ela.
    private EstatisticasJogador estatisticas = new EstatisticasJogador();
    private boolean softDeleted = false;
    private boolean locked = false;

    public JogadorDto() {
    }

    public JogadorDto(Jogador jogador) {
        this.id = jogador.getId();
        this.nome = jogador.getNome();
        this.username = jogador.getUsername();
        this.posicao = jogador.getPosicao();
        if (jogador.getEquipas() != null) {
            for (Equipa equipa : jogador.getEquipas()) {
                this.equipas.add(new EquipaEssentialDetails(equipa));
            }
        }
        this.estatisticas = jogador.getEstatisticas();
        this.softDeleted = jogador.isSoftDeleted();
        this.locked = jogador.isLocked();
    }

    public JogadorDto(Long id, String nome, String username, Posicao posicao) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.posicao = posicao;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public List<EquipaEssentialDetails> getEquipas() {
        return equipas;
    }

    public void setEquipas(List<EquipaEssentialDetails> equipas) {
        this.equipas = equipas;
    }

    public EstatisticasJogador getEstatisticas() {
        return estatisticas;
    }

    public void setEstatistica(EstatisticasJogador estatistica) {
        this.estatisticas = estatistica;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    

    /**
     * Estabelece se Jogador é igual a outro jogador.
     *
     * @return boolean indicando se é igual {@code true} ou não {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JogadorDto jogador = (JogadorDto) o;
        return id != null && id.equals(jogador.id);
    }

    /**
     * Obtem hashcode do jogador.
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
