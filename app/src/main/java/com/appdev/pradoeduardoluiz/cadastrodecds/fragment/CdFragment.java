package com.appdev.pradoeduardoluiz.cadastrodecds.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.appdev.pradoeduardoluiz.cadastrodecds.AddEditCD;
import com.appdev.pradoeduardoluiz.cadastrodecds.R;
import com.appdev.pradoeduardoluiz.cadastrodecds.ViewCD;
import com.appdev.pradoeduardoluiz.cadastrodecds.adapter.CdAdapter;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;

/**
 * A simple {@link Fragment} subclass.
 */
public class CdFragment extends Fragment{

    RecyclerView recyclerView;
    CdAdapter adapter;
    GestureDetector gestureDetector;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cd, container, false);

        recyclerView = view.findViewById(R.id.rvCds);
        recyclerView.setHasFixedSize(true);
        adapter = new CdAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){

            @Override
            public void onLongPress(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                final int position = recyclerView.getChildAdapterPosition(view);


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                Cd cd = DataStore.sharedInstance().getCd(position);

                builder.setMessage("Deseja realmente excluir o album " + cd.getNome() + "?")
                        .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataStore.sharedInstance().removeCd(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(view);

                Intent intent = new Intent(getActivity(), ViewCD.class);
                intent.putExtra("position", position);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(view);

                Intent intent = new Intent(getActivity(), AddEditCD.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 2);
                return true;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                return (view != null && gestureDetector.onTouchEvent(motionEvent));
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public CdAdapter getAdapter() {
        return adapter;
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }

}
