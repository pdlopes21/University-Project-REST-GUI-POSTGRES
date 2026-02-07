package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Entidade que representa uma equipa de futsal da FCUL. Contém o seu id (gerado
 * automáticamente), nome, lista de jogadores, lista de jogos e lista de pódios.
 */
@Entity
public class Equipa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    private List<Jogador> jogadores = new ArrayList<>();

    @OneToMany(mappedBy = "equipaCasa")
    private List<Jogo> jogosCasa = new ArrayList<>();

    @OneToMany(mappedBy = "equipaVisitante")
    private List<Jogo> jogosVisitante = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "equipa_podios", joinColumns = @JoinColumn(name = "equipa_id"))
    private List<Podio> podios = new ArrayList<>();

    @Embedded
    private EstatisticasEquipa estatisticas = new EstatisticasEquipa();


    private boolean softDeleted = false; // Indica se a equipa foi eliminada (soft delete)



    public Equipa() {
    }

    public Equipa(String nome) {
        this.nome = nome;
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

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public List<Jogo> getJogosCasa() {
        return jogosCasa;
    }

    public void setJogosCasa(List<Jogo> jogosCasa) {
        this.jogosCasa = jogosCasa;
    }

    public List<Jogo> getJogosVisitante() {
        return jogosVisitante;
    }

    public void setJogosVisitante(List<Jogo> jogosVisitante) {
        this.jogosVisitante = jogosVisitante;
    }

    public List<Podio> getPodios() {
        return podios;
    }

    public void setPodios(List<Podio> podios) {
        this.podios = podios;
    }

    public void addPodio(Podio podio) {
        this.podios.add(podio);
    }

    public void removePodio(Podio podio) {
        this.podios.remove(podio);
    }

    public EstatisticasEquipa getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticasEquipa estatisticas) {
        this.estatisticas = estatisticas;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public void addJogador(Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador) {
        this.jogadores.remove(jogador);
    }

    public void addJogoCasa(Jogo jogo) {
        this.jogosCasa.add(jogo);
    }

    public void addJogoVisitante(Jogo jogo) {
        this.jogosVisitante.add(jogo);
    }

    public void removeJogoCasa(Jogo jogo) {
        this.jogosCasa.remove(jogo);
    }

    public void removeJogoVisitante(Jogo jogo) {
        this.jogosVisitante.remove(jogo);
    }


    public void addGolosMarcados(int golos) {
        this.estatisticas.addGolosMarcados(golos);
    }

    public void removeGolosMarcados(int golos) {
        this.estatisticas.removeGolosMarcados(golos);
    }

    public void addGolosSofridos(int golos) {
        this.estatisticas.addGolosSofridos(golos);
    }

    public void removeGolosSofridos(int golos) {
        this.estatisticas.removeGolosSofridos(golos);
    }

    public void addCartoesAmarelos(int cartoes) {
        this.estatisticas.addCartoesAmarelos(cartoes);
    }

    public void removeCartoesAmarelos(int cartoes) {
        this.estatisticas.removeCartoesAmarelos(cartoes);
    }

    public void addCartoesVermelhos(int cartoes) {
        this.estatisticas.addCartoesVermelhos(cartoes);
    }

    public void removeCartoesVermelhos(int cartoes) {
        this.estatisticas.removeCartoesVermelhos(cartoes);
    }

    public void addVitorias(int vitorias) {
        this.estatisticas.addVitorias(vitorias);
    }

    public void removeVitorias(int vitorias) {
        this.estatisticas.removeVitorias(vitorias);
    }

    public void addEmpates(int empates) {
        this.estatisticas.addEmpates(empates);
    }

    public void removeEmpates(int empates) {
        this.estatisticas.removeEmpates(empates);
    }

    public void addDerrotas(int derrotas) {
        this.estatisticas.addDerrotas(derrotas);
    }

    public void removeDerrotas(int derrotas) {
        this.estatisticas.removeDerrotas(derrotas);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipa)) return false;

        Equipa equipa = (Equipa) o;

        return this.id == equipa.getId();
    }

    

    /**
     * Classe interna que tem armazenada o pódio da equipa: id, campeonato e
     * posição.
     */
    @Embeddable
    public static class Podio {

        @ManyToOne
        @JoinColumn(name = "campeonato_id", nullable = false)
        private Campeonato campeonato;

        @Column(nullable = false)
        private int posicao; // 1 = ouro, 2 = prata, 3 = bronze

        public Podio() {
        }

        public Podio(Campeonato campeonato, int posicao) {
            this.campeonato = campeonato;
            this.posicao = posicao;
        }

        public Campeonato getCampeonato() {
            return campeonato;
        }

        public void setCampeonato(Campeonato campeonato) {
            this.campeonato = campeonato;
        }

        public int getPosicao() {
            return posicao;
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }

    }

    @Embeddable
    public static class EstatisticasEquipa {

        private int golosMarcados = 0;
        private int golosSofridos = 0;
        private int cartoesAmarelos = 0;
        private int cartoesVermelhos = 0;
        private int vitorias = 0;
        private int empates = 0;
        private int derrotas = 0;

        public EstatisticasEquipa() {
            //Empty constructor for JPA
        }


        public int getGolosMarcados() {
            return golosMarcados;
        }

        public void setGolosMarcados(int golosMarcados) {
            this.golosMarcados = golosMarcados;
        }

        public int getGolosSofridos() {
            return golosSofridos;
        }

        public void setGolosSofridos(int golosSofridos) {
            this.golosSofridos = golosSofridos;
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

        public int getVitorias() {
            return vitorias;
        }

        public void setVitorias(int vitorias) {
            this.vitorias = vitorias;
        }

        public int getEmpates() {
            return empates;
        }

        public void setEmpates(int empates) {
            this.empates = empates;
        }

        public int getDerrotas() {
            return derrotas;
        }

        public void setDerrotas(int derrotas) {
            this.derrotas = derrotas;
        }

        public void addGolosMarcados(int golos) {
            this.golosMarcados += golos;
        }

        public void addGolosSofridos(int golos) {
            this.golosSofridos += golos;
        }

        public void addCartoesAmarelos(int cartoes) {
            this.cartoesAmarelos += cartoes;
        }

        public void addCartoesVermelhos(int cartoes) {
            this.cartoesVermelhos += cartoes;
        }

        public void addVitorias(int vitorias) {
            this.vitorias += vitorias;
        }

        public void addEmpates(int empates) {
            this.empates += empates;
        }

        public void addDerrotas(int derrotas) {
            this.derrotas += derrotas;
        }


        public void removeGolosMarcados(int golos) {
            this.golosMarcados -= golos;
        }

        public void removeGolosSofridos(int golos) {
            this.golosSofridos -= golos;
        }

        public void removeCartoesAmarelos(int cartoes) {
            this.cartoesAmarelos -= cartoes;
        }

        public void removeCartoesVermelhos(int cartoes) {
            this.cartoesVermelhos -= cartoes;
        }

        public void removeVitorias(int vitorias) {
            this.vitorias -= vitorias;
        }

        public void removeEmpates(int empates) {
            this.empates -= empates;
        }

        public void removeDerrotas(int derrotas) {
            this.derrotas -= derrotas;
        }


    }
}
