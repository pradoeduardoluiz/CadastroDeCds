package com.appdev.pradoeduardoluiz.cadastrodecds.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.appdev.pradoeduardoluiz.cadastrodecds.R;

import java.util.ArrayList;
import java.util.List;

import com.appdev.pradoeduardoluiz.cadastrodecds.domain.Cd;
import com.appdev.pradoeduardoluiz.cadastrodecds.domain.DataStore;

public class CdAdapter extends RecyclerView.Adapter<CdAdapter.CdHolder> implements Filterable {

    private List<Cd> cdsList = null;
    private List<Cd> cdsListFull = null;
    private final int UPDATECDS = 2;
    private Activity activity;

    public CdAdapter(Activity activity){
        this.activity = activity;
        cdsList = DataStore.sharedInstance().getCds();
        cdsListFull = new ArrayList<>(cdsList);
    }


    @Override
    public CdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_cds, viewGroup, false);
        return new CdHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CdHolder cdHolder, int position) {

        Cd cd = cdsList.get(position);
        cdHolder.tvName.setText("Album: " + cd.getNome());
        cdHolder.tvArtist.setText("Artista: " + cd.getArtista());
        cdHolder.tvYear.setText("População: " + cd.getAno());

    }

    @Override
    public int getItemCount() {
        return cdsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Cd> filteredList = new ArrayList<>();


                if (constraint == null || constraint.length() == 0){
                    filteredList.addAll(cdsListFull);
                }else{
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Cd cd: cdsListFull) {
                        if(cd.getNome().toLowerCase().contains(filterPattern)) {
                            filteredList.add(cd);
                        }

                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                cdsList.clear();
                cdsList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
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
