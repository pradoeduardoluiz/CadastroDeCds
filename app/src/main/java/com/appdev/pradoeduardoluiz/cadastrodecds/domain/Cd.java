package com.appdev.pradoeduardoluiz.cadastrodecds.domain;

import java.io.Serializable;

public class Cd implements Serializable {

    public Cd(){

    }

    public Cd(String nome, String artista, int ano){
        this.nome = nome;
        this.artista = artista;
        this.ano = ano;
    }

    private long id;
    private String nome;
    private String artista;
    private int ano;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Nome album: " + getNome() + " Artista: " + getArtista() + " Ano: ";
    }
}
