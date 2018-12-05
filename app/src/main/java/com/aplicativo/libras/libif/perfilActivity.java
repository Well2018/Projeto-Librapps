package com.aplicativo.libras.libif;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


//Minha classe de perfil que extende appCompatActivity
            //(Classe base para atividades que usam os recursos da barra de ação da biblioteca de suporte )public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
    public class perfilActivity extends AppCompatActivity  {

    private TextView txtEmail, txtId, txtNome;
    private Button btnSair;
    private FirebaseAuth auth;
    private FirebaseUser user;


                //aqui estou recriando a Activity, restaurando seu estado exatamente como era
                // antes de ser destruída, quando rodada pelo usuario
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_perfil);
                inicializaComponentes();
                eventoClicks();


            }

                //tenho aqui a função designada do OnClickListener, que  é chamada quando o usuario clica no botão
                //removo como trim, todos os caracteres de espaço em branco à esquerda e à direita do objeto String atual
            private void eventoClicks() {

                btnSair.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Conexao.logOut();
                        finish();
                    }
                });
            }


                //iniciando os componentes da tela
            private void inicializaComponentes(){

                txtEmail = (TextView) findViewById(R.id.txtEmail);
                txtId = (TextView) findViewById(R.id.txtId);
                btnSair = (Button) findViewById(R.id.btnSair);
                txtNome = (TextView) findViewById(R.id.txtNome);
            }


                //será executado quando a atividade estiver visível para o usuario
            @Override
            protected void onStart() {
                super.onStart();
                auth = Conexao.getFirebaseAuth();
                user = Conexao.getFirebaseUser();
                verificaUser();
                String nome = txtNome.getText().toString();
            }


                //verifica o usuario e "seta" seus dados na tela de perfil
            private void verificaUser() {
                if(user == null){

                    finish();
                }else{
                    txtNome.setText("Nome: "+ txtNome.getText().toString());
                    txtEmail.setText("Email: "+user.getEmail());
                    txtId.setText("Id: "+user.getUid());
                }
            }




    }
