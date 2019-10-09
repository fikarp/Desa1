package com.me.desaprajurit.berita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.desaprajurit.R;

import java.util.ArrayList;
import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {
    private ArrayList<BeritaModel> dataModel;
    private Context context;
    private LayoutInflater inflater;

    public BeritaAdapter(Context context, ArrayList<BeritaModel> dataModel) {

        inflater = LayoutInflater.from(context);
        this.dataModel = dataModel;
    }




    @Override
    public BeritaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_berita, parent, false);
        BeritaViewHolder holder = new BeritaViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BeritaViewHolder holder, int position) {
        holder.textBerita.setText(dataModel.get(position).getsJudul());

    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder{
        private TextView textBerita;


        public BeritaViewHolder(View itemView) {
            super(itemView);
            textBerita = itemView.findViewById(R.id.textJudul);
        }
    }
}
