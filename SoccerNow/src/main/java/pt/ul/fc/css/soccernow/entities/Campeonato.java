package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.InheritanceType;

/**
 * Entidade que representa um campeonato de futsal da FCUL. Contém o seu id
 * (gerado automáticamente), nome,
 * lista de jogos, lista de equipas e lista de pontos.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String nome;

    @OneToMany(mappedBy = "campeonato")
    protected List<Jogo> jogos = new ArrayList<>();

    @ManyToMany
    protected List<Equipa> equipas = new ArrayList<>();

    @Embedded
    protected EstatisticasCampeonato estatisticas = new EstatisticasCampeonato();

    private String epoca;

    private boolean completado;

    public Campeonato() {
    }

    public Campeonato(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Equipa> getEquipas() {
        return equipas;
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas = equipas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public void addJogo(Jogo jogo) {
        this.jogos.add(jogo);
        jogo.setCampeonato(this);
    }

    public void removeJogo(Jogo jogo) {
        this.jogos.remove(jogo);
        jogo.setCampeonato(null);
    }

    public void addEquipa(Equipa equipa) {
        this.equipas.add(equipa);
    }

    public void removeEquipa(Equipa equipa) {
        this.equipas.remove(equipa);
    }

    public EstatisticasCampeonato getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticasCampeonato estatisticas) {
        this.estatisticas = estatisticas;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public boolean getCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public void addJogosCompletos(int jogosCompletos) {
        this.estatisticas.setJogosCompletos(this.estatisticas.getJogosCompletos() + jogosCompletos);
    }

    public void removeJogosCompletos(int jogosCompletos) {
        this.estatisticas.setJogosCompletos(this.estatisticas.getJogosCompletos() - jogosCompletos);
    }

    public void addTotalGolos(int totalGolos) {
        this.estatisticas.setTotalGolos(this.estatisticas.getTotalGolos() + totalGolos);
    }

    public void removeTotalGolos(int totalGolos) {
        this.estatisticas.setTotalGolos(this.estatisticas.getTotalGolos() - totalGolos);
    }

    public void addCartoesAmarelos(int cartoesAmarelos) {
        this.estatisticas.setCartoesAmarelos(this.estatisticas.getCartoesAmarelos() + cartoesAmarelos);
    }

    public void removeCartoesAmarelos(int cartoesAmarelos) {
        this.estatisticas.setCartoesAmarelos(this.estatisticas.getCartoesAmarelos() - cartoesAmarelos);
    }

    public void addCartoesVermelhos(int cartoesVermelhos) {
        this.estatisticas.setCartoesVermelhos(this.estatisticas.getCartoesVermelhos() + cartoesVermelhos);
    }

    public void removeCartoesVermelhos(int cartoesVermelhos) {
        this.estatisticas.setCartoesVermelhos(this.estatisticas.getCartoesVermelhos() - cartoesVermelhos);
    }

    



    @Embeddable
    public static class EstatisticasCampeonato {

        private int jogosCompletos;
        private int totalGolos;
        private int cartoesAmarelos;
        private int cartoesVermelhos;


        public EstatisticasCampeonato() {
            this.jogosCompletos = 0;
            this.totalGolos = 0;
            this.cartoesAmarelos = 0;
            this.cartoesVermelhos = 0;
            
        }

        public int getJogosCompletos() {
            return jogosCompletos;
        }

        public void setJogosCompletos(int jogosCompletos) {
            this.jogosCompletos = jogosCompletos;
        }

        public int getTotalGolos() {
            return totalGolos;
        }

        public void setTotalGolos(int totalGolos) {
            this.totalGolos = totalGolos;
        }

        public int getCartoesAmarelos() {
            return cartoesAmarelos;
        }

        public void setCartoesAmarelos(int cartoesAmarelos) {
            this.cartoesAmarelos = cartoesAmarelos;
        }

        public int getCartoesVermelhos() {
            return cartoesVermelhos;
        }

        public void setCartoesVermelhos(int cartoesVermelhos) {
            this.cartoesVermelhos = cartoesVermelhos;
        }

        public void addGolos(int golos) {
            this.totalGolos += golos;
        }

        public void addCartaoAmarelo(int cartoes) {
            this.cartoesAmarelos += cartoes;
        }

        public void addCartaoVermelho(int cartoes) {
            this.cartoesVermelhos += cartoes;
        }

        public void addJogoCompleto() {
            this.jogosCompletos++;
        }

        public void removeJogoCompleto() {
            this.jogosCompletos--;
        }

        public void removeGolos(int golos) {
            this.totalGolos -= golos;
        }

        public void removeCartaoAmarelo(int cartoes) {
            this.cartoesAmarelos -= cartoes;
        }

        public void removeCartaoVermelho(int cartoes) {
            this.cartoesVermelhos -= cartoes;
        }

        


    }

}
