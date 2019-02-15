package com.appdev.pradoeduardoluiz.cadastrodecds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;
import com.appdev.pradoeduardoluiz.cadastrodecds.fragment.CdFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CdFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.cadCds);


        fragment = (CdFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");

        if (fragment == null){
            fragment = new CdFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction().add(R.id.main_container, fragment, "mainFrag");
            fragmentTransaction.commit();
        }


        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_novo:
                        Intent intent = new Intent(MainActivity.this, AddEditCD.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_limpar:
                        DataStore.sharedInstance().clearCds();
                        fragment.notifyDataSetChanged();
                        break;
                }

                return true;
            }
        });

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(updateData, new IntentFilter("updateData"));
    }

    private BroadcastReceiver updateData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fragment.notifyDataSetChanged();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        if(isFinishing()){
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            broadcastManager.unregisterReceiver(updateData);
        }
    }

}
