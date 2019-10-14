package com.me.desaprajurit.berita;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.desaprajurit.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {
    private ArrayList<BeritaModel> dataModel;
    private Context context;
    private LayoutInflater inflater;


    public BeritaAdapter(Context context, ArrayList<BeritaModel> dataModel) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dataModel = dataModel;
    }




    @Override
    public BeritaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_berita, parent, false);
        BeritaViewHolder holder = new BeritaViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BeritaViewHolder holder, final int position) {
        holder.textBerita.setText(dataModel.get(position).getsJudul());
        Picasso.with(context).load(dataModel.get(position).getsFoto()).resize(120, 60).into(holder.gambar);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idKu = "id";
                String idDikirim = dataModel.get(position).getsId();
                context = view.getContext();
                Intent intent = new Intent(context, DetailsBeritaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(idKu, idDikirim);
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder{
        private TextView textBerita;
        private ImageView gambar;
        private FrameLayout item;


        public BeritaViewHolder(View itemView) {
            super(itemView);
            textBerita = itemView.findViewById(R.id.textJudul);
            gambar = itemView.findViewById(R.id.imgGambar);
            item = itemView.findViewById(R.id.itemBerita);
//
        }
    }

}
