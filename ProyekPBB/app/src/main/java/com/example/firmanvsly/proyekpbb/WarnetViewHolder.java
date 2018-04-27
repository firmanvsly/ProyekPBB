package com.example.firmanvsly.proyekpbb;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class WarnetViewHolder extends RecyclerView.ViewHolder {

    public ImageView picture;
    public TextView name;
    public TextView description;
    public WarnetViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_card, parent, false));
        picture = (ImageView) itemView.findViewById(R.id.card_image);
        name = (TextView) itemView.findViewById(R.id.card_title);
        description = (TextView) itemView.findViewById(R.id.card_text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                context.startActivity(intent);
            }
        });

        // Adding Snackbar to Action Button inside card
        Button button = (Button)itemView.findViewById(R.id.action_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Action is pressed",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        ImageButton favoriteImageButton =
                (ImageButton) itemView.findViewById(R.id.favorite_button);
        favoriteImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Added to Favorite",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
        shareImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Share article",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
