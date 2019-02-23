package com.appdev.pradoeduardoluiz.cadastrodecds;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;

public class AddEditCD extends AppCompatActivity {

    private EditText txtAlbum;
    private EditText txtArtista;
    private EditText txtAno;
    Cd cd = null;
    private int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_cd);

        txtAlbum = findViewById(R.id.txtAlbum);
        txtArtista = findViewById(R.id.txtArtista);
        txtAno = findViewById(R.id.txtAno);

        position = getIntent().getIntExtra("position", -1);

        if(position == -1) {
            cd = new Cd();
        }else{
            cd = DataStore.sharedInstance(this).getCd(position);
            txtAlbum.setText(cd.getNome());
            txtArtista.setText(cd.getArtista());
            txtAno.setText(String.valueOf(cd.getAno()));
        }

        BottomNavigationView navigation = findViewById(R.id.bottom_nav_view_add_edit_cd);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_salvar_cd:
                    saveCd();
                    finish();
                    return true;
                case R.id.nav_cancelar_cd:
                    finish();
                    return true;
            }
            return false;
        }
    };


    public void saveCd(){

        cd = new Cd(txtAlbum.getText().toString(), txtArtista.getText().toString(),
                Integer.parseInt(txtAno.getText().toString()));

        if(position == -1){
            DataStore.sharedInstance(this).AddCd(cd);
        }else{
            DataStore.sharedInstance(this).editCd(cd,position);
        }

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(new Intent("updateData"));

        finish();
    }

}