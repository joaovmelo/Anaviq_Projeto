package com.example.projetolpiii.entidades;

import java.util.List;

public class Pergunta {
    private Integer id_pergunta;
    private String pergunta;
    private String explicacao;
    private List<Resposta> respostas;

    public Pergunta(){
    }

    public Integer getId_pergunta() {
        return id_pergunta;
    }

    public void setId_pergunta(Integer id_pergunta) {
        this.id_pergunta = id_pergunta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}
