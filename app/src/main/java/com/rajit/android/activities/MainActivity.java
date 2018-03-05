package com.rajit.android.activities;

import android.app.Activity;

import android.os.Bundle;

import com.rajit.android.utils.HelperClass;
import com.rajit.android.R;
import com.rajit.android.utils.SideBar;

import butterknife.ButterKnife;



public class MainActivity extends Activity {

    String myPhoneNumber;
    SideBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bar = new SideBar(this);
        myPhoneNumber = HelperClass.getPhone(this);



    }



}
