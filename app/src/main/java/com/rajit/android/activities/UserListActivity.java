package com.rajit.android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rajit.android.adapters.UserListAdapter;
import com.rajit.android.adapters.UserListAdapterAnother;
import com.rajit.android.utils.HelperClass;
import com.rajit.android.R;
import com.rajit.android.models.User;
import com.rajit.android.utils.SideBar;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class UserListActivity extends Activity {

    @BindView(R.id.buddyListView)
    RecyclerView buddyListView;

    User users[];

    AsyncHttpClient client;

    SideBar bar;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        bar = new SideBar(this);


        client = new AsyncHttpClient();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Getting User List. Please Wait...");

        getBuddyList();


    }

    private void getBuddyList() {

        client.get(HelperClass.GET_USERS, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                //  showToast(result);
                Log.d("--------", result);
                dialog.dismiss();

                Gson userListgson = new GsonBuilder().create();
                users = userListgson.fromJson(result, User[].class);
                List<User> usersList = Arrays.asList(users);
                UserListAdapterAnother adapter = new UserListAdapterAnother(UserListActivity.this, usersList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                buddyListView.setLayoutManager(mLayoutManager);
                buddyListView.setAdapter(adapter);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                dialog.dismiss();
                Toast.makeText(UserListActivity.this, "Can't connect to server!", Toast.LENGTH_LONG).show();

            }
        });

    }
    private void showToast(String message) {
        Toast.makeText(UserListActivity.this, message, Toast.LENGTH_LONG).show();
    }


}