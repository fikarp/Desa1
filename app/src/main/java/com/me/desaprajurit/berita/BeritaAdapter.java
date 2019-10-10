package com.me.desaprajurit.berita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(BeritaViewHolder holder, int position) {
        holder.textBerita.setText(dataModel.get(position).getsJudul());
        Picasso.with(context).load(dataModel.get(position).getsFoto()).resize(120, 60).into(holder.gambar);


    }



    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder{
        private TextView textBerita;
        private ImageView gambar;


        public BeritaViewHolder(View itemView) {
            super(itemView);
            textBerita = itemView.findViewById(R.id.textJudul);
            gambar = itemView.findViewById(R.id.imgGambar);
            new DownloadImageTask(gambar)
                    .execute(BeritaActivity.URL_FOTO);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
