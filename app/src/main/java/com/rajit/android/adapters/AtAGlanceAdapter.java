package com.rajit.android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rajit.android.R;
import com.rajit.android.models.AtAGlanceItem;
import com.rajit.android.models.User;
import com.rajit.android.utils.HelperClass;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

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