package com.appdev.pradoeduardoluiz.cadastrodecds.domain;

import java.util.ArrayList;
import java.util.List;

public class DataStore {


    private static DataStore instance = null;
    private List<Cd> cds = null;


    public static DataStore sharedInstance(){
        if (instance == null)
            instance = new DataStore();
        return instance;
    }


    public DataStore() {

        cds = new ArrayList<>();
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
    }

    public void editCd(Cd cd, int position){
        cds.set(position, cd);
    }

    public void removeCd(int position){
        cds.remove(position);
    }

    public void clearCds(){
        cds.clear();
    }



}
