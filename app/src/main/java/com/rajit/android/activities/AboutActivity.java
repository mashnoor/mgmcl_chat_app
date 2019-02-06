package com.rajit.android.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rajit.android.R;
import com.rajit.android.adapters.AtAGlanceAdapter;
import com.rajit.android.models.AtAGlanceItem;
import com.rajit.android.utils.HelperClass;
import com.rajit.android.utils.SideBar;


public class AboutActivity extends AppCompatActivity {


    SideBar bar;
    AtAGlanceAdapter adapter;
    ListView atAGlanceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bar = new SideBar(this);

        atAGlanceListView = findViewById(R.id.lvAtAGlance);

        getItems();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void getItems() {
        AsyncHttpClient client = new AsyncHttpClient();
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please wait...");
        client.get(HelperClass.GET_ITEMS, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);

                Gson g = new Gson();
                AtAGlanceItem[] items = g.fromJson(response, AtAGlanceItem[].class);

                adapter = new AtAGlanceAdapter(AboutActivity.this, items);
                atAGlanceListView.setAdapter(adapter);
                dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(AboutActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                finish();

            }
        });
    }

}
