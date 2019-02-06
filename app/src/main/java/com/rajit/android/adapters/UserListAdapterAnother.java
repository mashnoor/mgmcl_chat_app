package com.rajit.android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rajit.android.R;
import com.rajit.android.models.User;
import com.rajit.android.utils.HelperClass;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapterAnother extends BaseQuickAdapter<User, BaseViewHolder> {
    private Activity activity;
    public UserListAdapterAnother(Activity activity, List<User> users) {
        super(R.layout.user_row, users);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, User user) {
        holder.setText(R.id.buddy_name, user.getName());

        holder.setText(R.id.txtDesignation, user.getDesignation());

        Button callButton = holder.getView(R.id.btnCall);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getPhone().equals(HelperClass.getPhone(activity))) {
                    Toast.makeText(activity, "You can't message yourself!", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(activity, ConversationActivity.class);
                intent.putExtra(ConversationUIService.USER_ID, user.getPhone().trim());
                intent.putExtra(ConversationUIService.DISPLAY_NAME, user.getName().trim()); //put it for displaying the title.
                intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                activity.startActivity(intent);

            }
        });

        Button msgButton = holder.getView(R.id.btnMessage);

        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(activity)
                        .withPermission(android.Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {

                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if (user.getPhone().equals(HelperClass.getPhone(activity))) {
                                    Toast.makeText(activity, "You can't call yourself!", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + user.getPhone()));
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

        CircleImageView buddyImage = holder.getView(R.id.buddy_image);

        if(user.getImageurl() !=null)
        {
            if(!user.getImageurl().isEmpty())
                Glide.with(activity).load(user.getImageurl()).into(buddyImage);
        }

    }
}