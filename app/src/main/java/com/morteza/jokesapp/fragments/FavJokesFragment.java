package com.morteza.jokesapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.morteza.jokesapp.R;
import com.morteza.jokesapp.controller.FavJokeListAdapter;
import com.morteza.jokesapp.model.Joke;
import com.morteza.jokesapp.model.JokeManager;

import java.util.ArrayList;
import java.util.List;


public class FavJokesFragment extends Fragment {

    RecyclerView mRecyclerView;
    FavJokeListAdapter mFavJokesAdapter;
    JokeManager mJokeManager;
    private List<Joke> mJokeList = new ArrayList<>();

    private Joke deletedJoke;

    public FavJokesFragment() {
        // Required empty public constructor
    }


    public static FavJokesFragment newInstance() {
        FavJokesFragment fragment = new FavJokesFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mJokeManager = new JokeManager(context);
        mJokeList.clear();
        if (mJokeManager.retrieveJokes().size() > 0) {
            for (Joke joke : mJokeManager.retrieveJokes()) {
                mJokeList.add(joke);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_jokes, container, false);
        if (view != null) {
            mRecyclerView = view.findViewById(R.id.rv);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFavJokesAdapter = new FavJokeListAdapter(mJokeList, getContext());
            mRecyclerView.setAdapter(mFavJokesAdapter);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);

        }
        return view;
    }


    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            switch (direction) {

                case ItemTouchHelper.LEFT:
                case ItemTouchHelper.RIGHT:

                    deletedJoke = mJokeList.get(position);
                    mJokeManager.deleteJoke(mJokeList.get(position));
                    mJokeList.remove(position);
                    mFavJokesAdapter.notifyItemRemoved(position);
                    mFavJokesAdapter.notifyDataSetChanged();

                    Snackbar.make(mRecyclerView, " Joke is \" Removed\"", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    mJokeList.add(position, deletedJoke);
                                    mJokeManager.saveJoke(deletedJoke);
                                    mFavJokesAdapter.notifyItemInserted(position);
                                }
                            }).show();

                    break;
            }
        }
    };


}