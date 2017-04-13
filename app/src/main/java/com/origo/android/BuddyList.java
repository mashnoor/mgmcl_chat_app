package com.origo.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.origo.android.models.User;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class BuddyList extends Activity {

    @BindView (R.id.buddyListView) ListView buddyListView;

    User users[];

    AsyncHttpClient client;
    private void getBuddyList() {

        client.get(HelperClass.GET_USERS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                //  showToast(result);
                Log.d("--------", result);

                Gson userListgson = new GsonBuilder().create();
                users = userListgson.fromJson(result, User[].class);

                BuddyListAdapter adapter = new BuddyListAdapter(getApplicationContext(), users);

                buddyListView.setAdapter(adapter);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.d("--------", new String(responseBody));

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_list);
        ButterKnife.bind(this);

        client = new AsyncHttpClient();


        getBuddyList();


    }


    public class BuddyListAdapter extends ArrayAdapter<User>
    {
        public BuddyListAdapter(Context context, User[] users) {
            super(context, 0, users);
        }





        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final User curr_user = getItem(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.buddy_row, parent, false);
            }


            TextView buddyName = (TextView) convertView.findViewById(R.id.buddy_name);
            buddyName.setText(curr_user.getName());

            //CircleImageView buddyImage = (CircleImageView) convertView.findViewById(R.id.buddy_image);
           // Glide.with(BuddyList.this).load(HelperClass.GET_IMAGE_FILE + curr_buddy.getBuddyNumber()).into(buddyImage);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();

                }
            });

            return  convertView;

        }



}
}