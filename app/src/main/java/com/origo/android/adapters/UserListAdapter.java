package com.origo.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.origo.android.R;
import com.origo.android.models.User;

public class UserListAdapter extends ArrayAdapter<User>
{
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
            .getLayoutInflater().inflate(R.layout.buddy_row, parent, false);
        }


        TextView buddyName = (TextView) convertView.findViewById(R.id.buddy_name);
        buddyName.setText(curr_user.getName());

        //CircleImageView buddyImage = (CircleImageView) convertView.findViewById(R.id.buddy_image);
        // Glide.with(UserListActivity.this).load(HelperClass.GET_IMAGE_FILE + curr_buddy.getBuddyNumber()).into(buddyImage);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ConversationActivity.class);
                intent.putExtra(ConversationUIService.USER_ID, curr_user.getPhone().trim());
                intent.putExtra(ConversationUIService.DISPLAY_NAME, curr_user.getName().trim()); //put it for displaying the title.
                activity.startActivity(intent);

            }
        });

        return  convertView;

    }
}