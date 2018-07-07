package com.voz.johnny.estudolista.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.voz.johnny.estudolista.R;
import com.voz.johnny.estudolista.RecycleAdapter;
import com.voz.johnny.estudolista.controller.ChavesController;
import com.voz.johnny.estudolista.model.Chave;

import java.util.List;

public class Lista_Chaves extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] itens = {"Texto teste do layout","Texto teste do layout","Texto teste do layout","Texto teste do layout","Texto teste do layout"};
    String[] itens2 = new String[10];
    Integer[] idlist = new Integer[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista__chaves);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new CreateChaveListener() {
        });


        Listar();
        /*recyclerView = findViewById(R.id.listaview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecycleAdapter(this,itens));*/

            }

    public void Listar(){

        int x = 1;

        List<Chave> chaveList = new ChavesController(this).somente_chaves();

        if(chaveList.size()>0){

            for(Chave ch : chaveList){

                String chavetxt = ch.getChave();
                itens2[x] = chavetxt;
                 x = x + 1;

                /*item.setOnLongClickListener(new RetrieveListener());*/
            }

        } else {

        }


        recyclerView = findViewById(R.id.listaview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecycleAdapter(this,itens2,x));



    }

}
