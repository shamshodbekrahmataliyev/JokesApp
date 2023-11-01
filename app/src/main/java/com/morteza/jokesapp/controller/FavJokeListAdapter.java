package com.morteza.jokesapp.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.morteza.jokesapp.R;
import com.morteza.jokesapp.model.Joke;
import com.morteza.jokesapp.view.FavJokeViewHolder;

import java.util.List;

public class FavJokeListAdapter extends RecyclerView.Adapter<FavJokeViewHolder> {

    private List<Joke> mJokeList;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public FavJokeListAdapter(List jokeList, Context context) {
        mJokeList = jokeList;
        mContext = context;
    }

    @NonNull
    @Override
    public FavJokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_joke_item, parent, false);
        return new FavJokeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavJokeViewHolder holder, int position) {


        String jokeText = mJokeList.get(position).getJokeText();
        holder.getTxtFavJoke().setText(jokeText);

        holder.getImgButtonShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = jokeText;
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Mama Joke!");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                mContext.startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mJokeList.size();
    }
}
