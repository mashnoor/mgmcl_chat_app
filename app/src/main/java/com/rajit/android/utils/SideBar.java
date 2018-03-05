package com.rajit.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.applozic.mobicomkit.api.account.user.UserClientService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.rajit.android.activities.AboutActivity;
import com.rajit.android.activities.LoginActivity;
import com.rajit.android.activities.UserListActivity;

/**
 * Created by Menon on 9/8/16.
 */
public class SideBar {

    Activity activity;
    public SideBar(final Activity activity)
    {
        this.activity = activity;

        PrimaryDrawerItem profileItem = new PrimaryDrawerItem().withName("Open Conversations");
        PrimaryDrawerItem buddyItem = new PrimaryDrawerItem().withName("User List");
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem().withName("Logout");
        PrimaryDrawerItem atg = new PrimaryDrawerItem().withName("At a glance MGMCL");
        PrimaryDrawerItem exit = new PrimaryDrawerItem().withName("Exit");

       new DrawerBuilder().withActivity(activity)
                .addDrawerItems(profileItem, buddyItem, atg, logoutItem).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position==1)
                        {
                            Intent i = new Intent(activity, UserListActivity.class);
                            activity.startActivity(i);


                        }
                        else if(position==3)
                        {
                            HelperClass.savePhone(activity, "NO");
                            new UserClientService(activity).logout();
                            Intent i = new Intent(activity, LoginActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        }
                        else if(position==0)
                        {
                            Intent intent = new Intent(activity, ConversationActivity.class);

                            activity.startActivity(intent);
                        }
                        else if(position==2)
                        {
                            Intent i = new Intent(activity, AboutActivity.class);
                            activity.startActivity(i);
                        }

                        return false;
                    }
                }).build();


    }





}
