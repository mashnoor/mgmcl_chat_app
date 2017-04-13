package com.origo.android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.origo.android.Manifest;
import com.origo.android.R;
import com.origo.android.models.User;

import org.w3c.dom.Text;

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


        TextView buddyName = (TextView) convertView.findViewById(R.id.buddy_name);
        Button callButton = (Button) convertView.findViewById(R.id.btnCall);
        Button msgButton = (Button) convertView.findViewById(R.id.btnMessage);
        buddyName.setText(curr_user.getName());

        //CircleImageView buddyImage = (CircleImageView) convertView.findViewById(R.id.buddy_image);
        // Glide.with(UserListActivity.this).load(HelperClass.GET_IMAGE_FILE + curr_buddy.getBuddyNumber()).into(buddyImage);


        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ConversationActivity.class);
                intent.putExtra(ConversationUIService.USER_ID, curr_user.getPhone().trim());
                intent.putExtra(ConversationUIService.DISPLAY_NAME, curr_user.getName().trim()); //put it for displaying the title.
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
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + curr_user.getPhone()));
                                activity.startActivity(callIntent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

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