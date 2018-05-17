package com.practicals.chris.a2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class GamePlayFragment extends Fragment {

    private final ArrayList<Button> buttonArrayList = new ArrayList<>();
    private final int[] times = new int[]{120, 60, 30};
    SoundController soundController;
    int music;
    private SharedPreferences sharedPreferences;
    private int pref_level;
    private OnFragmentInteractionListener mListener;
    private int[] newValues;
    private Button playButton1;
    private Button playButton2;
    private Button playButton3;
    private Button playButton4;
    private Button playButton5;
    private Button playButton6;
    private Button playButton7;
    private Button addition;
    private Button subtraction;
    private Button multiplication;
    private Button division;
    private int currentTotal;
    private TextView totalText;
    private TextView text_score;
    private CountDownTimer timer;

    int previous_score;

    public GamePlayFragment() {
        // Required empty public constructor
    }

    public static GamePlayFragment newInstance(Bundle bundle) {
        GamePlayFragment fragment = new GamePlayFragment();
        Bundle args = new Bundle();

        args.putInt("goal", bundle.getInt("goal"));
        args.putIntArray("values", bundle.getIntArray("values"));
        args.putInt("score", bundle.getInt("score"));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = Objects.requireNonNull(getContext())
                .getSharedPreferences("CountdownPrefs", MODE_PRIVATE);

        pref_level = sharedPreferences.getInt("Level", 2);

        text_score = Objects.requireNonNull(getView()).findViewById(R.id.txt_score);
        previous_score = sharedPreferences.getInt("score", 0);
        text_score.setText(String.valueOf(previous_score));

        Log.i("Game", "GamePlayFragment created.");

        newValues = Objects.requireNonNull(getArguments()).getIntArray("values");
        final int goalNumber = Objects.requireNonNull(getArguments()).getInt("goal");


        Log.i("Game", Arrays.toString(newValues) + ", From PlayFragment.");

        final TextView goalText = Objects.requireNonNull(getView()).findViewById(R.id.goalText);
        totalText = getView().findViewById(R.id.txt_total);

        final int[] values = new int[1];
        currentTotal = Integer.parseInt(totalText.getText().toString());
        final String[] currentOperand = new String[1];

        playButton1 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_1);
        playButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[0];
                equate(values[0], currentOperand[0]);
            }
        });

        playButton2 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_2);
        playButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[1];
                equate(values[0], currentOperand[0]);
            }
        });

        playButton3 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_3);
        playButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[2];
                equate(values[0], currentOperand[0]);
            }
        });

        playButton4 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_4);
        playButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[3];
                equate(values[0], currentOperand[0]);
            }
        });

        playButton5 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_5);
        playButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[4];
                equate(values[0], currentOperand[0]);
            }
        });

        playButton6 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_6);
        playButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[5];
                equate(values[0], currentOperand[0]);
            }
        });

        playButton7 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_7);
        playButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[6];
                equate(values[0], currentOperand[0]);

            }
        });

        addition = getView().findViewById(R.id.play_button_addition);
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(false);
                currentOperand[0] = "+";
            }
        });

        subtraction = getView().findViewById(R.id.play_button_subtraction);
        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(false);
                currentOperand[0] = "-";
            }
        });

        multiplication = getView().findViewById(R.id.play_button_multiply);
        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(false);
                currentOperand[0] = "*";
            }
        });

        division = getView().findViewById(R.id.play_button_division);
        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(false);
                currentOperand[0] = "/";
            }
        });

        buttonArrayList.add(playButton1);
        buttonArrayList.add(playButton2);
        buttonArrayList.add(playButton3);
        buttonArrayList.add(playButton4);
        buttonArrayList.add(playButton5);
        buttonArrayList.add(playButton6);
        buttonArrayList.add(playButton7);

        for (int i = 0; i < buttonArrayList.size(); i++) {
            buttonArrayList.get(i).setText(String.valueOf(Objects.requireNonNull(newValues)[i]));
        }
        goalText.setText(String.valueOf(goalNumber));

        soundController = new SoundController(getContext());
        music = soundController.addSound(R.raw.thirtyseconds);

        soundController.play(music);

        // Timer
        final TextView text_timer = getView().findViewById(R.id.txt_timer);
        timer = new CountDownTimer(times[pref_level] * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                text_timer.setText(String.valueOf(millisUntilFinished).substring(0, String.valueOf(millisUntilFinished).length() - 3));
            }

            @Override
            public void onFinish() {
                Toast game_over_toast = Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT);
                game_over_toast.show();

                soundController.stop(music);


                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setMessage("Do you want to send out a Tweet with your score?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ((MainActivity) Objects.requireNonNull(getActivity()))
                                        .gameOver(Integer.parseInt(text_score.getText().toString()), pref_level, true);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((MainActivity) Objects.requireNonNull(getActivity()))
                                        .gameOver(Integer.parseInt(text_score.getText().toString()), pref_level, false);
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alertBuilder.create();
                alert.show();


            }
        }.start();

        Button submit = getView().findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalFinal = Integer.parseInt(totalText.getText().toString());
                Log.i("Play", String.format("%s, %s", goalNumber, totalFinal));

                int score = calcScore(totalFinal, goalNumber);
                soundController.stop(music);
                timer.cancel();
                // Start new GameStartFragment
                sharedPreferences.edit().putInt("score", score + previous_score).apply();
                startNewStartFragment();
            }
        });
    }


    private void startNewStartFragment() {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((ViewGroup) (Objects.requireNonNull(getView()).getParent())).getId(),
                GameStartFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void equate(int value, String currentOperand) {
        currentTotal = Integer.parseInt(totalText.getText().toString());
        int currentNumber = Integer.parseInt(totalText.getText().toString());
        if (currentTotal == 0) {
            totalText.setText(String.valueOf(value));
        } else {
            String equation = String.format("%s %s %s", currentNumber, currentOperand, value);
//            Log.i("Play", equation);
            String[] equationTokens = equation.split(" ");
            int newTotal = 0;
            switch (equationTokens[1]) {
                case "+":
                    newTotal = Integer.parseInt(equationTokens[0]) + Integer.parseInt(equationTokens[2]);
                    break;
                case "-":
                    newTotal = Integer.parseInt(equationTokens[0]) - Integer.parseInt(equationTokens[2]);
                    break;
                case "*":
                    newTotal = Integer.parseInt(equationTokens[0]) * Integer.parseInt(equationTokens[2]);
                    break;
                case "/":
                    newTotal = Integer.parseInt(equationTokens[0]) / Integer.parseInt(equationTokens[2]);
            }
            totalText.setText(String.valueOf(newTotal));
        }
    }

    private void pressableButtons(boolean number) {
        addition.setEnabled(number);
        subtraction.setEnabled(number);
        multiplication.setEnabled(number);
        division.setEnabled(number);

        playButton1.setEnabled(!number);
        playButton2.setEnabled(!number);
        playButton3.setEnabled(!number);
        playButton4.setEnabled(!number);
        playButton5.setEnabled(!number);
        playButton6.setEnabled(!number);
        playButton7.setEnabled(!number);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private int calcScore(int total, int goal) {
        int score = 0;

        int[] bigGoalRange = new int[]{goal - 200, goal + 200};
        int[] smallGoalRange = new int[]{goal - 100, goal + 100};
        int[] verySmallGoalRange = new int[]{goal - 50, goal + 50};

        if (total == goal) score += 100;

        if (total >= verySmallGoalRange[0] && total <= verySmallGoalRange[1]) score += 45;

        if (total >= smallGoalRange[0] && total <= smallGoalRange[1]) score += 30;

        if (total >= bigGoalRange[0] && total <= bigGoalRange[1]) score += 10;


        Toast score_display_toast = Toast.makeText(getContext(), "You scored: " + score, Toast.LENGTH_SHORT);
        score_display_toast.show();

        Log.i("Play", "Score of " + score);
        return score;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}