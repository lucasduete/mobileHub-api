package io.github.lucasduete.mobileHubApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String nome;
    private String foto;
    private String username;
    private String biografia;
    private int quantRepositorios;
    private int quantEstrelas;
    private int quantSeguidores;
    private int quantSeguindo;

    public User() {

    }

    public User(String nome, String foto, String username, String biografia, int quantRepositorios,
                int quantEstrelas, int quantSeguidores, int quantSeguindo) {
        this.nome = nome;
        this.foto = foto;
        this.username = username;
        this.biografia = biografia;
        this.quantRepositorios = quantRepositorios;
        this.quantEstrelas = quantEstrelas;
        this.quantSeguidores = quantSeguidores;
        this.quantSeguindo = quantSeguindo;
    }

    public String getNome() {
        return nome;
    }

    @JsonSetter("name")
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    @JsonSetter("avatar_url")
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    @JsonSetter("login")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getBiografia() {
        return biografia;
    }

    @JsonSetter("bio")
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public int getQuantRepositorios() {
        return quantRepositorios;
    }

    @JsonSetter("public_repos")
    public void setQuantRepositorios(int quantRepositorios) {
        this.quantRepositorios = quantRepositorios;
    }

    public int getQuantEstrelas() {
        return quantEstrelas;
    }

    @JsonSetter("")
    public void setQuantEstrelas(int quantEstrelas) {
        this.quantEstrelas = quantEstrelas;
    }

    public int getQuantSeguidores() {
        return quantSeguidores;
    }

    @JsonSetter("followers")
    public void setQuantSeguidores(int quantSeguidores) {
        this.quantSeguidores = quantSeguidores;
    }

    public int getQuantSeguindo() {
        return quantSeguindo;
    }

    @JsonSetter("following")
    public void setQuantSeguindo(int quantSeguindo) {
        this.quantSeguindo = quantSeguindo;
    }
}
