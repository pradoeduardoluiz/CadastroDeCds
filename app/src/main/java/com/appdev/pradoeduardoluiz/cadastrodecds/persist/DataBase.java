package com.appdev.pradoeduardoluiz.cadastrodecds.persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private SQLiteDatabase db;

    private final String TABLE_CD = "cds";

    private final String COLUMN_ID = "_id";
    private final String COLUMN_NAME = "nome";
    private final String COLUMN_ARTIST = "artista";
    private final String COLUMN_YEAR = "ano";

    public DataBase(Context context){

        DataBaseCore dataBaseCore = new DataBaseCore(context);
        db = dataBaseCore.getWritableDatabase();
    }

    public boolean insert(Cd cd){

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cd.getNome());
        values.put(COLUMN_ARTIST, cd.getArtista());
        values.put(COLUMN_YEAR, cd.getAno());

        long id = db.insert(TABLE_CD, null, values);

        if(id > 0){
            return true;
        }else {
            return false;
        }

    }
    public boolean update(Cd cd){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cd.getNome());
        values.put(COLUMN_ARTIST, cd.getArtista());
        values.put(COLUMN_YEAR, cd.getAno());

        int count = db.update(TABLE_CD, values, "_id = ?", new String[]{""+cd.getId()});

        if (count > 0){
            return true;
        }else{
            return false;
        }

    }
    public boolean delete(Cd cd){
        int count = db.delete(TABLE_CD, "_id = ?", new String[]{""+cd.getId()});
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    public Cd get(long id){

        String[] columns = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_ARTIST, COLUMN_YEAR};

        Cursor cursor = db.query(TABLE_CD, columns, COLUMN_ID + " = ?",
                new String[]{""+id},
                null, null, COLUMN_NAME + " ASC ");

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            Cd cd = new Cd();
            cd.setId(cursor.getLong(0));
            cd.setNome(cursor.getString(1));
            cd.setArtista(cursor.getString(2));
            cd.setAno(cursor.getInt(3));
            return cd;

        }else {
            return null;
        }
    }

    public List<Cd> list(){

        List<Cd> list = new ArrayList<>();

        String[] columns = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_ARTIST, COLUMN_YEAR};

        Cursor cursor = db.query(TABLE_CD, columns, null, null,
                null, null, COLUMN_NAME + " ASC ");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Cd cd = new Cd();
                cd.setId(cursor.getLong(0));
                cd.setNome(cursor.getString(1));
                cd.setArtista(cursor.getString(2));
                cd.setAno(cursor.getInt(3));
                list.add(cd);

            }while (cursor.moveToNext());

        }

        return list;
    }

}
