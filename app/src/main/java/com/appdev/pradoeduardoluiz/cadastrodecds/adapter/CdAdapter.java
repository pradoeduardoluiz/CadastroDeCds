package com.appdev.pradoeduardoluiz.cadastrodecds.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appdev.pradoeduardoluiz.cadastrodecds.R;

import java.util.List;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;

public class CdAdapter extends RecyclerView.Adapter<CdAdapter.CdHolder> {

    private List<Cd> cds = null;
    private final int UPDATECDS = 2;
    private Activity activity;

    public CdAdapter(Activity activity){
        this.activity = activity;
        cds = DataStore.sharedInstance(activity).getCds();
    }


    @Override
    public CdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_cds, viewGroup, false);
        return new CdHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CdHolder cdHolder, int position) {

        Cd cd = cds.get(position);
        cdHolder.tvName.setText("Album: " + cd.getNome());
        cdHolder.tvArtist.setText("Artista: " + cd.getArtista());
        cdHolder.tvYear.setText("População: " + cd.getAno());

    }

    @Override
    public int getItemCount() {
        return cds.size();
    }

    public class CdHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvName;
        public TextView tvArtist;
        public TextView tvYear;

        public CdHolder(@NonNull View itemView) {

            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            tvYear = itemView.findViewById(R.id.tvYear);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }

    }



}
