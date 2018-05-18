package com.practicals.chris.a2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class HighscoresFragment extends Fragment {

    private final ArrayList<ArrayList<HighscoreRequestSQL>> all = new ArrayList<>();
    private ListView listViewHighScores;
    private OnFragmentInteractionListener mListener;


    private int position = 1; // Level 2
    private TextView viewingLevel;

    private GestureDetectorCompat detector;

    public HighscoresFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_high_scores, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBController dbController = new DBController(getContext());
        SQLiteDatabase db = dbController.getReadableDatabase();

        Cursor cursor = db.query(true, DBController.TABLE_NAME,
                null, null, null, null, null, null, null);

        viewingLevel = Objects.requireNonNull(getView()).findViewById(R.id.txt_current_level);

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
        swapHighScore(position);
        viewingLevel.setText(String.format("%s %s", getString(R.string.viewing_highscore_level), String.valueOf(position + 1)));

        listViewHighScores.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeRight() {
                // Decrease
                moveListView(false);
            }

            @Override
            public void onSwipeLeft() {
                // Increase
                moveListView(true);
            }

        });
    }

    private void moveListView(boolean left) {
        if (left) {
            position++;
            swapHighScore(position);
        } else {
            position--;
            swapHighScore(position);
        }
    }

    private void swapHighScore(int level) {
        if (level >= 0 && level <= 2) {
            HighscoreResultAdapter adapter = new HighscoreResultAdapter(getContext(), all.get(level));
            listViewHighScores.setAdapter(adapter);
            viewingLevel.setText(String.format("%s %s", getString(R.string.viewing_highscore_level), String.valueOf(position + 1)));
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
        void onFragmentInteraction(Uri uri);
    }
}