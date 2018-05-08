package com.practicals.chris.a2;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class GamePlayFragment extends Fragment {

    int goalNumber;
    int[] newValues;
    Button playButton1;
    Button playButton2;
    Button playButton3;
    Button playButton4;
    Button playButton5;
    Button playButton6;
    Button playButton7;
    ArrayList<Button> buttonArrayList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        numbers = Objects.requireNonNull(getArguments()).getIntArray("FromMainActivityToMainPlayFragment");

        Log.i("Game", "GamePlayFragment created.");

        newValues = Objects.requireNonNull(getArguments()).getIntArray("values");
        goalNumber = Objects.requireNonNull(getArguments()).getInt("goal");

        Log.i("Game", Arrays.toString(newValues) + ", From PlayFragment.");

        TextView goalText = Objects.requireNonNull(getView()).findViewById(R.id.goalText);

        playButton1 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_1);
        playButton2 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_2);
        playButton3 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_3);
        playButton4 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_4);
        playButton5 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_5);
        playButton6 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_6);
        playButton7 = Objects.requireNonNull(getView()).findViewById(R.id.play_button_7);

        buttonArrayList.add(playButton1);
        buttonArrayList.add(playButton2);
        buttonArrayList.add(playButton3);
        buttonArrayList.add(playButton4);
        buttonArrayList.add(playButton5);
        buttonArrayList.add(playButton6);
        buttonArrayList.add(playButton7);

        for (int i = 0; i < buttonArrayList.size(); i++) {
            buttonArrayList.get(i).setText(String.valueOf(newValues[i]));
        }
        goalText.setText(String.valueOf(goalNumber));
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
