package com.aplicativo.libras.libif;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaL extends AppCompatActivity {

    private ImageView imgA;
    private StorageReference storageReference;
    private BootstrapButton btnA;
    private BootstrapButton btnB;
    private BootstrapButton btnC;
    private BootstrapButton btnD;
    private TextView txtProgresso;
    private int indexAtual = 0;
    private Licao licao = new Licao();

    private String resposta;
    //private int score = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_l);
        imgA = findViewById(R.id.imgA);
        storageReference = Conexao.getStorageReference();
        setTitle("LIÇÕES");
        btnA  = (BootstrapButton) findViewById(R.id.btnA);
        btnB  = (BootstrapButton) findViewById(R.id.btnB);
        btnC  = (BootstrapButton) findViewById(R.id.btnC);
        btnD  = (BootstrapButton) findViewById(R.id.btnD);

        txtProgresso = (TextView) findViewById(R.id.txtProgresso);
        progressDialog = new ProgressDialog(TelaL.this);
        progressDialog.setTitle("Aguarde!");
        progressDialog.setMessage("Carregando imagem...");
        progressDialog.setCancelable(false);

        progressDialog.show();
        alteraLicao();




        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnA.getText()== resposta){
                    feedBackRespostaCorreta(resposta);
                }else{
                    feedBackRespostaErrada(resposta);
                }
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnB.getText()== resposta){
                    feedBackRespostaCorreta(resposta);
                }else{
                    feedBackRespostaErrada(resposta);
                }

            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnC.getText()== resposta){
                    feedBackRespostaCorreta(resposta);
                }else{

                    feedBackRespostaErrada(resposta);

                }

            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnD.getText()== resposta){
                    feedBackRespostaCorreta(resposta);
                }else{

                    feedBackRespostaErrada(resposta);

                }

            }
        });


    }
    private void feedBackRespostaCorreta(String resposta){

        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(TelaL.this);
        alerDialogBuilder.
                setTitle("EXELENTE!")
                .setMessage("Parabéns, Resposta correta!")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //startActivity(new Intent(getApplicationContext(), TelaL.class));
                                if(indexAtual < 26) {
                                    progressDialog.show();
                                    alteraLicao();
                                }else if(indexAtual==26){

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TelaL.this);
                                    alertDialogBuilder.
                                            setTitle("LIÇÕES CONCLUÍDAS!")
                                            .setMessage("Mostre que você aprendeu jogando o desafio!")
                                            .setCancelable(false)
                                            .setPositiveButton("REFAZER LIÇÕES",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            indexAtual = 0;
                                                            startActivity(new Intent(getApplicationContext(), TelaL.class));
                                                            finish();
                                                        }
                                                    })
                                            .setNegativeButton("JOGAR DESAFIO",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(getApplicationContext(), TelaD.class));
                                                            finish();
                                                        }
                                                    });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#2E8B57"));
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF0000"));

                                }
                            }
                        });
        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2E8B57"));

    }

    private void feedBackRespostaErrada(String resposta){

        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(TelaL.this);
        alerDialogBuilder.
                setTitle("NÃO FOI DESSA VEZ!")
                .setMessage("Resposta incorreta! A Letra correspondente a imagem é:  " +resposta)
                .setPositiveButton("SEGUIR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //startActivity(new Intent(getApplicationContext(), TelaL.class));
                                if(indexAtual < 26) {
                                    progressDialog.show();
                                    alteraLicao();
                                }else if(indexAtual==26){
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TelaL.this);
                                    alertDialogBuilder.
                                            setTitle("LIÇÕES CONCLUÍDAS!")
                                            .setMessage("Mostre que você aprendeu jogando o desafio!")
                                            .setCancelable(false)
                                            .setPositiveButton("REFAZER LIÇÕES",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            indexAtual = 0;
                                                            startActivity(new Intent(getApplicationContext(), TelaL.class));
                                                            finish();
                                                        }
                                                    })
                                            .setNegativeButton("JOGAR DESAFIO",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(getApplicationContext(), TelaD.class));
                                                            finish();
                                                        }
                                                    });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF0000"));
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2E8B57"));
                                }
                            }
                        });
        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2E8B57"));




    }

    private void alteraLicao(){

        txtProgresso.setText("QUESTÃO "+(indexAtual+1)+"/26");


        carregarImagem(indexAtual);
        btnA.setText(licao.getOpcao1(indexAtual));
        btnB.setText(licao.getOpcao2(indexAtual));
        btnC.setText(licao.getOpcao3(indexAtual));
        btnD.setText(licao.getOpcao4(indexAtual));


        resposta = licao.getResposta(indexAtual);


        if (indexAtual == 26){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TelaL.this);
            alertDialogBuilder.
                    setTitle("LIÇÕES CONCLUÍDAS!")
                    .setMessage("Mostre que você aprendeu jogando o desafio!")
                    .setCancelable(false)
                    .setPositiveButton("REFAZER LIÇÕES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    indexAtual = 0;
                                    startActivity(new Intent(getApplicationContext(), TelaL.class));
                                    finish();
                                }
                            })
                    .setNegativeButton("JOGAR DESAFIO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getApplicationContext(), TelaD.class));
                                    finish();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2E8B57"));
        }
        else{
            //Toast.makeText(getBaseContext(), "index: "+this.indexAtual, Toast.LENGTH_SHORT).show();
            this.indexAtual++;

        }

    }


    private void carregarImagem(int num){

        FirebaseStorage storage = FirebaseStorage.getInstance();

        String caminho = licao.getImagem(num);

        final StorageReference storageReference =
                storage.getReferenceFromUrl(caminho);

        final int height = 300;
        final int width = 300;

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progressDialog.dismiss();
                Picasso.get().load(uri).resize(width, height).centerCrop().into(imgA);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}