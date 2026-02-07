package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import pt.ul.fc.di.css.javafxexample.dto.CampeonatoDto;

public class Campeonato {

    protected Long id;
    protected SimpleStringProperty nome = new SimpleStringProperty();
    protected SimpleListProperty<Jogo> jogos = new SimpleListProperty<>();
    protected SimpleListProperty<Equipa> equipas = new SimpleListProperty<>();
    protected SimpleObjectProperty<EstatisticasCampeonato> estatisticas = new SimpleObjectProperty<>();
    private SimpleStringProperty epoca = new SimpleStringProperty();
    private boolean completado;

    public Campeonato(Long id, String nome, String epoca, boolean completado, EstatisticasCampeonato estatisticas,
            List<Jogo> jogos, List<Equipa> equipas) {
        this.id = id;
        setNome(nome);
        setEpoca(epoca);
        setCompletado(completado);
        
        if(estatisticas !=null) {
            this.estatisticas.set(estatisticas);
        } 

        if(jogos != null && !jogos.isEmpty()) {
            setJogos(jogos);
        }

        if(equipas != null && !equipas.isEmpty()) {
            setEquipas(equipas);
        }
       
    }

    public Campeonato(Long id, String nome, String epoca) {
        this(id, nome, epoca, false, new EstatisticasCampeonato(), new ArrayList<>(),
                new ArrayList<>());
    }

    public Campeonato(CampeonatoDto campeonatoDto) {
        this(campeonatoDto.getId(), campeonatoDto.getNome(), campeonatoDto.getEpoca(),
                campeonatoDto.getCompletado(), campeonatoDto.getEstatisticas(),
                new ArrayList<>(), new ArrayList<>());
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

    public SimpleStringProperty getEpocaProperty() {
        return epoca;
    }

    public SimpleListProperty<Jogo> getJogosProperty() {
        return jogos;
    }

    public SimpleListProperty<Equipa> getEquipasProperty() {
        return equipas;
    }

    public SimpleObjectProperty<EstatisticasCampeonato> getEstatisticasProperty() {
        return estatisticas;
    }



    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }



    public String getEpoca() {
        return epoca.get();
    }

    public void setEpoca(String epoca) {
        this.epoca.set(epoca);
    }

    
    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    

    public List<Equipa> getEquipas() {
        return equipas.get();
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas.setAll(equipas);
    }



    public List<Jogo> getJogos() {
        return jogos.get();
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos.setAll(jogos);
    }



    public EstatisticasCampeonato getEstatisticas() {
        return estatisticas.get();
    }

    public void setEstatisticas(EstatisticasCampeonato estatisticas) {
        this.estatisticas.set(estatisticas);
    }

    




    public void addJogo(Jogo jogo) {
        this.jogos.add(jogo);
    }

    public void removeJogo(Jogo jogo) {
        this.jogos.remove(jogo);
    }

    public void addEquipa(Equipa equipa) {
        this.equipas.add(equipa);
    }

    public void removeEquipa(Equipa equipa) {
        this.equipas.remove(equipa);
    }


    public void addJogoCompleto() {
        this.estatisticas.get().addJogoCompleto();
    }

    public void removeJogoCompleto() {
        this.estatisticas.get().removeJogoCompleto();
    }

    public void addGolos(int golos) {
        this.estatisticas.get().addGolos(golos);
    }

    public void removeGolos(int golos) {
        this.estatisticas.get().removeGolos(golos);
    }


    public void addCartaoAmarelo(int cartoes) {
        this.estatisticas.get().addCartaoAmarelo(cartoes);
    }

    public void removeCartaoAmarelo(int cartoes) {
        this.estatisticas.get().removeCartaoAmarelo(cartoes);
    }

    public void addCartaoVermelho(int cartoes) {
        this.estatisticas.get().addCartaoVermelho(cartoes);
    }

    public void removeCartaoVermelho(int cartoes) {
        this.estatisticas.get().removeCartaoVermelho(cartoes);
    }


    @Override
    public String toString() {
        return "Campeonato: " + nome.get() + ", Época: " + epoca.get() + ", ID: " + id;
    }


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

