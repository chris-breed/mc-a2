package com.practicals.chris.a2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private OnFragmentInteractionListener mListener;

    int[] times = new int[]{120, 60, 30};

    int[] newValues;

    Button playButton1;
    Button playButton2;
    Button playButton3;
    Button playButton4;
    Button playButton5;
    Button playButton6;
    Button playButton7;

    Button addition;
    Button subtraction;
    Button multiplication;
    Button division;

    Button equals;

    int currentTotal;
    int currentNumber;

    TextView totalText;

    public GamePlayFragment() {
        // Required empty public constructor
    }

    public static GamePlayFragment newInstance(Bundle bundle) {
        GamePlayFragment fragment = new GamePlayFragment();
        Bundle args = new Bundle();

        args.putInt("goal", bundle.getInt("goal"));
        args.putIntArray("values", bundle.getIntArray("values"));

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

        Log.i("Game", "GamePlayFragment created.");

        newValues = Objects.requireNonNull(getArguments()).getIntArray("values");
        int goalNumber = Objects.requireNonNull(getArguments()).getInt("goal");

        Log.i("Game", Arrays.toString(newValues) + ", From PlayFragment.");

        TextView goalText = Objects.requireNonNull(getView()).findViewById(R.id.goalText);
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
                test(values[0], currentOperand[0]);
            }
        });


        playButton2 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_2);
        playButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[1];
                test(values[0], currentOperand[0]);
            }
        });

        playButton3 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_3);
        playButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[2];
                test(values[0], currentOperand[0]);
            }
        });

        playButton4 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_4);
        playButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[3];
                test(values[0], currentOperand[0]);
            }
        });

        playButton5 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_5);
        playButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[4];
                test(values[0], currentOperand[0]);
            }
        });

        playButton6 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_6);
        playButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[5];
                test(values[0], currentOperand[0]);
            }
        });

        playButton7 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_7);
        playButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressableButtons(true);
                values[0] = newValues[6];
                test(values[0], currentOperand[0]);

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

        // Timer
        timer();

    }

    public void test(int value, String currentOperand) {
        currentTotal = Integer.parseInt(totalText.getText().toString());
        currentNumber = Integer.parseInt(totalText.getText().toString());
        if (currentTotal == 0) {
            totalText.setText(String.valueOf(value));
        } else {
            String equation = String.format("%s %s %s", currentNumber, currentOperand, value);
            Log.i("Play", equation);
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

        Button submit = getView().findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void pressableButtons(boolean number) {
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

    // Current total and Goal Total
    public void submit() {
    }

    private void tweet() {

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void timer() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext())
                .getSharedPreferences("CountdownPrefs", MODE_PRIVATE);
        final int pref_level = sharedPreferences.getInt("Level", 2);

        final TextView text_score = getView().findViewById(R.id.txt_score);

        TextView text_timer = getView().findViewById(R.id.txt_timer);
        Timer timer = new Timer(times[pref_level] * 1000, 1000, text_timer) {
            @Override
            public void onFinish() {
                // Do a toast and move on to score submission and twitter
                Toast game_over_toast = Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT);
                game_over_toast.show();

                insertScore(Integer.parseInt(text_score.getText().toString()), pref_level);
                tweet(); //TODO: tweet
            }
        };
        timer.start();
    }

    private void insertScore(int score, int level) {
        DBController dbController = new DBController(getContext());
        SQLiteDatabase db = dbController.getReadableDatabase();

        db.execSQL(dbController.insertScore(score, level + 1));
    }
}
