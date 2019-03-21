package com.appdev.pradoeduardoluiz.cadastrodecds.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseCore extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_cds";
    private static final int DB_VERSION = 1;

    public DataBaseCore(Context context){
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE cds (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL, " +
                "artista text NOT NULL, " +
                "ano integer NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE cds;";
        onCreate(db);
    }
}

