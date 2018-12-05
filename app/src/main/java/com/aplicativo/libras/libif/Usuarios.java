package com.aplicativo.libras.libif;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by welli on 20/03/2018.
 */

        public class Usuarios {
            private String nome;
            private String email;
            private String senha;
            private String id;
            private int score;
            private String imagem;

            public Usuarios(){

            }

    public String getNome() {
        return nome;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("score", getScore());
        hashMapUsuario.put("imagem", getImagem());


        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("senha", getSenha());

        return hashMapUsuario;

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score;}

    public String getImagem() { return imagem; }
    public void setImagem(String imagem){ this.imagem = imagem; }
}
