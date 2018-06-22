package io.github.lucasduete.mobileHubApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    private String nome;
    private String fotoAutor;
    private String nomeAutor;
    private String descricao;


    public Repository() {

    }

    public Repository(String nome, String fotoAutor, String nomeAutor, String descricao) {
        this.nome = nome;
        this.fotoAutor = fotoAutor;
        this.nomeAutor = nomeAutor;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    @JsonSetter("full_name")
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoAutor() {
        return fotoAutor;
    }

    public void setFotoAutor(String fotoAutor) {
        this.fotoAutor = fotoAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getDescricao() {
        return descricao;
    }

    @JsonSetter("description")
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @JsonSetter("owner")
    public void setNomeAutor(Map<String, String> owner) {
        this.setNomeAutor(owner.get("login"));
        this.setFotoAutor(owner.get("avatar_url"));
    }
}
