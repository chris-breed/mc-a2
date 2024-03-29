package com.practicals.chris.a2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class GameStartFragment extends Fragment {

    private static int previous_score;

    private final ArrayList<Integer> newValues = new ArrayList<>();
    private final int[] level_1_goal = new int[]{100, 500};
    private final int[] level_2_goal = new int[]{500, 1000};
    private final int[] level_3_goal = new int[]{1000, 2000};
    // min/max for small/big number generation
    private final int smallMin = 1;
    private final int smallMax = 10;
    private final int bigMin = 10;
    private final int bigMax = 100;
    private int count = 0; // Keeps track of how many values are in the new arrayList
    private ArrayList<TextView> numberTextViews;
    private OnFragmentInteractionListener mListener;
    private int goalNumber;

    private SensorManager sensorManager;
    private SensorEventListener shakeListener;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private Boolean shook;


    public GameStartFragment() {
        // Required empty public constructor
    }

    public static GameStartFragment newInstance() {

        return new GameStartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shakeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta; // perform low-cut filte

                if (mAccel > 12) {
                    shakeSelectRest();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager = (SensorManager) Objects.requireNonNull(getContext()).getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(shakeListener, Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        // Checking SharedPreferences
        final SharedPreferences sharedPreferences = Objects.requireNonNull(getContext())
                .getSharedPreferences("CountdownPrefs", MODE_PRIVATE);
        final int pref_level = sharedPreferences.getInt("Level", 2);
        previous_score = sharedPreferences.getInt("score", 0);

        Log.i("Game", "GameStartFragment created.");

        final TextView prev_score = Objects.requireNonNull(getView()).findViewById(R.id.txt_current_total_score);
        prev_score.setText(String.valueOf(previous_score));

        Button quit_button = getView().findViewById(R.id.btn_quit);
        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Integer.parseInt(prev_score.getText().toString()) == 0)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setMessage("Are you sure you want to quit?\nYou're current score will be submitted.")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                                    alertBuilder.setMessage("Do you want to send out a Tweet with your score?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    ((MainActivity) Objects.requireNonNull(getActivity()))
                                                            .gameOver(previous_score, pref_level, true);

                                                    sharedPreferences.edit().putInt("score", 0).apply();
                                                    Fragment gameStartFragment = new GameStartFragment();
                                                    ((MainActivity) getActivity()).replaceFragment(gameStartFragment);
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    ((MainActivity) Objects.requireNonNull(getActivity()))
                                                            .gameOver(previous_score, pref_level, false);

                                                    sharedPreferences.edit().putInt("score", 0).apply();
                                                    Fragment gameStartFragment = new GameStartFragment();
                                                    ((MainActivity) getActivity()).replaceFragment(gameStartFragment);

                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert = alertBuilder.create();
                                    alert.show();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    sharedPreferences.edit().putInt("score", 0).apply();
                    Fragment gameStartFragment = new GameStartFragment();
                    ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(gameStartFragment);
                }
            }
        });
        if ((previous_score == 0)) {
            quit_button.setVisibility(View.GONE);
        }

        numberTextViews = new ArrayList<>();
        TextView number_1 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_1);
        TextView number_2 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_2);
        TextView number_3 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_3);
        TextView number_4 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_4);
        TextView number_5 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_5);
        TextView number_6 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_6);
        TextView number_7 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_7);

        Button bigNumberButton = getView().findViewById(R.id.bigButton);
        bigNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewValue(getNewNumber(bigMin, bigMax));
            }
        });
        Button smallNumberButton = getView().findViewById(R.id.smallButton);
        smallNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewValue(getNewNumber(smallMin, smallMax));
            }
        });

        numberTextViews.add(number_1);
        numberTextViews.add(number_2);
        numberTextViews.add(number_3);
        numberTextViews.add(number_4);
        numberTextViews.add(number_5);
        numberTextViews.add(number_6);
        numberTextViews.add(number_7);

        TextView goalText = Objects.requireNonNull(getView()).findViewById(R.id.playGoalText);


        int min = 0;
        int max = 0;
        switch (pref_level) {
            case 0:
                min = level_1_goal[0];
                max = level_1_goal[1];
                break;
            case 1:
                min = level_2_goal[0];
                max = level_2_goal[1];
                break;
            case 2:
                min = level_3_goal[0];
                max = level_3_goal[1];
                break;
        }

        Log.i("Countdown", String.format("%s, %s", min, max));
        Random rand = new Random();
        goalNumber = rand.nextInt((max - min) + 1) + min;
        goalText.setText(String.valueOf(goalNumber));

        shook = false;
    }

    private void shakeSelectRest() {
        Log.i("Shake", "Phone Shook.");
        Random rand = new Random();
        boolean randomNumberBig = rand.nextBoolean();

        if (!shook) {
            while (count < 7) {
                if (randomNumberBig) { // Big numbers
                    addNewValue(getNewRandomNumber(bigMin, bigMax));
                    randomNumberBig = rand.nextBoolean();
                } else {    // Small numbers
                    addNewValue(getNewRandomNumber(smallMin, smallMax));
                    randomNumberBig = rand.nextBoolean();
                }
                shook = true;
                sensorManager.unregisterListener(shakeListener);
            }
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void addNewValue(int valueToBeAdded) {
        numberTextViews.get(count).setText(String.format("%d", valueToBeAdded));
        count++;
        newValues.add(valueToBeAdded);

        if (count >= 7) {
            moreThanSeven();
        }
    }

    private int getNewRandomNumber(int min, int max) {
        int number = 0;
        while (number <= 0) {
            Random rand = new Random();
            number = rand.nextInt(max - min) + min;
        }
        return number;
    }

    // Runs when the newValues ArrayList has 7 values.
    private int getNewNumber(int min, int max) {
        int number = 0;
        while (number <= 0) {
            Random rand = new Random();
            number = rand.nextInt(max - min) + min;
        }
        return number;
    }

    private void moreThanSeven() {
        int[] valuesToBePassed = new int[7];
        for (int i = 0; i < newValues.size(); i++) {
            valuesToBePassed[i] = newValues.get(i);
        }

        // newValues ArrayList and the goalNumber int.
        Numbers numbers = new Numbers(valuesToBePassed, goalNumber);

        Log.i("Game", "Goal Number: " + numbers.getGoalNumber() + ". Base numbers: " + Arrays.toString(numbers.getNumberArray()));

        Bundle bundle = new Bundle();
        bundle.putInt("goal", numbers.getGoalNumber());
        bundle.putIntArray("values", numbers.getNumberArray());

        try {
            bundle.putInt("score", previous_score);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        startNewPlayFragmentWithBundle(bundle);
    }

    private void startNewPlayFragmentWithBundle(Bundle goalAndValues) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((ViewGroup) (Objects.requireNonNull(getView()).getParent())).getId(),
                GamePlayFragment.newInstance(goalAndValues));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(shakeListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
