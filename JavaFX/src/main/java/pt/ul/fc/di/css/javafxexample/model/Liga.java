package pt.ul.fc.di.css.javafxexample.model;

import java.util.HashMap;
import java.util.Map;

public class Liga extends Campeonato {

    private Map<String, Integer> tabela = new HashMap<>();


    public Liga(long id, String nome, String epoca) {
        super(id, nome, epoca);
    }


    public Map<String, Integer> getTabela() {
        return tabela;
    }

    public void setTabela(Map<String, Integer> tabela) {
        this.tabela = tabela;
    }


    @Override
    public void addEquipa(Equipa equipa) {
        if (!this.equipas.contains(equipa)) {
            this.equipas.add(equipa);
            this.tabela.put(equipa.getNome(), 0);
        } else {
            throw new IllegalArgumentException("Equipa Já adicionada.");
        }
    }

    @Override 
    public void removeEquipa(Equipa equipa) {
        if (this.equipas.contains(equipa)) {
            this.equipas.remove(equipa);
            this.tabela.remove(equipa.getNome());
        } else {
            throw new IllegalArgumentException("Equipa não existe.");
        }
    }

    public void addPontos(String nome, int pontos) {
        if (this.tabela.containsKey(nome)) {
            this.tabela.put(nome, this.tabela.get(nome) + pontos);
        } else {
            throw new IllegalArgumentException("Equipa não existe.");
        }
    }

    public void removePontos(String nome, int pontos) {
        if (this.tabela.containsKey(nome)) {
            this.tabela.put(nome, this.tabela.get(nome) - pontos);
        } else {
            throw new IllegalArgumentException("Equipa não existe.");
        }
    }

    //EXPERIMENTAL: UPDATE A ORDEM DA TABELA~
    //NAO TESTADO
    public void updateTabela() {
        this.tabela = new HashMap<>(this.tabela);
        this.tabela.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> this.tabela.put(x.getKey(), x.getValue()));
    }
    
}
