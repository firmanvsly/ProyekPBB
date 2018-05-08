package com.example.firmanvsly.proyekpbb;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class WarnetAdapter extends RecyclerView.Adapter<WarnetAdapter.WarnetViewHolder> implements Filterable {

    private Context mCtx;
    private List<Warnet> warnetList;
    private List<Warnet> warnetListFiltered;

    public WarnetAdapter(Context mCtx, List<Warnet> warnetList) {
        this.mCtx = mCtx;
        this.warnetList = warnetList;
        this.warnetListFiltered = warnetList;
    }

    @Override
    public WarnetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_warnet, parent,false);
        return new WarnetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WarnetViewHolder holder, int position) {
        final Warnet Warnet = warnetList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(Warnet.getGambar())
                .into(holder.imageView);

        holder.tvNama.setText(Warnet.getNama());
        holder.tvDeskripsi.setText(Warnet.getDeskripsi());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx,WarnetDetail.class);
                i.putExtra("warnet_image",warnetList.get(holder.getAdapterPosition()).getGambar());
                i.putExtra("warnet_title",warnetList.get(holder.getAdapterPosition()).getNama());
                i.putExtra("warnet_desc",warnetList.get(holder.getAdapterPosition()).getDeskripsi());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return warnetList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    warnetList = warnetListFiltered;
                } else {
                    List<Warnet> filteredList = new ArrayList<>();
                    for (Warnet row : warnetListFiltered) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    warnetList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = warnetList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                warnetList = (ArrayList<Warnet>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class WarnetViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvDeskripsi;
        ImageView imageView;

        public WarnetViewHolder(View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.card_title);
            tvDeskripsi = itemView.findViewById(R.id.card_text);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}