package com.voz.johnny.estudolista.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.voz.johnny.estudolista.DataBaseAdapter;
import com.voz.johnny.estudolista.model.Chave;

import java.util.ArrayList;
import java.util.List;

public class ChavesController extends DataBaseAdapter {


    public ChavesController(Context context){
        //integrar com banco de dados
        super(context);
    }

        public boolean create(Chave chave){

            ContentValues values = new ContentValues();
            values.put("chave", chave.getChave());

            SQLiteDatabase db = this.getWritableDatabase();
            boolean isCreate = db.insert("chaves_tbl",null,values) > 0;
            db.close();

            return isCreate;
        }

        public int totalChaves(){

            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM chaves_tbl";

            int contador = db.rawQuery(sql,null).getCount();

            return contador;

            }
            public List<Chave> listarChaves(){

            List<Chave> chaveList = new ArrayList<>();

                    String sql = "SELECT * FROM chaves_tbl ORDER BY id DESC";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if(cursor.moveToFirst()){
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String nome = cursor.getString(cursor.getColumnIndex("chave"));

                    Chave chave = new Chave();
                    chave.setId(id);
                    chave.setChave(nome);

                    chaveList.add(chave);

                    }while(cursor.moveToNext());
            }

            cursor.close();
            db.close();
        return chaveList;

        }

        public Chave bucarID(int chaveID){

                    Chave chave = null;

            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM chaves_tbl WHERE id = "+chaveID;
            Cursor cursor = db.rawQuery(sql,null);

            if(cursor.moveToFirst()){

                String chavetxt = cursor.getString(cursor.getColumnIndex("chave"));

                chave = new Chave();

                chave.setId(chaveID);
                chave.setChave(chavetxt);
            }

                return chave;
        }

        public boolean update (Chave chave){

            ContentValues values = new ContentValues();
            values.put("chave", chave.getChave());

            String where = "id = ?";
            String[] whereArgs = {Integer.toString(chave.getId())};

            SQLiteDatabase db = this.getWritableDatabase();

            boolean isUpdate = db.update("chaves_tbl",values,where,whereArgs) > 0;

            db.close();

            return isUpdate;
        }

        public boolean delete (int chaveID){

        boolean isDeletado = false;
        SQLiteDatabase db = this.getWritableDatabase();

        isDeletado = db.delete("chaves_tbl","id = '" + chaveID + "'",null)>0;
        db.close();

        return  isDeletado;

        }


    public List<Chave> somente_chaves(){

        List<Chave> chaveList = new ArrayList<>();

        String sql = "SELECT chave FROM chaves_tbl ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                String nome = cursor.getString(cursor.getColumnIndex("chave"));

                Chave chave = new Chave();
                chave.setChave(nome);

                chaveList.add(chave);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chaveList;

    }

    public String encontrar_ID(String chave){


        String nr;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM chaves_tbl WHERE chave = '"+chave+"'";
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
        }
        do {
            String idtxt = cursor.getString(cursor.getColumnIndex("id"));

            nr = idtxt;

        }while(cursor.moveToNext());

        cursor.close();
        db.close();

        return nr;


    }


}
