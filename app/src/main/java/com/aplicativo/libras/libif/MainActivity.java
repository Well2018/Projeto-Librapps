package com.aplicativo.libras.libif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



//Minha classe principal que extende appCompatActivity
        //(Classe base para atividades que usam os recursos da barra de ação da biblioteca de suporte )

    public class MainActivity extends AppCompatActivity  {

    private BootstrapEditText edtEmail, edtSenha;
    private BootstrapButton btnEntrar;
    private TextView btnNovoUsuario;
    private TextView txtResetesenha;

    private ProgressDialog progressDialog;

    private FirebaseAuth auth;


                 //aqui estou recriando a Activity, restaurando seu estado exatamente como era
                // antes de ser destruída, quando rodada pelo usuario

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                inicializaComponentes();
                eventoClicks();

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Aguarde!");
                progressDialog.setMessage("Verificando dados...");
                progressDialog.setCancelable(false);

                if(usuarioLogado()){
                  Intent intent = new Intent(MainActivity.this, MENUActivity.class);
                    startActivity(intent); }




            }



                //tenho aqui a função designada do OnClickListener, que  é chamada quando o usuario clica no botão
                //removo como trim, todos os caracteres de espaço em branco à esquerda e à direita do objeto String atual
            private void eventoClicks(){

                btnNovoUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(getApplicationContext(),cadastrosActivity.class);
                        startActivity(i);
                    }
                });

                btnEntrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        if(!edtEmail.getText().toString().trim().equals("") && !edtSenha.getText().toString().trim().equals("")){

                            progressDialog.show();
                            String email = edtEmail.getText().toString().trim();
                            String senha = edtSenha.getText().toString().trim();
                            login(email,senha);


                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Por favor, preencha seu login e senha!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                txtResetesenha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, Reset_Senha.class);
                        startActivity(i);
                    }
                });
            }


                //processo de entrar na conta(usuario ja cadastrado)
                //uso do task, onde a tarefa vai retornar o objeto quando obter sucesso
                //uso do intent para direcionar o usuario para tela de perfil após clicar no botão de entrar
            private void login(String email, String senha) {

                auth.signInWithEmailAndPassword(email,senha)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(MainActivity.this, MENUActivity.class);
                                    startActivity(i);
                                }else{
                                    progressDialog.dismiss();
                                    alert("E-mail ou Senha inválido!");
                                }
                            }
                        });
            }


            //uso do toast para mostrar a msg na tela de entrada, de sucesso ou erro ao se cadastrar
            private void alert(String s) {

                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            //iniciando os componentes da tela
            private void inicializaComponentes(){

                edtEmail = (BootstrapEditText) findViewById(R.id.edtEmail);
                edtSenha = (BootstrapEditText) findViewById(R.id.edtSenha);
                btnEntrar = (BootstrapButton) findViewById(R.id.btnEntrar);
                btnNovoUsuario = (TextView) findViewById(R.id.btnNovoUsuario);
                txtResetesenha = (TextView) findViewById(R.id.txtReseteSenha);
            }

            //será executado quando a atividade estiver visível para o usuario
            @Override
            protected void onStart() {
                super.onStart();
                auth = Conexao.getFirebaseAuth();

            }

        public Boolean usuarioLogado(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            progressDialog.dismiss();
        }
    }
