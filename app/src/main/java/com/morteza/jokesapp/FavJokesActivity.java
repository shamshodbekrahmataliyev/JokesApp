package com.morteza.jokesapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.morteza.jokesapp.fragments.FavJokesFragment;

public class FavJokesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_jokes);

        FavJokesFragment mFavJokesFragment = FavJokesFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fav_jokes_container, mFavJokesFragment).commit();
    }
}