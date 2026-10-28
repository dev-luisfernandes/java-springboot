package br.com.fiap.entities;

public class Produtos {

    private int id;
    private String nome;
    private String descricao;
    private String urlImagem;
    private int quantidade;
    private String tipo;
    private double pontos;

    public Produtos() {}

    public Produtos(int id, String nome, String descricao, String urlImagem, int quantidade, String tipo, double pontos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.pontos = pontos;
    }

    // Getters och Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", urlImagem='" + urlImagem + '\'' +
                ", quantidade=" + quantidade +
                ", tipo='" + tipo + '\'' +
                ", pontos=" + pontos +
                '}';
    }
}