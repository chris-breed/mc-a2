package com.practicals.chris.a2;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;


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
            Popup instruction_popup = new Popup();
        }

        // Set up
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment mainFragmentContainer = fragmentManager.findFragmentById(R.id.mainFragmentContainer);
        replaceFragment(new GameStartFragment());

    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    @Override
    protected void onStart() {
        super.onStart();

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