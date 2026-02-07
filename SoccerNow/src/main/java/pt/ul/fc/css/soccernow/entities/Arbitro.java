package pt.ul.fc.css.soccernow.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Entidade que representa um árbitro de futsal da FCUL. Contém o seu id (gerado
 * automáticamente), nome, se tem certificado ou não e a lista de jogos que
 * participou.
 */
@Entity
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private boolean certificado = false;

    @ManyToMany(mappedBy = "arbitros")
    private List<Jogo> jogos = new ArrayList<>();

    private int cartoes;

    private boolean softDeleted = false;

    public Arbitro() {
    }

    public Arbitro(String nome) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getCertificado() {
        return certificado;
    }

    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    

    public void addJogo(Jogo jogo) {
        this.jogos.add(jogo);
    }

    public void removeJogo(Jogo jogo) {
        this.jogos.remove(jogo);
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
        this.cartoes--;
    }

    
    /**
     * Estabelece se Árbitro é igual a outro àrbitro.
     *
     * @return boolean indicando se é igual {@code true} ou não {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Arbitro arbitro = (Arbitro) o;
        return id != null && id.equals(arbitro.id);
    }

    /**
     * Obtem hashcode do àrbitro.
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
