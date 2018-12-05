package com.aplicativo.libras.libif;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MENUActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private CircleImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        storage=FirebaseStorage.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        this.getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                android.support.v4.app.Fragment current = getCurrentFragment();
                if(current instanceof HomeActivity){
                    navigationView.setCheckedItem(R.id.nav_perfil);
                }
                else{
                    navigationView.setCheckedItem(R.id.nav_ranking);
                }
            }
        });
        final TextView txtnome = (TextView) headerView.findViewById(R.id.textViewNome);
        TextView txtemail = (TextView) headerView.findViewById(R.id.textViewEmail);
        imageView = (CircleImageView) headerView.findViewById(R.id.imageView);


        FirebaseAuth autenticacao;
        autenticacao = FirebaseAuth.getInstance();
        String email = autenticacao.getCurrentUser().getEmail();

        DatabaseReference referenciaFirebase;
        referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        referenciaFirebase.child("usuários").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nome = postSnapshot.child("nome").getValue().toString();
                    final String imagem = postSnapshot.child("imagem").getValue().toString();
                    txtnome.setText(nome);
                    if(!imagem.equals("")){

                    storageReference = storage.getReferenceFromUrl(imagem);
                        final int height = 160;
                        final int width = 160;

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).resize(width, height).centerCrop().into(imageView);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    /*storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(MENUActivity.this).using(new
                                    FirebaseImageLoader()).load(storageReference).override(150,150).into(imageView);
                        }
                    });
                    */
                }}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        txtemail.setText(email);

        selectscreenDisplay(R.id.nav_perfil);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                getSupportFragmentManager().popBackStack();

            }else {
                super.onBackPressed();


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       selectscreenDisplay(id);

        return true;
    }
    private void selectscreenDisplay(int id){
        android.support.v4.app.Fragment fm = null;
        switch (id){
            case R.id.nav_perfil:
                fm = new HomeActivity();
                break;
            case R.id.nav_ranking:
                fm = new MostraRanking();
                break;
            case R.id.nav_sair:
                sair();
                break;
        }
        if (fm!=null){
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_menu,fm);
            ft.commit();
            ft.addToBackStack(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    private void sair(){
        Conexao.logOut();
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public android.support.v4.app.Fragment getCurrentFragment(){
        return this.getSupportFragmentManager().findFragmentById(R.id.content_menu);
    }
}
