package com.aplicativo.libras.libif;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by welli on 28/02/2018.
 */


    //realizando conexão com o faribase, com suporte a autenticação
    //uso do faribaseUser para gerenciar o estado de autenticacao dos usarios, bem comoinformacoes de seu perfil

    public class Conexao {

            private static FirebaseAuth firebaseAuth;
            private static FirebaseAuth.AuthStateListener authStateListener;
            private static FirebaseUser firebaseUser;
            private static StorageReference storageReference;
            private static FirebaseStorage storage;

            private static DatabaseReference referenciaFirebase;



    private Conexao() {


            }
    public static DatabaseReference getFirebase(){
        if(referenciaFirebase==null){
            referenciaFirebase= FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }


    public static FirebaseAuth getFirebaseAuth(){

                if(firebaseAuth == null){

                    inicializarFirebaseAuth();
                }

                return firebaseAuth;

            }

    public static FirebaseStorage getFirebaseStorage(){
        if (storage == null){
            storage = FirebaseStorage.getInstance();
        }

        return storage;
    }

    public static StorageReference getStorageReference(){
        if(storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();

        }
        return storageReference;
    }

            //uso do getInstance para retornar a instancia do objeto user em caso de possíveis comparacoes
            private static void inicializarFirebaseAuth() {

                firebaseAuth = FirebaseAuth.getInstance();
                authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(user != null){

                            firebaseUser = user;
                        }
                    }
                };

                //chamado quando há uma alteração no estado de autenticação. cadastrando ou cancelando cadastros.
                firebaseAuth.addAuthStateListener(authStateListener);
            }

            public static FirebaseUser getFirebaseUser(){
                return firebaseUser;

            }

            //processo de sair da conta

            public static void logOut(){
                firebaseAuth.signOut();

            }
    }







