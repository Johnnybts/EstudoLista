package com.voz.johnny.estudolista.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.voz.johnny.estudolista.R;
import com.voz.johnny.estudolista.controller.ChavesController;
import com.voz.johnny.estudolista.model.Chave;

public class CreateChaveListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementView = inflater.inflate(R.layout.addchave,null);

        final EditText editChave = (EditText) formElementView.findViewById(R.id.editTextChave);

        new AlertDialog.Builder(context)
                .setView(formElementView)
                .setTitle("Adicionar Nova Chave")
                .setPositiveButton("Incluir",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String addchave = editChave.getText().toString();

                                Chave chave = new Chave();
                                chave.setChave(addchave);

                                boolean criadoComSucesso = new ChavesController(context).create(chave);

                                if(criadoComSucesso){

                                    Toast.makeText(context,"Chave incluida com sucesso!",Toast.LENGTH_SHORT).show();
                                    ((Lista_Chaves)context).Listar();


                                }else{

                                    Toast.makeText(context,"Não foi possível incluir a chave!",Toast.LENGTH_SHORT).show();

                                }

                                    dialogInterface.cancel();
                            }
                        }).show();



    }
}
