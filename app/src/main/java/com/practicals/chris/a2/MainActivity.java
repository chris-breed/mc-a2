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

import java.util.Arrays;

public class MainActivity extends FragmentActivity implements GameMainFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, HighScoresFragment.OnFragmentInteractionListener, GameStartFragment.OnFragmentInteractionListener, GamePlayFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment mainFragmentContainer;

    Fragment gamePlayFragment = new GamePlayFragment();
    Fragment settingsFragment = new SettingsFragment();
    Fragment highScoresFragment = new HighScoresFragment();

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mainFragmentContainer = fragmentManager.findFragmentById(R.id.mainFragmentContainer);

        Bundle bundle = getIntent().getExtras();

        int[] newValues;

        // Bundle from Game Start fragment, should contain the 7 random big/small values
        if (bundle != null) {
//        assert bundle != null;
            newValues = bundle.getIntArray("FromStartToMain");
            Log.i("Game", Arrays.toString(newValues) + ", from Intent.");

            Bundle gamePlayIntent = new Bundle();
            gamePlayIntent.putIntArray("FromMainActivityToMainPlayFragment", newValues);

            GamePlayFragment newGamePlayFragment = new GamePlayFragment();
            newGamePlayFragment.setArguments(gamePlayIntent); // When GameMainFragment is opened, check the bundle for this, then swap its fragment with the Play fragment
            replaceFragment(newGamePlayFragment);
        } else {
            Log.i("Game", "Bundle from StartFragment is empty");
            assert bundle != null;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment, fragment.toString());
//        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}

// TODO: Crash, click same nav icon twice
