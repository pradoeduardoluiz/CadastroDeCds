package com.appdev.pradoeduardoluiz.cadastrodecds.domain;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.appdev.pradoeduardoluiz.cadastrodecds.persist.DataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataStore {


    private static DataStore instance = null;
    private List<Cd> cds = null;
    private DataBase dataBase;
    private Context context;


    public static DataStore sharedInstance(){
        if (instance == null){
            instance = new DataStore();
        }
        return instance;
    }

    public void setContext(Context context){
        this.context = context;
        dataBase = new DataBase(context);
        cds = dataBase.list();
    }

    public List<Cd> getCds() {
        return cds;
    }

    public void setCds(List<Cd> cds) {
        this.cds = cds;
    }

    public Cd getCd(int position){
        return cds.get(position);
    }

    public void AddCd(Cd cd){

        if(dataBase.insert(cd)){
            cds.add(cd);
        }else{
            showError("Ocorreu um erro ao inserir cd " + cd.getNome());
        }

    }

    private void showError(String msg) {
        Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT).show();
    }

    public void editCd(Cd cd, int position){

        if(dataBase.update(cd)){
            cds.set(position, cd);
        }else{
            showError("Ocorreu um erro ao editar cd " + cd.getNome());
        }

        cds.set(position, cd);
    }

    public void removeCd(int position){

        if(dataBase.delete(cds.get(position))){
            cds.remove(position);
        }

    }

    public void clearCds(){

        for (int i = 0; i < cds.size(); i++) {
            removeCd(i);
        }
        cds.clear();
    }


    public void getInitialData() {

        cds.clear();
        cds.add(new Cd("All hope is gone", "Slipknot", 2008));
        cds.add(new Cd("DESIGN YOUR UNIVERSE", "EPICA", 2009));
        cds.add(new Cd("Endorama", "Kreator", 1999));
        cds.add(new Cd("Fear of the Dark", "Iron Maiden", 1992));
        cds.add(new Cd("Killer be Killed", "Killer be Killed", 2013));
        cds.add(new Cd("Wasting Ligth", "Foo Figthers", 2011));
        cds.add(new Cd("The Way Of The Fist", "Five Finger Death Punch", 2007));
        cds.add(new Cd("Alpha Noir", "Moonspell", 2012));
        cds.add(new Cd("Karma and Effect", "Seether", 2005));
        

    }

}
