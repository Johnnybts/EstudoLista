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

public class RetrieveListener implements View.OnLongClickListener {

    Context context;
    String id;
    int id2;

    public boolean CliqueLongo(View v, int nr, final String chave){

        context = v.getContext();
        id =  Integer.toString(nr);
        final String texto = chave;

        final CharSequence[] itens = {"Editar","Deletar"};

        new AlertDialog.Builder(context).setTitle("Detalhes da Chave")
                .setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i == 0){
                            //editar
                            editChave(Integer.parseInt(id), texto);

                        }else if (i==1){
                            //deletar
                            final ChavesController chavecontroller = new ChavesController(context);
                            final int idloca = Integer.parseInt(chavecontroller.encontrar_ID(chave));
                            boolean isDeletou = new ChavesController(context).delete(idloca);

                            if(isDeletou){
                                ((Lista_Chaves)context).Listar();
                                Toast.makeText(context, "Registro deletado com sucesso.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Erro ao excluir a chave", Toast.LENGTH_SHORT).show();
                            }

                        }

                        dialogInterface.dismiss();

                    }

                }).show();
        return false;
    }

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id =  view.getTag().toString();
        System.out.println(id);

        final CharSequence[] itens = {"Editar","Deletar"};

        new AlertDialog.Builder(context).setTitle("Detalhes da Chave")
                .setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i == 0){
                            //editar
                            /*editChave(Integer.parseInt(id),);*/

                        }else if (i==1){

                            //deletar


                            boolean isDeletou = new ChavesController(context).delete(Integer.parseInt(id));

                            if(isDeletou){
                               Toast.makeText(context, "Registro deletado com sucesso.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Erro ao excluir a chave", Toast.LENGTH_SHORT).show();
                            }

                        }

                        dialogInterface.dismiss();

                    }

                }).show();
        return false;
    }

    public void editChave(final int chaveID,String chave){

        Toast.makeText(context,"teste"+ chaveID,Toast.LENGTH_SHORT).show();

            final ChavesController chavecontroller = new ChavesController(context);
            final int idloca = Integer.parseInt(chavecontroller.encontrar_ID(chave));
            final Chave texto = chavecontroller.bucarID(idloca);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View formChave = inflater.inflate(R.layout.addchave,null,false);
        final EditText editchave = (EditText)formChave.findViewById(R.id.editTextChave);

        editchave.setText(texto.getChave());

        new AlertDialog.Builder(context)
                .setView(formChave)
                .setTitle("Editar")
                .setPositiveButton("Atualizar Chave",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                               Chave ch = new Chave();

                               ch.setId(idloca);
                               ch.setChave(editchave.getText().toString());

                               boolean isUpdate = chavecontroller.update(ch);

                               if(isUpdate){
                                   Toast.makeText(context, "Dados atualizado com sucesso", Toast.LENGTH_SHORT).show();
                                   ((Lista_Chaves) context).Listar();
                               } else {
                                   Toast.makeText(context, "Falha ao salvar", Toast.LENGTH_SHORT).show();
                               }
                                dialogInterface.cancel();

                            }
                        }
                ).show();


    }
}
