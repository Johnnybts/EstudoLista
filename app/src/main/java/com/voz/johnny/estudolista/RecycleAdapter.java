package com.voz.johnny.estudolista;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.voz.johnny.estudolista.view.Lista_Chaves;
import com.voz.johnny.estudolista.view.RetrieveListener;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String itens[];
    int qtd;
    int id;

    int tag;
    //tentar receber lista!
    public RecycleAdapter(Context context, String[] itens, int qtd){
        this.context = context;
        this.itens = itens;
        this.qtd = qtd;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infater = LayoutInflater.from(context);
        View linha = infater.inflate(R.layout.lista_layout,parent,false);
        final Itens itens = new Itens(linha);

        linha.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String ChaveAtual = String.valueOf(itens.texto.getText());

                RetrieveListener rec = new RetrieveListener();
                rec.CliqueLongo(v,itens.getAdapterPosition(),ChaveAtual);

                Toast.makeText(context, "Clicou" + ChaveAtual, Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        ;

        return itens;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Itens)holder).texto.setText(itens[position]);
        ((Itens)holder).nr.setText(Integer.toString(position));

    }

    @Override
    public int getItemCount() {
        return qtd /*itens.length*/;
    }

    public class Itens extends  RecyclerView.ViewHolder{
        TextView texto;
        TextView nr;
        public Itens(View itemView) {
            super(itemView);
            texto = (TextView) itemView.findViewById(R.id.texto);
            nr = (TextView) itemView.findViewById(R.id.textView);

        }
    }

}
