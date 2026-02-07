package pt.ul.fc.css.soccernow.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.ul.fc.css.soccernow.dto.EssentialDetails.EquipaEssentialDetails;
import pt.ul.fc.css.soccernow.dto.EssentialDetails.JogoEssentialDetails;
import pt.ul.fc.css.soccernow.entities.Campeonato;
import pt.ul.fc.css.soccernow.entities.Equipa;
import pt.ul.fc.css.soccernow.entities.Jogo;
import pt.ul.fc.css.soccernow.entities.Liga;
import pt.ul.fc.css.soccernow.entities.Campeonato.EstatisticasCampeonato;


//Para evitar bloating, não colocamos os jogos. Em caso de necessidade de acesso, criar método em controller/Handler

public class CampeonatoDto {

    private Long id;
    private String tipo;
    private String nome;
    private String epoca;
    private List<EquipaEssentialDetails> equipas = new ArrayList<>();
    private List<JogoEssentialDetails> jogosCompletos = new ArrayList<>();
    private List<JogoEssentialDetails> jogosMarcados = new ArrayList<>();
    //Verificar se tabela é bem enviada
    private Map<String, Integer> tabela;
    //Só há ints, pelo que não será necessário criar um DTO para as estatísticas
    private EstatisticasCampeonato estatisticas;
    private boolean completado;



    public CampeonatoDto() {
    }

    public CampeonatoDto(Liga liga) {
        this.id = liga.getId();
        this.tipo = "Liga";
        this.nome = liga.getNome();
        if (liga.getEquipas() != null) {
            for (Equipa equipa : liga.getEquipas()) {
                this.equipas.add(new EquipaEssentialDetails(equipa));
            }
        }
        if (liga.getTabela() != null) {
            this.tabela = liga.getTabela();
        }

        if (liga.getJogos() != null) {
            for (Jogo jogo : liga.getJogos()) {
                if (jogo.getCompletado()) {
                    this.jogosCompletos.add(new JogoEssentialDetails(jogo));
                } else {
                    this.jogosMarcados.add(new JogoEssentialDetails(jogo));
                }
            }
        }

        if (liga.getEstatisticas() != null) {
            this.estatisticas = liga.getEstatisticas();
        }

        this.epoca = liga.getEpoca();
        this.completado = liga.getCompletado();



    }


    //TEMPORARY
    public CampeonatoDto(Campeonato campeonato) {
        this.id = campeonato.getId();
        this.tipo = "Campeonato";
        this.nome = campeonato.getNome();
        if (campeonato.getEquipas() != null) {
            for (Equipa equipa : campeonato.getEquipas()) {
                this.equipas.add(new EquipaEssentialDetails(equipa));
            }
        }

        if (campeonato.getJogos() != null) {
            for (Jogo jogo : campeonato.getJogos()) {
                if (jogo.getCompletado()) {
                    this.jogosCompletos.add(new JogoEssentialDetails(jogo));
                } else {
                    this.jogosMarcados.add(new JogoEssentialDetails(jogo));
                }
            }
        }

        if (campeonato.getEstatisticas() != null) {
            this.estatisticas = campeonato.getEstatisticas();
        }

        this.epoca = campeonato.getEpoca();
        this.completado = campeonato.getCompletado();
        
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<EquipaEssentialDetails> getEquipas() {
        return equipas;
    }

    public void setEquipas(List<EquipaEssentialDetails> equipas) {
        this.equipas = equipas;
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

    public Map<String, Integer> getTabela() {
        return tabela;
    }

    public void setTabela(Map<String, Integer> tabela) {
        this.tabela = tabela;
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

    

}
