package com.practicals.chris.a2;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HighScoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HighScoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HighScoresFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private DBController dbController;

    public HighScoresFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HighScoresFragment newInstance() {
        HighScoresFragment fragment = new HighScoresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_high_scores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbController = new DBController(getContext());
        SQLiteDatabase db = dbController.getReadableDatabase();

        Cursor cursor = db.query(true, DBController.TABLE_NAME,
                null, null, null, null, null, null, null);


        ArrayList<HighscoreRequestSQL> arrayListOfHighScores = new ArrayList<>();

        while (cursor.moveToNext()) {
            String datetime = cursor.getString(1);
            String score = cursor.getString(2);
//            Log.i("DB", String.format("Datetime: %s, Score: %s", datetime, score));

            HighscoreRequestSQL toInsert = new HighscoreRequestSQL(datetime, score);
            arrayListOfHighScores.add(toInsert);
        }

        // Sorting ArrayList by score.
        Collections.sort(arrayListOfHighScores, new Comparator<HighscoreRequestSQL>() {
            @Override
            public int compare(HighscoreRequestSQL o1, HighscoreRequestSQL o2) {
                return Integer.compare(o1.getScore(), o2.getScore());
            }
        });

//        for (int i = 0; i < arrayListOfHighScores.size(); i++) {
//            Log.i("DB", String.valueOf(arrayListOfHighScores.get(i).getScore()));
//        }

        Collections.reverse(arrayListOfHighScores);

        HighscoreResultAdapter adapter = new HighscoreResultAdapter(getContext(), arrayListOfHighScores);


        ListView listViewDates = view.findViewById(R.id.listView);

        // Set the Adapters
        listViewDates.setAdapter(adapter);
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
