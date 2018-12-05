package com.aplicativo.libras.libif;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RankingAdapter  extends RecyclerView.Adapter<RankingAdapter.MyAdapter> {

    ArrayList<String> nomes,pontos;
    FragmentActivity context;

    public RankingAdapter(ArrayList<String> nomes, ArrayList<String> pontos,  FragmentActivity context) {
        this.nomes=nomes;
        this.pontos=pontos;
        this.context=context;

    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemranking, parent, false);
        return new MyAdapter(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter holder, int position) {
        FirebaseAuth autenticacao;
        autenticacao = FirebaseAuth.getInstance();
        String email = autenticacao.getCurrentUser().getEmail();

        DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        //storage = FirebaseStorage.getInstance();
        referenciaFirebase.child("usuários").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nomebd = postSnapshot.child("nome").getValue().toString();

                    if(holder.jogador.getText().equals(nomebd)){
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#B8860B"));
                    }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(position < 10){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#3CB371"));
            Log.d("Position",""+position);
            holder.jogador.setText(nomes.get(position));
            holder.pontuacao.setText(pontos.get(position));
            holder.contador.setText(String.valueOf(position+1));
            }else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#00ACED"));
            Log.d("Position",""+position);
            holder.jogador.setText(nomes.get(position));
            holder.pontuacao.setText(pontos.get(position));
            holder.contador.setText(String.valueOf(position+1));
        }



    }

    @Override
    public int getItemCount() {
        if(nomes != null){
            return nomes.size();
        }
        return 0;
    }


    public static class MyAdapter extends  RecyclerView.ViewHolder

    {
        TextView jogador,pontuacao,contador;
        CardView cardView;

        public MyAdapter(View itemView) {
            super(itemView);
            jogador = (TextView) itemView.findViewById(R.id.tvrnome);
            pontuacao = (TextView) itemView.findViewById(R.id.tvrpontos);
            contador = (TextView) itemView.findViewById(R.id.tvrcont);
            cardView = itemView.findViewById(R.id.idCardView);

        }

    }
}
