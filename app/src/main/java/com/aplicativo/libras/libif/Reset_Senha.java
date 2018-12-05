package com.aplicativo.libras.libif;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


//Minha classe de recuperar senha que extende appCompatActivity
                //(Classe base para atividades que usam os recursos da barra de ação da biblioteca de suporte )

    public class Reset_Senha extends AppCompatActivity {

        private BootstrapEditText edtEmail;
        private BootstrapButton btnResetSenha;

        private FirebaseAuth auth;

        //aqui estou recriando a Activity, restaurando seu estado exatamente como era
        // antes de ser destruída, quando rodada pelo usuario
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reset__senha);
            inicializarComponentes();
            eventoClicks();
        }

        //tenho aqui a função designada do OnClickListener, que  é chamada quando o usuario clica no botão
        //removo com o trim, todos os caracteres de espaço em branco à esquerda e à direita do objeto String atual
        private void eventoClicks() {

            btnResetSenha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!edtEmail.getText().toString().trim().equals("")) {
                        String email = edtEmail.getText().toString().trim();
                        resetSenha(email);
                    } else {
                        Toast.makeText(Reset_Senha.this, "Por favor, insira os dados para cadastro!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        //processo de recuperar senha(usuario ja cadastrado)
        //uso do task, onde a tarefa vai retornar o objeto quando obter sucesso
        //usuario vai receber uma mensagem ao clicar no botão de recuperar senha
        private void resetSenha(String email) {

            auth.sendPasswordResetEmail(email).addOnCompleteListener(Reset_Senha.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        alert("Um E-mail foi enviado para sua conta.");
                        finish();
                        Intent i = new Intent(Reset_Senha.this, MainActivity.class);
                        startActivity(i);
                    } else {

                        alert("Não foi possível recuperar sua senha. E-mail não encontrado!");
                    }
                }
            });
        }

        //uso do toast para mostrar a msg na tela, se foi enviado a recupecao para o email ou se houve erro
        private void alert(String s) {

            Toast.makeText(Reset_Senha.this, s, Toast.LENGTH_SHORT).show();
        }

        private void inicializarComponentes() {

            edtEmail = (BootstrapEditText) findViewById(R.id.edtEmail);
            btnResetSenha = (BootstrapButton) findViewById(R.id.btnResetSenha);

        }
        //será executado quando a atividade estiver visível para o usuario

        @Override
        protected void onStart() {
            super.onStart();
            auth = Conexao.getFirebaseAuth();

        }
    }
