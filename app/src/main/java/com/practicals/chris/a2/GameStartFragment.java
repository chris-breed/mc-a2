package com.practicals.chris.a2;

import android.annotation.SuppressLint;
import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameStartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameStartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameStartFragment extends Fragment {

    public Random rand = new Random();
    int count = 0; // Keeps track of how many values are in the new arrayList

    TextView number_1;
    TextView number_2;
    TextView number_3;
    TextView number_4;
    TextView number_5;
    TextView number_6;
    TextView number_7;

    Button bigNumberButton;
    Button smallNumberButton;

    ArrayList<TextView> numberTextViews;
    ArrayList<Integer> newValues = new ArrayList<>();
    TextView goalText;
    int[] numbersReceived;
    Countdown countdownController;
    private OnFragmentInteractionListener mListener;
    // min/max for small/big number generation
    private int smallMin = 1;
    private int smallMax = 10;
    private int bigMin = 10;
    private int bigMax = 100;

    public GameStartFragment() {
        // Required empty public constructor
    }

    public static GameStartFragment newInstance(String param1, String param2) {
        GameStartFragment fragment = new GameStartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("Game", "GameStartFragment created.");

        numberTextViews = new ArrayList<>();
        number_1 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_1);
        number_2 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_2);
        number_3 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_3);
        number_4 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_4);
        number_5 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_5);
        number_6 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_6);
        number_7 = Objects.requireNonNull(getView()).findViewById(R.id.numbers_7);

        bigNumberButton = getView().findViewById(R.id.bigButton);
        bigNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewValue(getNewNumber(bigMin, bigMax));
            }
        });
        smallNumberButton = getView().findViewById(R.id.smallButton);
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

        goalText = Objects.requireNonNull(getView()).findViewById(R.id.playGoalText);

        countdownController = new Countdown(1000, 50); // min/max for the goal number
        countdownController.start();
        goalText.setText(String.valueOf(countdownController.getGoalNumber()));

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    void addNewValue(int valueToBeAdded) {
        numberTextViews.get(count).setText(String.format("%d", valueToBeAdded));
        count++;
        newValues.add(valueToBeAdded);

        if (count >= 7) {
            moreThanSeven();
        }
    }

    // Runs when the newValues ArrayList has 7 values.
    private void moreThanSeven() {
        int[] valuesToBePassed = new int[7];
        for (int i = 0; i < newValues.size(); i++) {
            valuesToBePassed[i] = newValues.get(i);
        }

        // newValues ArrayList and the goalNumber int.
        Numbers numbers = new Numbers(valuesToBePassed, countdownController.getGoalNumber());

        Log.i("Game", "Goal Number: " + numbers.getGoalNumber() + ". Base numbers: " + Arrays.toString(numbers.getNumberArray()));
//        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), MainActivity.class)
//                .putExtra("FromStart", true)
//                .putExtra("newValues", numbers);
//        getActivity().startActivity(intent);

        Bundle bundle = new Bundle();
        bundle.putInt("goal", numbers.getGoalNumber());
        bundle.putIntArray("values", numbers.getNumberArray());

        startNewPlayFragmentWithBundle(bundle);
    }

    void startNewPlayFragmentWithBundle(Bundle goalAndValues) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((ViewGroup) (Objects.requireNonNull(getView()).getParent())).getId(),
                GamePlayFragment.newInstance(goalAndValues));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private int getNewNumber(int min, int max) {
        int number = 0;
        while (number <= 0) {
            number = rand.nextInt(max - min) + min;
        }
        return number;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
