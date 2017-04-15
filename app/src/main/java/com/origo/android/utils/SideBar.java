package com.origo.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.applozic.mobicomkit.api.account.user.UserClientService;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.origo.android.activities.LoginActivity;
import com.origo.android.activities.UserListActivity;
import com.origo.android.utils.HelperClass;

/**
 * Created by Menon on 9/8/16.
 */
public class SideBar {

    Activity activity;
    public SideBar(final Activity activity)
    {
        this.activity = activity;

        PrimaryDrawerItem profileItem = new PrimaryDrawerItem().withName("Profile");
        PrimaryDrawerItem buddyItem = new PrimaryDrawerItem().withName("Users");
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem().withName("Logout");
        PrimaryDrawerItem exit = new PrimaryDrawerItem().withName("Exit");

        final Drawer drawer = new DrawerBuilder().withActivity(activity)
                .addDrawerItems(profileItem, buddyItem, logoutItem, exit).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position==1)
                        {
                            Intent i = new Intent(activity, UserListActivity.class);
                            activity.startActivity(i);


                        }
                        if(position==2)
                        {
                            HelperClass.savePhone(activity, "NO");
                            new UserClientService(activity).logout();
                            Intent i = new Intent(activity, LoginActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        }

                        return false;
                    }
                }).build();


    }





}
