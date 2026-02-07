package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pt.ul.fc.di.css.javafxexample.dto.EquipaDto;


/**
 * Entidade que representa uma equipa de futsal da FCUL. Contém o seu id (gerado
 * automáticamente), nome, lista de jogadores, lista de jogos e lista de pódios.
 */
public class Equipa {


    private Long id;
    private StringProperty nome = new SimpleStringProperty();
    private ListProperty<Jogador> jogadores = new SimpleListProperty<>();
    private ListProperty<Jogo> jogosCasa = new SimpleListProperty<>();
    private ListProperty<Jogo> jogosVisitante = new SimpleListProperty<>();
    private ListProperty<Podio> podios = new SimpleListProperty<>();
    private ObjectProperty<EstatisticasEquipa> estatisticas = new SimpleObjectProperty<>();
    private BooleanProperty softDeleted = new SimpleBooleanProperty(false);


    public Equipa(Long id, String nome, List<Jogador> jogadores, List<Jogo> jogosCasa, List<Jogo> jogosVisitante,
            List<Podio> podios, EstatisticasEquipa estatisticas, boolean softDeleted) {
        this.id = id;
        setNome(nome);
        if(jogadores != null && !jogadores.isEmpty()) {
            setJogadores(jogadores);
        }
        if(jogosCasa != null && !jogosCasa.isEmpty()) {
            setJogosCasa(jogosCasa);
        }
        if(jogosVisitante != null && !jogosVisitante.isEmpty()) {
            setJogosVisitante(jogosVisitante);
        }
        if(podios != null && !podios.isEmpty()) {
            setPodios(podios);
        }
        if(estatisticas != null) {
            setEstatisticas(estatisticas);
        }
        setSoftDeleted(softDeleted);
    }


    public Equipa(Long id, String nome) {
        this(id, nome, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new EstatisticasEquipa(), false);

    }

    public Equipa(EquipaDto dto) {
        this(dto.getId(), dto.getNome(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                dto.getEstatisticas(), dto.isSoftDeleted());
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StringProperty getNomeProperty() {
        return nome;
    }

    public ListProperty<Jogador> getJogadoresProperty() {
        return jogadores;
    }
    public ListProperty<Jogo> getJogosCasaProperty() {
        return jogosCasa;
    }

    public ListProperty<Jogo> getJogosVisitanteProperty() {
        return jogosVisitante;
    }

    public ListProperty<Podio> getPodiosProperty() {
        return podios;
    }

    public ObjectProperty<EstatisticasEquipa> getEstatisticasProperty() {
        return estatisticas;
    }

    public BooleanProperty getSoftDeletedProperty() {
        return softDeleted;
    }

    public boolean isSoftDeleted() {
        return softDeleted.get();
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted.set(softDeleted);
    }
    



    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }



    public List<Jogador> getJogadores() {
        return jogadores.get();
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores.setAll(jogadores);
    }



    public List<Jogo> getJogosCasa() {
        return jogosCasa.get();
    }

    public void setJogosCasa(List<Jogo> jogosCasa) {
        this.jogosCasa.setAll(jogosCasa);
    }



    public List<Jogo> getJogosVisitante() {
        return jogosVisitante.get();
    }

    public void setJogosVisitante(List<Jogo> jogosVisitante) {
        this.jogosVisitante.setAll(jogosVisitante);
    }



    public List<Podio> getPodios() {
        return podios.get();
    }

    public void setPodios(List<Podio> podios) {
        this.podios.setAll(podios);
    }



    public EstatisticasEquipa getEstatisticas() {
        return estatisticas.get();
    }

    public void setEstatisticas(EstatisticasEquipa estatisticas) {
        this.estatisticas.set(estatisticas);
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

    public void removeJogoCasa(Jogo jogo) {
        this.jogosCasa.remove(jogo);
    }



    public void addJogoVisitante(Jogo jogo) {
        this.jogosVisitante.add(jogo);
    }

    public void removeJogoVisitante(Jogo jogo) {
        this.jogosVisitante.remove(jogo);
    }



    public void addPodio(Podio podio) {
        this.podios.add(podio);
    }

    public void removePodio(Podio podio) {
        this.podios.remove(podio);
    }

    

    public void addEstatisticasJogosJogados(int jogos) {
        this.estatisticas.get().addJogosJogados(jogos);
    }

    public void removeEstatisticasJogosJogados(int jogos) {
        this.estatisticas.get().removeJogosJogados(jogos);
    }

    public void addEstatisticasGolosMarcados(int golos) {
        this.estatisticas.get().addGolosMarcados(golos);
    }

    public void removeEstatisticasGolosMarcados(int golos) {
        this.estatisticas.get().removeGolosMarcados(golos);
    }

    public void addEstatisticasGolosSofridos(int golos) {
        this.estatisticas.get().addGolosSofridos(golos);
    }

    public void removeEstatisticasGolosSofridos(int golos) {
        this.estatisticas.get().removeGolosSofridos(golos);
    }

    public void addEstatisticasCartoesAmarelos(int cartoes) {
        this.estatisticas.get().addCartoesAmarelos(cartoes);
    }

    public void removeEstatisticasCartoesAmarelos(int cartoes) {
        this.estatisticas.get().removeCartoesAmarelos(cartoes);
    }

    public void addEstatisticasCartoesVermelhos(int cartoes) {
        this.estatisticas.get().addCartoesVermelhos(cartoes);
    }

    public void removeEstatisticasCartoesVermelhos(int cartoes) {
        this.estatisticas.get().removeCartoesVermelhos(cartoes);
    }

    public void addEstatisticasVitorias(int vitorias) {
        this.estatisticas.get().addVitorias(vitorias);
    }

    public void removeEstatisticasVitorias(int vitorias) {
        this.estatisticas.get().removeVitorias(vitorias);
    }

    public void addEstatisticasEmpates(int empates) {
        this.estatisticas.get().addEmpates(empates);
    }

    public void removeEstatisticasEmpates(int empates) {
        this.estatisticas.get().removeEmpates(empates);
    }

    public void addEstatisticasDerrotas(int derrotas) {
        this.estatisticas.get().addDerrotas(derrotas);
    }

    public void removeEstatisticasDerrotas(int derrotas) {
        this.estatisticas.get().removeDerrotas(derrotas);
    }

    public void addEstatisticasJogos(int jogos) {
        this.estatisticas.get().addJogosJogados(jogos);
    }

    public void removeEstatisticasJogos(int jogos) {
        this.estatisticas.get().removeJogosJogados(jogos);
    }

    @Override
    public String toString() {
        return "Equipa: " + nome.get() + " id: " + id;
    }




    /**
     * Classe interna que tem armazenada o pódio da equipa: id, campeonato e
     * posição.
     */
    public static class Podio {

        private Campeonato campeonato;
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

    public static class EstatisticasEquipa {

        private int jogosJogados = 0;
        private int golosMarcados = 0;
        private int golosSofridos = 0;
        private int cartoesAmarelos = 0;
        private int cartoesVermelhos = 0;
        private int vitorias = 0;
        private int empates = 0;
        private int derrotas = 0;

        public EstatisticasEquipa() {
            // Apagar anotação
        }

        public int getJogosJogados() {
            return jogosJogados;
        }

        public void setJogosJogados(int jogosJogados) {
            this.jogosJogados = jogosJogados;
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

        public void addJogosJogados(int jogos) {
            this.jogosJogados += jogos;
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

        public void removeJogosJogados(int jogos) {
            this.jogosJogados -= jogos;
        }

    }
}
