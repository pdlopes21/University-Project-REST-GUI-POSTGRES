package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Entidade que representa um jogador de futsal da FCUL. Contém o seu id (gerado automáticamente), nome,
 * posição no campo, equipas a que pertence, e estadísticas do jogador.
 */
@Entity
public class Jogador {

    public enum Posicao {
        GR, // Guarda-redes
        JC; // Jogador de campo


        public boolean equalsString(String value) {
            if (value == null) return false;
            return this.name().equalsIgnoreCase(value);
        }
    }



    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Posicao posicao;

    @ManyToMany(mappedBy = "jogadores")
    private List<Equipa> equipas = new ArrayList<>();

    @Embedded
    private EstatisticasJogador estatisticas = new EstatisticasJogador();

    private boolean locked = false; // Indica se o jogador está bloqueado para hard delete

    private boolean softDeleted = false; // Indica se o jogador foi eliminado
    // Soft delete é uma técnica onde o objeto não é removido da base de dados, mas marcado como eliminado.

    
    
    public Jogador() {}

    public Jogador(String username, String nome, Posicao posicao) {
        this.username = username;
        this.nome = nome;
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

    public List<Equipa> getEquipas() {
        return equipas;
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas = equipas;
    }

    public EstatisticasJogador getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticasJogador estatisticas) {
        this.estatisticas = estatisticas;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public void adicionarGolo() {
        this.estatisticas.adicionarGolo();
    }

    public void adicionarCartaoAmarelo() {
        this.estatisticas.adicionarCartaoAmarelo();
    }

    public void adicionarCartaoVermelho() {
        this.estatisticas.adicionarCartaoVermelho();
    }

    public void adicionarVitoria() {
        this.estatisticas.adicionarVitoria();
    }

    public void adicionarEmpate() {
        this.estatisticas.adicionarEmpate();
    }

    public void adicionarDerrota() {
        this.estatisticas.adicionarDerrota();
    }

    

    public void removerGolo() {
        this.estatisticas.removerGolo();
    }

    public void removerCartaoAmarelo() {
        this.estatisticas.removerCartaoAmarelo();
    }

    public void removerCartaoVermelho() {
        this.estatisticas.removerCartaoVermelho();
    }

    public void removerVitoria() {
        this.estatisticas.removerVitoria();
    }

    public void removerEmpate() {
        this.estatisticas.removerEmpate();
    }

    public void removerDerrota() {
        this.estatisticas.removerDerrota();
    }

    
    /**Imprime derrotas das estatisticas do jogador.
    * @return String com estatisticas.  */
    public String exibirEstatisticas() {
        return this.estatisticas.exibirEstatisticas();
    }


    /**Estabelece se Jogador é igual a outro jogador.
    @return boolean indicando se é igual {@code true} ou não {@code false}*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return id != null && id.equals(jogador.id); 
    }
    /**Obtem hashcode do jogador.
    @return int hashcode
    */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    
    /**Classe interna que tem armazenada as estadísticas do jogador: numero de jogos que jogou, golos, cartões
    amarelos e vermelhos, vitórias, empates e derrotas */
    @Embeddable
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


