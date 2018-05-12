package com.practicals.chris.a2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import java.util.Date;
import java.util.Calendar;
import java.util.Random;


public class MainActivity extends FragmentActivity implements SettingsFragment.OnFragmentInteractionListener, HighScoresFragment.OnFragmentInteractionListener, GameStartFragment.OnFragmentInteractionListener, GamePlayFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment mainFragmentContainer;

    private DBController dbController;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
                    Fragment highScoresFragment = new HighScoresFragment();
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

        // Set up
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mainFragmentContainer = fragmentManager.findFragmentById(R.id.mainFragmentContainer);
        replaceFragment(new GameStartFragment());

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbController = new DBController(this);
        SQLiteDatabase db = dbController.getReadableDatabase();

        Cursor cursor = db.query(true, DBController.TABLE_NAME,
                null, null, null, null, null, null, null);

//        while (cursor.moveToNext()) {
//            String age = cursor.getString(1);
//            Log.i("DB", "Age: " + age);
//        }

        Random rand = new Random();
        db.execSQL(dbController.insertScore(rand.nextInt(1000) + 1));
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
    public void replaceFragment(Fragment newFragment) {


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentContainer, newFragment, newFragment.toString());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}