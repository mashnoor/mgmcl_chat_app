package com.origo.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.origo.android.adapters.UserListAdapter;
import com.origo.android.utils.HelperClass;
import com.origo.android.R;
import com.origo.android.models.User;
import com.origo.android.utils.SideBar;


import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class UserListActivity extends Activity {

    @BindView (R.id.buddyListView) ListView buddyListView;

    User users[];

    AsyncHttpClient client;

    SideBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        bar = new SideBar(this);

        client = new AsyncHttpClient();
        getBuddyList();


    }
    private void getBuddyList() {

        client.get(HelperClass.GET_USERS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                //  showToast(result);
                Log.d("--------", result);

                Gson userListgson = new GsonBuilder().create();
                users = userListgson.fromJson(result, User[].class);
                UserListAdapter adapter = new UserListAdapter(UserListActivity.this, users);
                buddyListView.setAdapter(adapter);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(UserListActivity.this, "Can't connect to server!", Toast.LENGTH_LONG).show();

            }
        });

    }



}