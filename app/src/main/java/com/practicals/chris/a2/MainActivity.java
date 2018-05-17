package com.practicals.chris.a2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;


public class MainActivity extends FragmentActivity implements SettingsFragment.OnFragmentInteractionListener, HighscoresFragment.OnFragmentInteractionListener, GameStartFragment.OnFragmentInteractionListener, GamePlayFragment.OnFragmentInteractionListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_game:
                    Fragment gameStartFragment = new GameStartFragment();
                    replaceFragment(gameStartFragment);
                    return true;
                case R.id.nav_settings:
                    Fragment settingsFragment = new SettingsFragment();
                    replaceFragment(settingsFragment);
                    return true;
                case R.id.nav_scores:
                    Fragment highScoresFragment = new HighscoresFragment();
                    replaceFragment(highScoresFragment);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstTime()) {
            startActivity(new Intent(MainActivity.this, Popup.class));
        }

        DBController dbController = new DBController(getApplicationContext());
        SQLiteDatabase db = dbController.getReadableDatabase();
        db.execSQL(dbController.createDB());

        // Set up
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(new GameStartFragment());
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }
        return !ranBefore;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void tweeter(int score) {
        TweetComposer.Builder tweet_builder = new TweetComposer.Builder(this)
                .text(String.format("I achieved a score of %s on Countdown App!", score));
        tweet_builder.show();

    }

    void insertScore(int score, int level) {
        DBController dbController = new DBController(this);
        SQLiteDatabase db = dbController.getReadableDatabase();

        db.execSQL(dbController.insertScore(score, level + 1));
    }

    public void gameOver(int score, int level) {
        insertScore(score, level);
        tweeter(score);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // Used for the navigation mostly
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentContainer, newFragment, newFragment.toString());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}