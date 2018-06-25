package io.github.lucasduete.mobileHubApi.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    private int numero;
    private String nome;
    private String Descricao;
    private String nomeAutor;
    private String fotoAutor;

    public Issue() {

    }

    public Issue(int numero, String nome, String descricao, String nomeAutor, String fotoAutor) {
        this.numero = numero;
        this.nome = nome;
        Descricao = descricao;
        this.nomeAutor = nomeAutor;
        this.fotoAutor = fotoAutor;
    }

    @JsonGetter("numero")
    public int getNumero() {
        return numero;
    }

    @JsonSetter("number")
    public void setNumero(int numero) {
        this.numero = numero;
    }

    @JsonGetter("nome")
    public String getNome() {
        return nome;
    }

    @JsonSetter("title")
    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonGetter("descricao")
    public String getDescricao() {
        return Descricao;
    }

    @JsonSetter("body")
    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    @JsonGetter("nomeAutor")
    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    @JsonGetter("fotoAutor")
    public String getFotoAutor() {
        return fotoAutor;
    }

    public void setFotoAutor(String fotoAutor) {
        this.fotoAutor = fotoAutor;
    }

    @JsonSetter("user")
    public void setAutor(Map<String, String> owner) {
        this.setNomeAutor(owner.get("login"));
        this.setFotoAutor(owner.get("avatar_url"));
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (numero != issue.numero) return false;
        if (nome != null ? !nome.equals(issue.nome) : issue.nome != null) return false;
        if (Descricao != null ? !Descricao.equals(issue.Descricao) : issue.Descricao != null) return false;
        if (nomeAutor != null ? !nomeAutor.equals(issue.nomeAutor) : issue.nomeAutor != null) return false;
        return fotoAutor != null ? fotoAutor.equals(issue.fotoAutor) : issue.fotoAutor == null;
    }

    @Override
    public int hashCode() {

        int result = numero;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (Descricao != null ? Descricao.hashCode() : 0);
        result = 31 * result + (nomeAutor != null ? nomeAutor.hashCode() : 0);
        result = 31 * result + (fotoAutor != null ? fotoAutor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("Issue{");
        sb.append("numero=").append(numero);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", Descricao='").append(Descricao).append('\'');
        sb.append(", nomeAutor='").append(nomeAutor).append('\'');
        sb.append(", fotoAutor='").append(fotoAutor).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
