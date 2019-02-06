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
import com.rajit.android.models.User;
import com.rajit.android.utils.HelperClass;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends ArrayAdapter<User> {
    private Activity activity;

    public UserListAdapter(Activity activity, User[] users) {
        super(activity, 0, users);
        this.activity = activity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final User curr_user = getItem(position);
        if (convertView == null) {
            convertView = activity
                    .getLayoutInflater().inflate(R.layout.user_row, parent, false);
        }
        TextView userName = convertView.findViewById(R.id.buddy_name);
        TextView designation = convertView.findViewById(R.id.txtDesignation);
        Button callButton = convertView.findViewById(R.id.btnCall);
        Button msgButton = convertView.findViewById(R.id.btnMessage);
        CircleImageView buddyImage = convertView.findViewById(R.id.buddy_image);
        designation.setText(curr_user.getDesignation());
        if (curr_user.getPhone().equals(HelperClass.getPhone(activity))) {
            userName.setText(curr_user.getName() + "(Me)");
        } else {
            userName.setText(curr_user.getName());
        }

        if (curr_user.getImageurl() != null) {
            if (!curr_user.getImageurl().isEmpty())
                Picasso.get().load(curr_user.getImageurl()).placeholder(getContext().getResources().getDrawable(R.drawable.no_image)).error(getContext().getResources().getDrawable(R.drawable.no_image)).into(buddyImage);

        }

        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curr_user.getPhone().equals(HelperClass.getPhone(activity))) {
                    Toast.makeText(activity, "You can't call yourself!", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(activity, ConversationActivity.class);
                intent.putExtra(ConversationUIService.USER_ID, curr_user.getPhone().trim());
                intent.putExtra(ConversationUIService.DISPLAY_NAME, curr_user.getName().trim()); //put it for displaying the title.
                intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                activity.startActivity(intent);

            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(activity)
                        .withPermission(android.Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {

                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if (curr_user.getPhone().equals(HelperClass.getPhone(activity))) {
                                    Toast.makeText(activity, "You can't call yourself!", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + curr_user.getPhone()));
                                activity.startActivity(callIntent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(activity, "Permission Denided!", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        })
                        .check();

            }
        });

        return convertView;

    }
}