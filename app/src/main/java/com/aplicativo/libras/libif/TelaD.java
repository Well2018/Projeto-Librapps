package com.aplicativo.libras.libif;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TelaD extends AppCompatActivity {

    private ImageView imgA;
    private StorageReference storageReference;
    private BootstrapButton btnA;
    private BootstrapButton btnB;
    private BootstrapButton btnC;
    private BootstrapButton btnD;
    private TextView txtScore;
private String nomeUser, imagemUser;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    DatabaseReference referenciaFirebase;
    private Desafio licao = new Desafio();
    private String resposta;
    private int score = 0;
    private int imagemLength = licao.imagens.length;
    Usuarios usuarios;
    Random r;
    String email,pegapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);
         usuarios = new Usuarios();
        FirebaseAuth autenticacao;
        autenticacao = FirebaseAuth.getInstance();
        email = autenticacao.getCurrentUser().getEmail();
       usuarios.setEmail(email);

        referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        //storage = FirebaseStorage.getInstance();
        referenciaFirebase.child("usuários").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String scorebd = postSnapshot.child("score").getValue().toString();
                    pegapt=scorebd;

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        r = new Random();
        imgA = findViewById(R.id.imgA);
        storageReference = Conexao.getStorageReference();
        //carregarImagem();
        setTitle("DESAFIOS");
        btnA  = (BootstrapButton) findViewById(R.id.btnA);
        btnB  = (BootstrapButton) findViewById(R.id.btnB);
        btnC  = (BootstrapButton) findViewById(R.id.btnC);
        btnD  = (BootstrapButton) findViewById(R.id.btnD);

        txtScore = (TextView) findViewById(R.id.txtScore);
        //txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        txtScore.setText("SCORE:"+ score);

        alteraLicao(r.nextInt(imagemLength));


        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnA.getText()== resposta){
                    score+=10;
                    txtScore.setText("SCORE:"+ score);
                    alteraLicao(r.nextInt(imagemLength));
                    //attScore(score);
                }else{

                    gameOver();

                }
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnB.getText()== resposta){
                    score+=10;
                    txtScore.setText("SCORE:"+ score);
                    alteraLicao(r.nextInt(imagemLength));
                    //attScore(score);
                }else{

                    gameOver();

                }

            }
        });
        referenciaFirebase.child("usuários").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nome = postSnapshot.child("nome").getValue().toString();
                    String imagem = postSnapshot.child("imagem").getValue().toString();
                    nomeUser=nome;
                    imagemUser=imagem;

                    }
                usuarios.setNome(nomeUser);
                usuarios.setImagem(imagemUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnC.getText()== resposta){
                    score+=10;
                    txtScore.setText("SCORE:"+ score);
                    alteraLicao(r.nextInt(imagemLength));
                    //attScore(score);
                }else{

                    gameOver();

                }

            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnD.getText()== resposta){
                    score+=10;
                    txtScore.setText("SCORE:"+ score);
                    alteraLicao(r.nextInt(imagemLength));
                    //attScore(score);
                }else{

                    gameOver();

                }

            }
        });


    }

    private void gameOver(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TelaD.this);
        alertDialogBuilder.
                setTitle("GAME OVER!")
                .setMessage("Você errou! Total de pontos acumulados: " +score+ " PONTOS.")
                .setCancelable(false)
                .setPositiveButton("NOVO JOGO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), TelaD.class));
                                finish();
                            }
                        })
                .setNegativeButton("SAIR DO DESAFIO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                usuarios.setScore(score);
                                    mudaPontos();
                                    finish();

                            }
                        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2E8B57"));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF0000"));

    }

    private void mudaPontos() {

        referenciaFirebase.child("usuários").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(Integer.parseInt(pegapt)<score){
                        postSnapshot.getRef().setValue(usuarios);
                    }
            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void alteraLicao(int num){

        //txtPergunta.setText(licao.getPergunta(num));
        carregarImagem(num);
        btnA.setText(licao.getOpcao1(num));
        btnB.setText(licao.getOpcao2(num));
        btnC.setText(licao.getOpcao3(num));
        btnD.setText(licao.getOpcao4(num));

        resposta = licao.getResposta(num);

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
                Picasso.get().load(uri).resize(width, height).centerCrop().into(imgA);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}
