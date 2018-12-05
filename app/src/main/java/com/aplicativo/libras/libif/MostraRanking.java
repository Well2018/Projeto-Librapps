package com.aplicativo.libras.libif;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class MostraRanking extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String> nomes = new ArrayList<String>(), pt= new ArrayList<String>();
    DatabaseReference referenciaFirebase;
    RankingAdapter adapter;
    int cont=0;
String pegapt;
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_mostra_ranking,container,false);
        getActivity().setTitle("RANKING");
        recyclerView= v.findViewById(R.id.myrv);
        FirebaseAuth autenticacao;
        autenticacao = FirebaseAuth.getInstance();
        String email = autenticacao.getCurrentUser().getEmail();


        referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        Preenche();



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return v;
    }
    public  void Preenche(){
        referenciaFirebase.child("usuários").orderByChild("score").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomes.clear();
                pt.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String nome = postSnapshot.child("nome").getValue().toString();
                    String pont = postSnapshot.child("score").getValue().toString();
                    nomes.add(nome);
                    pt.add(pont);

                }
                Collections.reverse(nomes);
                Collections.reverse(pt);
                adapter = new RankingAdapter(nomes,pt,getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
