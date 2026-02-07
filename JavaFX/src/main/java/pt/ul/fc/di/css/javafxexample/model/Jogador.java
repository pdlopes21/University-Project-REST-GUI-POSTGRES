package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pt.ul.fc.di.css.javafxexample.dto.JogadorDto;

public class Jogador {

    public enum Posicao { 
        JC, GR

    }

    private long id;
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty username = new SimpleStringProperty();
    private ObjectProperty<Posicao> posicao = new SimpleObjectProperty<>();
    private ListProperty<Equipa> equipas = new SimpleListProperty<>();
    private ObjectProperty<EstatisticasJogador> estatisticas = new SimpleObjectProperty<>();
    private boolean softDeleted = false;
    private boolean isLocked = false;

    public Jogador(long id, String nome, String username, Posicao posicao, List<Equipa> equipas, boolean softDeleted, boolean isLocked) {
        this.id = id;
        setNome(nome);
        setUsername(username);
        setPosicao(posicao);
        if(!equipas.isEmpty()) {
            this.equipas.setAll(equipas);
        }
        this.estatisticas.set(new EstatisticasJogador());
        this.softDeleted = softDeleted;
        this.isLocked = isLocked;
    }

    public Jogador(long id, String nome, String username, Posicao posicao) {
        this(id, nome, username, posicao, new ArrayList<>(), false, false);
    }

    public Jogador(JogadorDto jogador) {
        this(jogador.getId(), jogador.getNome(), jogador.getUsername(), jogador.getPosicao(), new ArrayList<>(), jogador.isSoftDeleted(), jogador.isLocked());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public StringProperty getNomeProperty() {
        return nome;
    }

    public StringProperty getUsernameProperty() {
        return username;
    }

    public ObjectProperty<Posicao> getPosicaoProperty() {
        return posicao;
    }

    public ListProperty<Equipa> getEquipasProperty() {
        return equipas;
    }

    public ObjectProperty<EstatisticasJogador> getEstatisticasProperty() {
        return estatisticas;
    }



    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }



    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }



    public Posicao getPosicao() {
        return posicao.get();
    }

    public void setPosicao(Posicao posicao) {
        this.posicao.set(posicao);
    }
    
    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }



    public List<Equipa> getEquipas() {
        return equipas.get();
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas.setAll(equipas);
    }

    public void adicionarEquipa(Equipa equipa) {
        this.equipas.add(equipa);
    }

    public void removerEquipa(Equipa equipa) {
        this.equipas.remove(equipa);
    }



    public EstatisticasJogador getEstatisticas() {
        return estatisticas.get();
    }

    public void setEstatisticas(EstatisticasJogador estatisticas) {
        this.estatisticas.set(estatisticas);
    }


    public void adicionarGolo() {
        this.estatisticas.get().adicionarGolo();
    }

    public void adicionarCartaoAmarelo() {
        this.estatisticas.get().adicionarCartaoAmarelo();
    }

    public void adicionarCartaoVermelho() {
        this.estatisticas.get().adicionarCartaoVermelho();
    }

    public void adicionarVitoria() {
        this.estatisticas.get().adicionarVitoria();
    }

    public void adicionarEmpate() {
        this.estatisticas.get().adicionarEmpate();
    }

    public void adicionarDerrota() {
        this.estatisticas.get().adicionarDerrota();
    }


    public void removerGolo() {
        this.estatisticas.get().removerGolo();
    }

    public void removerCartaoAmarelo() {
        this.estatisticas.get().removerCartaoAmarelo();
    }

    public void removerCartaoVermelho() {
        this.estatisticas.get().removerCartaoVermelho();
    }

    public void removerVitoria() {
        this.estatisticas.get().removerVitoria();
    }

    public void removerEmpate() {
        this.estatisticas.get().removerEmpate();
    }

    public void removerDerrota() {
        this.estatisticas.get().removerDerrota();
    }



    @Override
    public String toString() {
        return "Jogador: " + nome.get() + " id: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Jogador jogador = (Jogador) obj;

        return id == jogador.id;
    }

    public static class EstatisticasJogador {

        private int golos = 0;
        private int cartoesAmarelos = 0;
        private int cartoesVermelhos= 0;
        private int vitorias = 0;
        private int empates = 0;
        private int derrotas = 0;


        public EstatisticasJogador() { /*Empty Constructor */ }


        public int getGolos() {
            return golos;
        }

        public void setGolos(int golos) {
            this.golos = golos;
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


        public void adicionarGolo() {
            this.golos++;
        }

        public void adicionarCartaoAmarelo() {
            this.cartoesAmarelos++;
        }

        public void adicionarCartaoVermelho() {
            this.cartoesVermelhos++;
        }
 
        public void adicionarVitoria() {
            this.vitorias++;
        }

        public void adicionarEmpate() {
            this.empates++;
        }
  
        public void adicionarDerrota() {
            this.derrotas++;
        }

        public int totalJogosJogados() {
            // Método para obter o número total de jogos jogados
            return this.vitorias + this.empates + this.derrotas;
        }

        public void removerGolo() {
            if (this.golos > 0) {
                this.golos--;
            } else {
                throw new IllegalArgumentException("Número de golos não pode ser negativo.");
            }
        }


        public void removerCartaoAmarelo() {
            if (this.cartoesAmarelos > 0) {
                this.cartoesAmarelos--;
            } else {
                throw new IllegalArgumentException("Número de cartões amarelos não pode ser negativo.");
            }
        }
  

        public void removerCartaoVermelho() {
            if (this.cartoesVermelhos > 0) {
                this.cartoesVermelhos--;
            } else {
                throw new IllegalArgumentException("Número de cartões vermelhos não pode ser negativo.");
            }
        }
  

        public void removerVitoria() {
            if (this.vitorias > 0) {
                this.vitorias--;
            } else {
                throw new IllegalArgumentException("Número de vitórias não pode ser negativo.");
            }
        }


        public void removerEmpate() {
            if (this.empates > 0) {
                this.empates--;
            } else {
                throw new IllegalArgumentException("Número de empates não pode ser negativo.");
            }
        }


        public void removerDerrota() {
            if (this.derrotas > 0) {
                this.derrotas--;
            } else {
                throw new IllegalArgumentException("Número de derrotas não pode ser negativo.");
            }
        }

        /**Exibe estatisticas.
        @return String estatisticas*/
        public String exibirEstatisticas() {
            return " Golos: " + golos + 
                ", Cartões Amarelos: " + cartoesAmarelos + 
                ", Cartões Vermelhos: " + cartoesVermelhos +
                ", Vitórias: " + vitorias + 
                ", Empates: " + empates +
                ", Derrotas: " + derrotas;
        }
    }
    
}
