package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import pt.ul.fc.di.css.javafxexample.dto.ArbitroDto;

public class Arbitro {


    private Long id;
    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private boolean certificado = false;
    private SimpleListProperty<Jogo> jogos = new SimpleListProperty<>();
    private int cartoes;
    private boolean softDeleted = false;

    public Arbitro(Long id, String nome, String username, boolean certificado, int cartoes, List<Jogo> jogos, boolean softDeleted) {
        this.id = id;
        setNome(nome);
        setUsername(username);
        setCertificado(certificado);
        setCartoes(cartoes);
        if (!jogos.isEmpty()) {
            this.jogos.setAll(jogos);
        }
        setSoftDeleted(softDeleted);
    }

    public Arbitro(Long id, String nome, String username) {
        this(id, nome, username, false, 0, new ArrayList<>(), false);
    }

    public Arbitro(ArbitroDto arbitro) {
        this(arbitro.getId(), arbitro.getNome(), arbitro.getUsername(), arbitro.getCertificado(), arbitro.getCartoes(), new ArrayList<>(), arbitro.isSoftDeleted());
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

    public SimpleStringProperty getUsernameProperty() {
        return username;
    }

    public SimpleListProperty<Jogo> getJogosProperty() {
        return jogos;
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



    public boolean isCertificado() {
        return certificado;
    }

    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }



    public List<Jogo> getJogos() {
        return jogos.get();
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos.setAll(jogos);
    }

    
    
    public int getCartoes() {
        return cartoes;
    }

    public void setCartoes(int cartoes) {
        this.cartoes = cartoes;
    }

    public void addCartao() {
        this.cartoes++;
    }

    public void removeCartao() {
        if (this.cartoes > 0) {
            this.cartoes--;
        }
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    
    public void addJogo(Jogo jogo) {
        this.jogos.add(jogo);
    }

    public void removeJogo(Jogo jogo) {
        this.jogos.remove(jogo);
    }

    @Override
    public String toString() {
        return "Arbitro:" + nome.get() + " Id: " +id;

    }

}

