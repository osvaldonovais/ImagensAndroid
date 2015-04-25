package br.zarb.imagens;

/**
 * Created by osvaldonovais on 16/04/15.
 */
public class Imagem {
    private String nome;
    private String path;
    private Boolean like;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }
}
