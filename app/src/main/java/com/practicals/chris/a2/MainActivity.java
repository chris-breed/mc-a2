package com.practicals.chris.a2;

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

import java.util.Random;


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
    private DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment mainFragmentContainer = fragmentManager.findFragmentById(R.id.mainFragmentContainer);
        replaceFragment(new GameStartFragment());

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbController = new DBController(this);
        SQLiteDatabase db = dbController.getReadableDatabase();

        Random rand = new Random();
        db.execSQL(dbController.insertScore(rand.nextInt(1000) + 1, rand.nextInt(3) + 1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbController.close();
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