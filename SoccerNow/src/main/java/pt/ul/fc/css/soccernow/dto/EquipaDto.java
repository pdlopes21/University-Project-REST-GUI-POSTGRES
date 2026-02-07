package pt.ul.fc.css.soccernow.dto;

import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.CampeonatoEssentialDetails;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogadorEssentialDetails;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Equipa.EstatisticasEquipa;
import pt.ul.fc.css.soccernow.entities.Equipa.Podio;
import pt.ul.fc.css.soccernow.entities.Jogador;
import pt.ul.fc.css.soccernow.entities.Jogo;

/**
 * Dto da Equipa com o seu id (gerado automáticamente), nome, lista de detalhes
 * essenciais do jogador, lista de detalhes essenciais do jogo, lista de pódios
 * e estatisticas.
 */
public class EquipaDto {

    private Long id;
    private String nome;
    private List<JogadorEssentialDetails> jogadores = new ArrayList<>();
    private List<JogoEssentialDetails> jogosCompletos = new ArrayList<>();
    private List<JogoEssentialDetails> jogosMarcados = new ArrayList<>();
    private List<PodioDto> podios = new ArrayList<>();
     //Como EstatisticasEquipa não contém referências a outras entidades, podemos usar diretamente a classe EstatisticasEquipa
    // em vez de criar uma nova classe DTO para ela.
    private EstatisticasEquipa estatisticas;
    private boolean softDeleted = false; 

    public EquipaDto() {
    }

    public EquipaDto(Equipa equipa) {
        this.id = equipa.getId();
        this.nome = equipa.getNome();
        if (equipa.getJogadores() != null) {
            for (Jogador jogador : equipa.getJogadores()) {
                this.jogadores.add(new JogadorEssentialDetails(jogador));
            }
        }
        if (equipa.getJogosCasa() != null || equipa.getJogosVisitante() != null) {
            for (Jogo jogo : equipa.getJogosCasa()) {
                if(jogo.getCompletado()) {
                    this.jogosCompletos.add(new JogoEssentialDetails(jogo));
                } else {
                    this.jogosMarcados.add(new JogoEssentialDetails(jogo));
                }
            }
            for (Jogo jogo : equipa.getJogosVisitante()) {
                if(jogo.getCompletado()) {
                    this.jogosCompletos.add(new JogoEssentialDetails(jogo));
                } else {
                    this.jogosMarcados.add(new JogoEssentialDetails(jogo));
                }
            }

        }
        if (equipa.getPodios() != null) {
            for (Podio podio : equipa.getPodios()) {
                this.podios.add(new PodioDto(podio));
            }
        }
        if (equipa.getEstatisticas() != null) {
            this.estatisticas = equipa.getEstatisticas();
        }
        this.softDeleted = equipa.isSoftDeleted();

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

    public List<JogadorEssentialDetails> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<JogadorEssentialDetails> jogadores) {
        this.jogadores = jogadores;
    }

    public EstatisticasEquipa getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticasEquipa estatisticas) {
        this.estatisticas = estatisticas;
    }

    public void addJogador(JogadorEssentialDetails jogador) {
        this.jogadores.add(jogador);
    }

    public void removeJogador(JogadorEssentialDetails jogador) {
        this.jogadores.remove(jogador);
    }

    public List<JogoEssentialDetails> getJogosCompletos() {
        return jogosCompletos;
    }

    public void setJogosCompletos(List<JogoEssentialDetails> jogosCompletos) {
        this.jogosCompletos = jogosCompletos;
    }

    public List<JogoEssentialDetails> getJogosMarcados() {
        return jogosMarcados;
    }

    public void setJogosMarcados(List<JogoEssentialDetails> jogosMarcados) {
        this.jogosMarcados = jogosMarcados;
    }

    public List<PodioDto> getPodios() {
        return podios;
    }

    public void setPodios(List<PodioDto> podios) {
        this.podios = podios;
    }

    public void addPodio(PodioDto podio) {
        this.podios.add(podio);
    }

    public void removePodio(PodioDto podio) {
        this.podios.remove(podio);
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    /**
     * Classe interna que representa o Dto do Pódio. Possui detalhes de
     * campeonato e posição.
     */
    public static class PodioDto {

        private CampeonatoEssentialDetails campeonato;
        private int posicao;

        public PodioDto() {
        }

        public PodioDto(Podio podio) {
            this.campeonato = new CampeonatoEssentialDetails(podio.getCampeonato());
            this.posicao = podio.getPosicao();
        }

        public CampeonatoEssentialDetails getCampeonato() {
            return campeonato;
        }

        public void setCampeonato(CampeonatoEssentialDetails campeonato) {
            this.campeonato = campeonato;
        }

        public int getPosicao() {
            return posicao;
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }

    }

}
