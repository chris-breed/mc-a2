package com.practicals.chris.a2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreResultAdapter extends ArrayAdapter {

    protected Context context;
    protected ArrayList<HighscoreRequestSQL> items;

    public HighscoreResultAdapter(Context context, ArrayList<HighscoreRequestSQL> items) {
        super(context, R.layout.list_view_layout, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_layout, null);
        }

        ((TextView) convertView.findViewById(R.id.lv_date)).setText(items.get(position).getDate());
        ((TextView) convertView.findViewById(R.id.lv_score)).setText(String.valueOf(items.get(position).getScore()));

        return convertView;
    }
}
