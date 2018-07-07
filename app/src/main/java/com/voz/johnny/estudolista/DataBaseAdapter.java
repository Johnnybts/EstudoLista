package com.voz.johnny.estudolista;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "CHAVES.db";

    public DataBaseAdapter(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE chaves_tbl " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "chave TEXT)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS chaves_tbl";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }
}
