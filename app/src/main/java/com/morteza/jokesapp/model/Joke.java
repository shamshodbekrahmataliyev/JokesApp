package com.morteza.jokesapp.model;

public class Joke {


    private String jokeText;
    private boolean jokeIsLiked;

    public Joke(String jokeText, boolean jokeIsLiked) {
        this.jokeText = jokeText;
        this.jokeIsLiked = jokeIsLiked;
    }

    public String getJokeText() {
        return jokeText;
    }

    public void setJokeText(String jokeText) {
        this.jokeText = jokeText;
    }

    public boolean isJokeLiked() {
        return jokeIsLiked;
    }

    public void setJokeLiked(boolean jokeIsLiked) {
        this.jokeIsLiked = jokeIsLiked;
    }

}
