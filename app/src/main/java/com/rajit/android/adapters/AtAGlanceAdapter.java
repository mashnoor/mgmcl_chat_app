package com.rajit.android.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rajit.android.R;
import com.rajit.android.models.AtAGlanceItem;

public class AtAGlanceAdapter extends ArrayAdapter<AtAGlanceItem> {
    private Activity activity;

    public AtAGlanceAdapter(Activity activity, AtAGlanceItem[] items) {
        super(activity, 0, items);
        this.activity = activity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final AtAGlanceItem currItem = getItem(position);
        if (convertView == null) {
            convertView = activity
                    .getLayoutInflater().inflate(R.layout.ataglance_row, parent, false);
        }
        TextView tvHeading = convertView.findViewById(R.id.tvHeading);
        TextView tvBody = convertView.findViewById(R.id.tvBody);

        tvHeading.setText(currItem.getHeading());
        tvBody.setText(currItem.getDetails());

        return convertView;

    }
}