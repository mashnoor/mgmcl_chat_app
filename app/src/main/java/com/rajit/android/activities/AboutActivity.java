package com.rajit.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rajit.android.R;
import com.rajit.android.utils.SideBar;

import butterknife.ButterKnife;


public class AboutActivity extends AppCompatActivity {


    SideBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bar = new SideBar(this);

    }

}
