package com.aplicativo.libras.libif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


//Minha classe cadastro que extende appCompatActivity
        //(Classe base para atividades que usam os recursos da barra de ação da biblioteca de suporte )

public class cadastrosActivity extends AppCompatActivity {

    private BootstrapEditText edtNome, edtEmail, edtSenha;
    private BootstrapButton btnRegistrar, btnVoltar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuarios user;
    private TextView txtImg;
    private BootstrapButton uploadImg;

    //private CircleImageView CaImg;

    private FirebaseStorage storage;
    private Uri uri;
    private String url;
    private ProgressDialog progressDialog;





        //aqui estou recriando a Activity, restaurando seu estado exatamente como era
        // antes de ser destruída, quando rodada pelo usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);
        inicializaComponentes();

        //CaImg = (CircleImageView) findViewById(R.id.CaImg);
        progressDialog = new ProgressDialog(cadastrosActivity.this);
        progressDialog.setTitle("Aguarde!");
        progressDialog.setMessage("Finalizando cadastro...");
        progressDialog.setCancelable(false);

        storage = FirebaseStorage.getInstance();
        user = new Usuarios();



        //tenho aqui a função designada do OnClickListener, que  é chamada quando o usuario clica no botão
        //removo como trim, todos os caracteres de espaço em branco à esquerda e à direita do objeto String atual
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(cadastrosActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selecionaArquivo();

                }
                else{
                    ActivityCompat.requestPermissions(cadastrosActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edtEmail.getText().toString().trim().equals("") && !edtSenha.getText().toString().trim().equals("")){
                    progressDialog.show();
                    user.setNome(edtNome.getText().toString().trim());
                    user.setEmail(edtEmail.getText().toString().trim());
                    user.setSenha(edtSenha.getText().toString().trim());
                    user.setScore(0);
                    if(uri!=null){
                        upload(uri);

                    }else{

                        user.setImagem("");
                        criarUser();

                    }



                }else{

                    Toast.makeText(cadastrosActivity.this, "Por favor, insira os dados para cadastro!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void upload(Uri uri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Upload da imagem");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis()+"";
        uploadImg.setText(fileName);
        StorageReference storageReference = storage.getReference();
        storageReference.child("imagem").child(fileName).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        url = taskSnapshot.getDownloadUrl().toString();
                        user.setImagem(url);
                        criarUser();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(cadastrosActivity.this, "Erro no envio do arqquivo!", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selecionaArquivo();

        }else {
            Toast.makeText(cadastrosActivity.this, "Por favor, conceder permissão de acesso!", Toast.LENGTH_LONG).show();

        }
    }

    private void selecionaArquivo(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, 86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==86 && resultCode==RESULT_OK && data != null){
            uri=data.getData();
            txtImg.setText(uri.getLastPathSegment());
           /* String colunas[] = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            cursor = getContentResolver().query(uri, colunas, null,null,null);

            cursor.moveToFirst();

            int indexColuna = cursor.getColumnIndex(colunas[0]);
            String pathimage = cursor.getString(indexColuna);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(pathimage);
            CaImg.setImageBitmap(bitmap);*/

        }else {
            Toast.makeText(cadastrosActivity.this, "Por favor, selecione um arquivo", Toast.LENGTH_LONG).show();

        }
    }

    //Usando a função do firebase para criar uma nova conta de usuário associada ao endereço de e-mail e à senha especificados
    //uso do task, onde a tarefa vai retornar o objeto quando obter sucesso
    //uso do intent para direcionar o usuario para tela de perfil após clicar no botão de registrar
    private void criarUser(){

        auth = Conexao.getFirebaseAuth();

        auth.createUserWithEmailAndPassword(
               user.getEmail(), user.getSenha())
                .addOnCompleteListener(cadastrosActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                    if(task.isSuccessful()){
                        insereUsuario(user);
                    } else{
                        String erroExcecao = "";

                        try{
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException e){
                            erroExcecao = "Digite uma senha que contenha no mínimo 8 caracteres, incluindo letras e números";
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            progressDialog.dismiss();
                            erroExcecao = "E-mail inválido!";
                        }catch (FirebaseAuthUserCollisionException e){
                            progressDialog.dismiss();
                            erroExcecao = "E-mail já cadastrado!";
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            erroExcecao = "Erro ao efetuar o cadastro!";
                            e.printStackTrace();
                        }
                        Toast.makeText(cadastrosActivity.this,erroExcecao,Toast.LENGTH_LONG).show();
                    }
                }
            });}

    private boolean insereUsuario (Usuarios user){
        try{
            reference = Conexao.getFirebase().child("usuários");
            reference.push().setValue(user);
            progressDialog.dismiss();
            Toast.makeText(cadastrosActivity.this,"Usuário cadastrado com sucesso!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(cadastrosActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }catch (Exception e){
            progressDialog.dismiss();
            Toast.makeText(cadastrosActivity.this,"Erro ao cadastrar usuário!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;

        }
    }

    //uso do toast para mostrar a msg na tela de cadastro, de sucesso ou erro ao se cadastrar
    private void alert(String msg){

        Toast.makeText(cadastrosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    //iniciando os componentes da tela
    private void inicializaComponentes(){

        edtNome = (BootstrapEditText) findViewById(R.id.edtNome);
        edtEmail = (BootstrapEditText) findViewById(R.id.edtEmail);
        edtSenha = (BootstrapEditText) findViewById(R.id.edtSenha);
        btnRegistrar = (BootstrapButton) findViewById(R.id.btnRegistrar);
        btnVoltar = (BootstrapButton) findViewById(R.id.btnVoltar);
        uploadImg = (BootstrapButton) findViewById(R.id.uploadImg);
        txtImg = (TextView) findViewById(R.id.txtImg);

    }

    //será executado quando a atividade estiver visível para o usuario

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

}
