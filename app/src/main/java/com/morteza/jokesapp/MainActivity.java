package com.morteza.jokesapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arasthel.asyncjob.AsyncJob;
import com.morteza.jokesapp.controller.CardsDataAdapter;
import com.morteza.jokesapp.controller.JokeLikeListener;
import com.morteza.jokesapp.model.Joke;
import com.morteza.jokesapp.model.JokeManager;
import com.wenchao.cardstack.CardStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CardStack.CardEventListener, JokeLikeListener {


    CardStack mCardStack;
    CardsDataAdapter mCardAdapter;
    private List<Joke> allJokes = new ArrayList<>();
    private JokeManager mJokeManager;


    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJokeManager = new JokeManager(this);
        Log.i("JOKES", loadJSONFromAsset());
        mCardStack = findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.joke_card);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardsDataAdapter(this,0);



        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                handleShakeEvent();
            }
        });



        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        // Do some background work
                        try {

                            JSONObject rootObject = new JSONObject(loadJSONFromAsset());

                            JSONArray fatJokes = rootObject.getJSONArray("fat");
                            addJokesToArrayList(fatJokes, allJokes);

                            JSONArray stupidJokes = rootObject.getJSONArray("stupid");
                            addJokesToArrayList(stupidJokes, allJokes);

                            JSONArray uglyJokes = rootObject.getJSONArray("ugly");
                            addJokesToArrayList(uglyJokes, allJokes);

                            JSONArray nastyJokes = rootObject.getJSONArray("nasty");
                            addJokesToArrayList(nastyJokes, allJokes);

                            JSONArray hairyJokes = rootObject.getJSONArray("hairy");
                            addJokesToArrayList(hairyJokes, allJokes);

                            JSONArray baldJokes = rootObject.getJSONArray("bald");
                            addJokesToArrayList(baldJokes, allJokes);

                            JSONArray oldJokes = rootObject.getJSONArray("old");
                            addJokesToArrayList(oldJokes, allJokes);

                            JSONArray poorJokes = rootObject.getJSONArray("poor");
                            addJokesToArrayList(poorJokes, allJokes);

                            JSONArray shortJokes = rootObject.getJSONArray("short");
                            addJokesToArrayList(shortJokes, allJokes);

                            JSONArray skinnyJokes = rootObject.getJSONArray("skinny");
                            addJokesToArrayList(skinnyJokes, allJokes);

                            JSONArray tallJokes = rootObject.getJSONArray("tall");
                            addJokesToArrayList(tallJokes, allJokes);

                            JSONArray likeJokes = rootObject.getJSONArray("like");
                            addJokesToArrayList(likeJokes, allJokes);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {

                        for (Joke joke : allJokes) {
                            mCardAdapter.add(joke.getJokeText());
                        }
                        mCardStack.setAdapter(mCardAdapter);

                    }
                }).create().start();





        mCardStack.setListener(this);

    }

    private void handleShakeEvent() {

        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        Collections.shuffle(allJokes);
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {

                        mCardAdapter.clear();
                        mCardAdapter = new CardsDataAdapter(MainActivity.this,
                                0);
                        for (Joke joke : allJokes) {
                            mCardAdapter.add(joke.getJokeText());
                        }
                        mCardStack.setAdapter(mCardAdapter);
                    }
                }).create().start();

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("jokes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void addJokesToArrayList(JSONArray jsonArray, List<Joke> arrayList) {
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    arrayList.add(new Joke(jsonArray.getString(i), false));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean swipeEnd(int section, float distance) {

        return (distance>300)? true : false;
    }

    @Override
    public boolean swipeStart(int section, float distance) {


        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {

        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {


    }

    @Override
    public void topCardTapped() {

    }

    @Override
    public void jokeIsLiked(Joke joke) {


        if (joke.isJokeLiked()) {

            mJokeManager.saveJoke(joke);

        } else {

            mJokeManager.deleteJoke(joke);
        }

    }

    // Menu options

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        startActivity(new Intent(MainActivity.this, FavJokesActivity.class));


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);


    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(mShakeDetector);
    }
}