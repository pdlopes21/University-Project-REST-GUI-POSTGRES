package pt.ul.fc.di.css.javafxexample.dto;

import java.util.Objects;

import pt.ul.fc.di.css.javafxexample.model.Arbitro;
import pt.ul.fc.di.css.javafxexample.model.Campeonato;
import pt.ul.fc.di.css.javafxexample.model.Equipa;
import pt.ul.fc.di.css.javafxexample.model.Jogador;
import pt.ul.fc.di.css.javafxexample.model.Jogador.Posicao;
import pt.ul.fc.di.css.javafxexample.model.Jogo;
import pt.ul.fc.di.css.javafxexample.model.Liga;
import pt.ul.fc.di.css.javafxexample.model.Local;

/**
 * Classe para criar detalhes simplificados dos elementos de outros Dtos
 */
public class EssentialDetails {

    private EssentialDetails() {
        // Construtor privado para evitar instância
    }

    /**
     * Classe interna com detalhes básicos do Àrbitro (id e nome)
     */
    public static class ArbitroEssentialDetails {

        private Long id;
        private String nome;
        private String username;

        public ArbitroEssentialDetails() {
        }

        public ArbitroEssentialDetails(Arbitro arbitro) {
            if (arbitro == null) {
                return;
            }
            this.id = arbitro.getId();
            this.nome = arbitro.getNome();
            this.username = arbitro.getUsername();
        }

        public ArbitroEssentialDetails(ArbitroDto dto) {
            this.id = dto.getId();
            this.nome = dto.getNome();
            this.username = dto.getUsername();
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


        @Override
        public String toString() {
            return "Arbitro: " + nome + ", ID: " + id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ArbitroEssentialDetails)) return false;
            ArbitroEssentialDetails that = (ArbitroEssentialDetails) o;
            return Objects.equals(id, that.id);
        }

    }

    /**
     * Classe interna com detalhes básicos do Campeonato (id e nome)
     */
    public static class CampeonatoEssentialDetails {

        private Long id;
        private String nome;
        private String epoca;

        public CampeonatoEssentialDetails() {
        }

        public CampeonatoEssentialDetails(Campeonato campeonato) {
            if (campeonato == null) {
                return;
            }
            this.id = campeonato.getId();
            this.nome = campeonato.getNome();
            this.epoca = campeonato.getEpoca();
        }

        public CampeonatoEssentialDetails(Liga campeonato) {
            if (campeonato == null) {
                return;
            }
            this.id = campeonato.getId();
            this.nome = campeonato.getNome();
            this.epoca = campeonato.getEpoca();
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

        public String getEpoca() {
            return epoca;
        }

        public void setEpoca(String epoca) {
            this.epoca = epoca;
        }

        

    }

    /**
     * Classe interna com detalhes básicos do Jogador (id, nome e posição no
     * campo).
     */
    public static class JogadorEssentialDetails {

        private Long id;
        private String nome;
        private String username;
        private Posicao posicao;

        public JogadorEssentialDetails() {
        }

        public JogadorEssentialDetails(Jogador jogador) {
            if (jogador == null) {
                return;
            }
            this.id = jogador.getId();
            this.nome = jogador.getNome();
            this.posicao = jogador.getPosicao();
            this.username = jogador.getUsername();
        }

        public JogadorEssentialDetails(JogadorDto dto) {
            this.id = dto.getId();
            this.nome = dto.getNome();
            this.posicao = dto.getPosicao();
            this.username = dto.getUsername();
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

        public Posicao getPosicao() {
            return posicao;
        }

        public void setPosicao(Posicao posicao) {
            this.posicao = posicao;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }



        @Override
        public String toString() {
            return "Jogador: " + nome + ", ID: " + id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JogadorEssentialDetails)) return false;
            JogadorEssentialDetails that = (JogadorEssentialDetails) o;
            return Objects.equals(id, that.id);
        }

    }

    /**
     * Classe interna com detalhes básicos do Jogo (id, detalhes de campeonato,
     * equipaCasa, equipaVisitante, àrbitro principal, data, hora, local e
     * estado de completude do jogo.
     *
     */
    public static class JogoEssentialDetails {

        private Long id;
        private CampeonatoEssentialDetails campeonato;
        private EquipaEssentialDetails equipaCasa;
        private EquipaEssentialDetails equipaVisitante;
        private boolean completado;

        public JogoEssentialDetails() {
        }

        public JogoEssentialDetails(Jogo jogo) {
            if (jogo == null) {
                return;
            }
            this.id = jogo.getId();
            this.campeonato = new CampeonatoEssentialDetails(jogo.getCampeonato());
            this.equipaCasa = new EquipaEssentialDetails(jogo.getEquipaCasa());
            this.equipaVisitante = new EquipaEssentialDetails(jogo.getEquipaVisitante());
            this.completado = jogo.getCompletado();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public boolean isCompletado() {
            return completado;
        }

        public void setCompletado(boolean completado) {
            this.completado = completado;
        }

        public CampeonatoEssentialDetails getCampeonato() {
            return campeonato;
        }

        public void setCampeonato(CampeonatoEssentialDetails campeonato) {
            this.campeonato = campeonato;
        }


        public EquipaEssentialDetails getEquipaCasa() {
            return equipaCasa;
        }

        public void setEquipaCasa(EquipaEssentialDetails equipaCasa) {
            this.equipaCasa = equipaCasa;
        }

        public EquipaEssentialDetails getEquipaVisitante() {
            return equipaVisitante;
        }

        public void setEquipaVisitante(EquipaEssentialDetails equipaVisitante) {
            this.equipaVisitante = equipaVisitante;
        }

        @Override
        public String toString() {
            return "Jogo: " + equipaCasa.getNome() + " vs " + equipaVisitante.getNome() +
                    ", ID: " + id;
        }

    }

    /**
     * Classe interna com informações básicas das equipas (id e nome)
     */
    public static class EquipaEssentialDetails {

        private Long id;
        private String nome;

        public EquipaEssentialDetails() {
        }

        public EquipaEssentialDetails(Equipa equipa) {
            if (equipa == null) {
                return;
            }
            this.id = equipa.getId();
            this.nome = equipa.getNome();
        }

        public EquipaEssentialDetails(EquipaDto equipaDto) {
            if (equipaDto == null) {
                return;
            }
            this.id = equipaDto.getId();
            this.nome = equipaDto.getNome();
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EquipaEssentialDetails)) return false;
            EquipaEssentialDetails that = (EquipaEssentialDetails) o;
            return Objects.equals(id, that.id) && nome.equals(that.nome);
        }

        @Override
        public String toString() {
            return "Equipa: " + nome + ", ID: " + id;
        }

    }

    public static class LocalEssentialDetails {

        private Long id;
        private String nome;
        private String morada;

        public LocalEssentialDetails() {
        }

        public LocalEssentialDetails(Local local) {
            if (local == null) {
                return;
            }
            this.id = local.getId();
            this.nome = local.getNome();
            this.morada = local.getMorada();
        }

        public LocalEssentialDetails(LocalDto local) {
            if (local == null) {
                return;
            }
            this.id = local.getId();
            this.nome = local.getNome();
            this.morada = local.getMorada();
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

    }

}
