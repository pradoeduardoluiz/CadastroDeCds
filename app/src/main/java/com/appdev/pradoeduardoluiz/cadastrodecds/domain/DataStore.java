package com.appdev.pradoeduardoluiz.cadastrodecds.domain;

import android.app.Activity;

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
    private static Activity activity;


    public static DataStore sharedInstance(Activity act){
        if (instance == null){
            activity = act;
            instance = new DataStore();
        }
        return instance;
    }


    public DataStore() {

        cds = new ArrayList<Cd>();
        readRegister();

        //cds.add(new Cd("All hope is gone", "Slipknot", 2008));
        //cds.add(new Cd("DESIGN YOUR UNIVERSE", "EPICA", 2009));
        //cds.add(new Cd("Endorama", "Kreator", 1999));
        //cds.add(new Cd("Fear of the Dark", "Iron Maiden", 1992));
        //cds.add(new Cd("Killer be Killed", "Killer be Killed", 2013));
        //cds.add(new Cd("Wasting Ligth", "Foo Figthers", 2011));
        //cds.add(new Cd("The Way Of The Fist", "Five Finger Death Punch", 2007));
        //cds.add(new Cd("Alpha Noir", "Moonspell", 2012));
        //cds.add(new Cd("Karma and Effect", "Seether", 2005));

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
        cds.add(cd);
        if(writeRegister(cds)){
            readRegister();
        }
    }

    public void editCd(Cd cd, int position){
        cds.set(position, cd);
        if(writeRegister(cds)){
            readRegister();
        }
    }

    public void removeCd(int position){
        cds.remove(position);
        if(writeRegister(cds)){
            readRegister();
        }
    }

    public void clearCds(){
        cds.clear();
        if(writeRegister(cds)){
            readRegister();
        }
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
        
        if(writeRegister(cds)){
            readRegister();
        }

    }

    public void commit(){
        writeRegister(cds);
    }


    private boolean writeRegister(List<Cd> cds) {

        try {
            OutputStream outputStream = activity.openFileOutput("db.txt", MODE_PRIVATE);
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);

            for (Cd cd:cds) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(cd.getNome() + ";");
                stringBuilder.append(cd.getArtista() + ";");
                stringBuilder.append(cd.getAno() + ";");
                stringBuilder.append("\n");

                streamWriter.write(stringBuilder.toString());
                streamWriter.flush();
            }
            streamWriter.close();
            outputStream.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void readRegister() {

        cds.clear();

        try {
            InputStream inputStream = activity.openFileInput("db.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);

            String str = "";

            while ((str = reader.readLine()) != null){

                Cd cd = new Cd();
                String array[] = str.split(";");

                cd.setNome(array[0]);
                cd.setArtista(array[1]);
                cd.setAno(Integer.parseInt(array[2]));

                cds.add(cd);
            }

            reader.close();
            streamReader.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
