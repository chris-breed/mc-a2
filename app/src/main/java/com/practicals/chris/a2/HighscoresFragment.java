package com.practicals.chris.a2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighscoresFragment extends Fragment {

    private final ArrayList<ArrayList<HighscoreRequestSQL>> all = new ArrayList<>();
    private ListView listViewHighScores;
    private OnFragmentInteractionListener mListener;

    public HighscoresFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HighscoresFragment newInstance() {
        HighscoresFragment fragment = new HighscoresFragment();
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
        return inflater.inflate(R.layout.fragment_high_scores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBController dbController = new DBController(getContext());
        SQLiteDatabase db = dbController.getReadableDatabase();

        Cursor cursor = db.query(true, DBController.TABLE_NAME,
                null, null, null, null, null, null, null);


        ArrayList<HighscoreRequestSQL> arrayListOfHighScores_1 = new ArrayList<>();
        ArrayList<HighscoreRequestSQL> arrayListOfHighScores_2 = new ArrayList<>();
        ArrayList<HighscoreRequestSQL> arrayListOfHighScores_3 = new ArrayList<>();

        while (cursor.moveToNext()) {
            String datetime = cursor.getString(1);
            int score = cursor.getInt(2);
            int level = cursor.getInt(3);
//            Log.i("DB", String.format("Datetime: %s, Score: %s", datetime, score));

            HighscoreRequestSQL toInsert = new HighscoreRequestSQL(datetime, score, level);

            switch (level) {
                case 1:
                    arrayListOfHighScores_1.add(toInsert);
                    break;
                case 2:
                    arrayListOfHighScores_2.add(toInsert);
                    break;
                case 3:
                    arrayListOfHighScores_3.add(toInsert);
                    break;
            }
        }

        cursor.close();

        // Sorting ArrayList by score.
        Collections.sort(arrayListOfHighScores_1, new Comparator<HighscoreRequestSQL>() {
            @Override
            public int compare(HighscoreRequestSQL o1, HighscoreRequestSQL o2) {
                return Integer.compare(o1.getScore(), o2.getScore());
            }
        });
        Collections.reverse(arrayListOfHighScores_1);

        Collections.sort(arrayListOfHighScores_2, new Comparator<HighscoreRequestSQL>() {
            @Override
            public int compare(HighscoreRequestSQL o1, HighscoreRequestSQL o2) {
                return Integer.compare(o1.getScore(), o2.getScore());
            }
        });
        Collections.reverse(arrayListOfHighScores_2);

        Collections.sort(arrayListOfHighScores_3, new Comparator<HighscoreRequestSQL>() {
            @Override
            public int compare(HighscoreRequestSQL o1, HighscoreRequestSQL o2) {
                return Integer.compare(o1.getScore(), o2.getScore());
            }
        });
        Collections.reverse(arrayListOfHighScores_3);


        all.add(arrayListOfHighScores_1);
        all.add(arrayListOfHighScores_2);
        all.add(arrayListOfHighScores_3);

//        for (int i = 0; i < arrayListOfHighScores.size(); i++) {
//            Log.i("DB", String.valueOf(arrayListOfHighScores.get(i).getScore()));
//        }

        listViewHighScores = view.findViewById(R.id.listView);


        // Buttons
        Button one = getView().findViewById(R.id.btn_score_one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hs_button_click(v);
            }
        });

        Button two = getView().findViewById(R.id.btn_score_two);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hs_button_click(v);
            }
        });

        Button three = getView().findViewById(R.id.btn_score_three);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hs_button_click(v);
            }
        });
    }

    private void hs_button_click(View view) {
        switch (view.getId()) {
            case R.id.btn_score_one:
                swapHighScore(0);
                break;
            case R.id.btn_score_two:
                swapHighScore(1);
                break;
            case R.id.btn_score_three:
                swapHighScore(2);
                break;
        }
    }

    private void swapHighScore(int level) {
        HighscoreResultAdapter adapter = new HighscoreResultAdapter(getContext(), all.get(level));
        listViewHighScores.setAdapter(adapter);
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