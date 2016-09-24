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

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


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

    ArrayList<BuddyClass> buddyList;

    AsyncHttpClient client;
    private void getBuddyList() {

        client.get(HelperClass.GET_FRIENDS + HelperClass.getPhone(BuddyList.this), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                //  showToast(result);

                try {
                    JSONArray all_buddy_array = new JSONArray(result);
                    for (int i = 0; i<all_buddy_array.length(); i++)
                    {
                        JSONObject curr_buddy = all_buddy_array.getJSONObject(i);
                        BuddyClass tmp_post = new BuddyClass(curr_buddy.getString("user_name"), curr_buddy.getString("user_phone"));

                        buddyList.add(tmp_post);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                BuddyListAdapter adapter = new BuddyListAdapter(getApplicationContext(), buddyList);

                buddyListView.setAdapter(adapter);


                Log.d("----------", result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_list);
        ButterKnife.bind(this);

        client = new AsyncHttpClient();
        buddyList = new ArrayList<>();

        getBuddyList();


    }


    public class BuddyListAdapter extends ArrayAdapter<BuddyClass>
    {
        public BuddyListAdapter(Context context, ArrayList<BuddyClass> buddies) {
            super(context, 0, buddyList);
        }





        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final BuddyClass curr_buddy = getItem(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.buddy_list_like, parent, false);
            }


            TextView buddyName = (TextView) convertView.findViewById(R.id.buddy_name);
            buddyName.setText(curr_buddy.getBuddyName());

            CircleImageView buddyImage = (CircleImageView) convertView.findViewById(R.id.buddy_image);
            Glide.with(BuddyList.this).load(HelperClass.GET_IMAGE_FILE + curr_buddy.getBuddyNumber()).into(buddyImage);


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