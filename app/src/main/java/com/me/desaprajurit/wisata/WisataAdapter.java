package com.me.desaprajurit.wisata;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.me.desaprajurit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.WisataViewHolder> implements Filterable {
    private ArrayList<WisataModel> dataModel;
    private ArrayList<WisataModel> mFilteredList;
    private Context context;
    private LayoutInflater inflater;
    ValueFilter valueFilter;



    public WisataAdapter(Context context, ArrayList<WisataModel> dataModel) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dataModel = dataModel;
        this.mFilteredList = dataModel;

    }

    @Override
    public WisataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_wisata, parent, false);
        WisataViewHolder holder = new WisataViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WisataViewHolder holder, final int position) {
        holder.textNamaWista.setText(dataModel.get(position).getsNamaWisata());
        holder.textLokasi.setText(dataModel.get(position).getsAlamat());
        Picasso.with(context).load(dataModel.get(position).getsFoto()).resize(120, 60).into(holder.gambar);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idDikirim = dataModel.get(position).getsId();
                System.out.println("id Dikirim >>" + idDikirim);
                String id = "id";
                context = view.getContext();
                Intent intent = new Intent(context, DetailWisataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(id, idDikirim);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            System.out.println("eksekusi >" + valueFilter);
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<WisataModel> filterList = new ArrayList<WisataModel>();
                for (int i = 0; i < mFilteredList.size(); i++) {
                    if ((mFilteredList.get(i).getsNamaWisata().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        WisataModel industr = new WisataModel(mFilteredList
                                .get(i).getsNamaWisata(), mFilteredList.get(i)
                                .getsAlamat(), mFilteredList.get(i)
                                .getsKeterangan(),mFilteredList.get(i)
                                .getsFoto(),mFilteredList.get(i)
                                .getsId(),mFilteredList.get(i)
                                .getsSejarah());
                        System.out.println("hasil industry >:" + mFilteredList
                                .get(i).getsNamaWisata());
                        filterList.add(industr);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilteredList.size();
                results.values = mFilteredList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            dataModel = (ArrayList<WisataModel>) results.values;
            notifyDataSetChanged();
        }

    }

    public class WisataViewHolder extends RecyclerView.ViewHolder{
        private TextView textNamaWista, textLokasi;
        private ImageView gambar;
        private Button detail;


        public WisataViewHolder(View itemView) {
            super(itemView);
            textLokasi = itemView.findViewById(R.id.textLokasi);
            textNamaWista = itemView.findViewById(R.id.textJudul);
            gambar = itemView.findViewById(R.id.imgGambar);
            detail = itemView.findViewById(R.id.btnDetail);
        }
    }

}
