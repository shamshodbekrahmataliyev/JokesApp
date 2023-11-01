package com.morteza.jokesapp.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.morteza.jokesapp.R;

public class FavJokeViewHolder extends RecyclerView.ViewHolder {


    private TextView txtFavJoke;
    private ImageButton imgButtonShare;

    public FavJokeViewHolder(@NonNull View itemView) {
        super(itemView);

        txtFavJoke = itemView.findViewById(R.id.txtFavJoke);
        imgButtonShare = itemView.findViewById(R.id.shareButtonFavListItem);
    }

    public TextView getTxtFavJoke() {
        return txtFavJoke;
    }

    public ImageButton getImgButtonShare() {
        return imgButtonShare;
    }
}
