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
import android.widget.TextView;

import java.util.Arrays;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameMainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    TextView goalText;
    int[] numbersReceived;

    public GameMainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GameMainFragment newInstance() {
        GameMainFragment fragment = new GameMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goalText = Objects.requireNonNull(getView()).findViewById(R.id.goalText);

        Countdown countdownController = new Countdown(1000, 50); // min/max for the goal number
        countdownController.start(); // Just gets a random number TODO: for now
        goalText.setText(String.format("%d", countdownController.getGoalNumber()));

        Bundle bundle = getArguments();

        if (bundle != null) {
            numbersReceived = Objects.requireNonNull(bundle).getIntArray("FromMainActivityToMainPlayFragment");
            Log.i("Game", Arrays.toString(numbersReceived) + ", From MainFragment.");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_game_main, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

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
