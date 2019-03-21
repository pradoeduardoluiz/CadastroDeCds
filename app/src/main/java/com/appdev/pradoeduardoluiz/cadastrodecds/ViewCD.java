package com.appdev.pradoeduardoluiz.cadastrodecds;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;

public class ViewCD extends AppCompatActivity {

    private TextView txtAlbum;
    private TextView txtArtista;
    private TextView txtAno;
    private int position = 1;
    private Cd cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cd);

        txtAlbum = (TextView) findViewById(R.id.txtAlbum);
        txtArtista = findViewById(R.id.txtArtista);
        txtAno = findViewById(R.id.txtAno);

        position = getIntent().getIntExtra("position", -1);

        if (position != -1){
            cd = DataStore.sharedInstance().getCd(position);

            txtAlbum.setText(cd.getNome());
            txtArtista.setText(cd.getArtista());
            txtAno.setText(String.valueOf(cd.getAno()));

        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view_view_cd);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_edit_cd:
                    Intent intent = new Intent(ViewCD.this, AddEditCD.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.nav_cancelar_cd:
                    finish();
                    return true;
            }
            return false;
        }
    };

}
