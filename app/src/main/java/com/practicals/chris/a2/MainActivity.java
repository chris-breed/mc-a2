package com.practicals.chris.a2;

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
import android.widget.FrameLayout;

import java.util.Objects;

public class MainActivity extends FragmentActivity implements SettingsFragment.OnFragmentInteractionListener, HighScoresFragment.OnFragmentInteractionListener, GameStartFragment.OnFragmentInteractionListener, GamePlayFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment mainFragmentContainer;

    Fragment gamePlayFragment = new GamePlayFragment();
    Fragment settingsFragment = new SettingsFragment();
    Fragment highScoresFragment = new HighScoresFragment();


    Bundle bundleToPass = new Bundle();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_game:
                    replaceFragment(gamePlayFragment);
                    return true;
                case R.id.nav_settings:
                    replaceFragment(settingsFragment);
                    return true;
                case R.id.nav_scores:
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


        // Get data from StartFragment
        Bundle fromStart = getIntent().getExtras();
        Boolean fromStartBoolResult = false;

        Log.i("Game", "Value of fromStart. Should be null when first run.\nIs: " + String.valueOf(fromStart));

        if (fromStart != null && !fromStart.isEmpty()) {
            Log.i("Game", "FromStart contains values.\nPreparing GamePlayFragment.");
            fromStartBoolResult = Objects.requireNonNull(fromStart).getBoolean("FromStart");

            if (fromStartBoolResult.equals(true)) {
                // Bundle up values and start GamePlayFragment
                Log.i("Game", "Beginning GamePlayFragment replacement.");
                Log.i("Game", "FromStart Bundle contains: " + fromStart);
                // Send data to PlayFragment

                Log.i("Game", "Bundling data and replacing mainFragmentContainer");
                bundleToPass.clear();
                bundleToPass.putAll(fromStart);
                GamePlayFragment gamePlayFragment = new GamePlayFragment();
                replaceFragment(gamePlayFragment);
            }
        }
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

// TODO: Crash, click same nav icon twice
