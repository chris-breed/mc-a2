package com.practicals.chris.a2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class HighscoreResultAdapter extends ArrayAdapter {

    private final ArrayList<HighscoreRequestSQL> items;

    HighscoreResultAdapter(Context context, ArrayList<HighscoreRequestSQL> items) {
        super(context, R.layout.list_view_layout, items);
        this.items = items;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_layout, null);
        }

        ((TextView) convertView.findViewById(R.id.lv_date)).setText(items.get(position).getDate());
        ((TextView) convertView.findViewById(R.id.lv_score)).setText(String.valueOf(items.get(position).getScore()));
        ((TextView) convertView.findViewById(R.id.lv_level)).setText(String.valueOf(items.get(position).getLevel()));

        return convertView;
    }
}
