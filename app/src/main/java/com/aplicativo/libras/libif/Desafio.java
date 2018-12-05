package com.aplicativo.libras.libif;

public class Desafio {

    public String aLicao[]= {
            "1 - Escolha a opção correspondente a imagem!",
            "2 - Escolha a opção correspondente a imagem!",
            "3 - Escolha a opção correspondente a imagem!",
            "4 - Escolha a opção correspondente a imagem!",
            "5 - Escolha a opção correspondente a imagem!",
            "6 - Escolha a opção correspondente a imagem!"
    };

    private String aOpcoes[][]= {
            {"A","B","C","D"},
            {"C","A","R","B"},
            {"Z","C","I","J"},
            {"S","D","F","O"},
            {"S","R","E","L"},
            {"O","F","T","X"},
            {"A","J","C","G"},
            {"H","I","X","O"},
            {"S","I","M","T"},
            {"Y","K","R","J"},
            {"I","Y","A","K"},
            {"C","L","X","T"},
            {"L","R","F","M"},
            {"G","A","Y","N"},
            {"A","W","O","M"},
            {"P","K","H","T"},
            {"Q","I","R","H"},
            {"O","R","A","B"},
            {"G","H","S","C"},
            {"S","T","K","W"},
            {"R","A","U","F"},
            {"V","S","G","Z"},
            {"C","W","T","S"},
            {"L","X","K","A"},
            {"W","T","C","Y"},
            {"Z","A","M","C"}
    };

    private String aResposta[]={
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
    };


    public String imagens[]={
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_a.jpg?alt=media&token=ff4216d3-873c-41e2-9c0a-1f40bb553e42",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_b.jpg?alt=media&token=e5b38239-a289-4a6f-b51e-4087967aef3e",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_c.jpg?alt=media&token=b5f64881-ecf6-42ff-b50f-e37d62f9a74e",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_d.jpg?alt=media&token=1385f133-7845-4c5a-9e40-3dc6b2b64ba0",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_e.jpg?alt=media&token=40ae3591-b08e-4fbd-91c7-2aded59c2d06",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_f.jpg?alt=media&token=3b8def8c-6c48-42ce-ba66-39505ab11323",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_g.jpg?alt=media&token=d0758cb0-2e9f-4953-ae01-c2e66c353d2e",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_h.jpg?alt=media&token=acbae951-2dde-448d-9d9f-26361f9259e5",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_i.jpg?alt=media&token=6468ce19-574c-41c6-99a8-870f2853c119",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_j.jpg?alt=media&token=96e0a234-265a-4632-9882-dc3463a50746",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_k.jpg?alt=media&token=7e6d71f4-4cea-45ed-938b-5942ab0c724f",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_l.jpg?alt=media&token=8a8c862d-d57f-4db8-85c2-134c2492cd6d",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_m.jpg?alt=media&token=1c7dd545-7a94-4b19-a10b-9cd670a2dcbe",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_n.jpg?alt=media&token=bb82f9c1-407f-4e51-b656-9bbfce6accd1",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_o.jpg?alt=media&token=dd43b39b-3f6c-4070-8258-4428524b7a84",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_p.jpg?alt=media&token=4c279dd5-8999-44b2-85ac-f816ae5372e1",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_q.jpg?alt=media&token=24b5e0b8-0164-48ed-9640-31be2054ce36",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_r.jpg?alt=media&token=81f18bd1-3998-428b-9680-b5bc81e3f9dd",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_s.jpg?alt=media&token=5f60b26e-d8d9-46b6-9949-86609631891c",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_t.jpg?alt=media&token=332a6c8c-b896-423e-8789-f4fb4937e286",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_u.jpg?alt=media&token=060fd9e3-c0ad-4802-ac82-9ce0ef8adfc8",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_v.jpg?alt=media&token=d7d53e1c-48f4-4c81-b0b4-6c152de3ed95",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_w.jpg?alt=media&token=e4f73e46-34cf-49ca-b9a6-ca8446813727",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_x.jpg?alt=media&token=5d7edfe3-e8ae-44b2-bf35-e0a67c8d66b3",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_y.jpg?alt=media&token=6da5e747-8d5b-46a3-b852-87c1e072306e",
            "https://firebasestorage.googleapis.com/v0/b/libif-31bdb.appspot.com/o/imagens%2Fimages_z.jpg?alt=media&token=34458817-3101-4ddb-b974-e2e4990e981b"

    };


    public String getPergunta(int a){
        String pergunta = aLicao[a];
        return pergunta;
    }

    public String getImagem(int a){
        String imagem = imagens[a];
        return imagem;
    }

    public String getOpcao1(int a){
        String opcao = aOpcoes[a][0];
        return opcao;
    }

    public String getOpcao2(int a){
        String opcao = aOpcoes[a][1];
        return opcao;
    }

    public String getOpcao3(int a){
        String opcao = aOpcoes[a][2];
        return opcao;
    }

    public String getOpcao4(int a){
        String opcao = aOpcoes[a][3];
        return opcao;
    }

    public String getResposta(int a){
        String resposta = aResposta[a];
        return resposta;

    }
}
