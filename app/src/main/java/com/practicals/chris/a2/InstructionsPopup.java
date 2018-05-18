package com.practicals.chris.a2;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class InstructionsPopup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.9));

    }
}
