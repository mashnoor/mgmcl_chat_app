package com.origo.android;

import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.Date;

/**
 * Created by Menon on 7/25/16.
 */
public class Post {

    String userPhone;
    String voiceFile;
    String time;
    String userName;
    String txtPost;


    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getVoiceFile() {
        return voiceFile;
    }

    public String getTime() {
        //CharSequence mtime = DateUtils.getRelativeTimeSpanString(System.currentTimeMillis(), Long.parseLong(time),  DateUtils.MINUTE_IN_MILLIS);
        //Log.d("----------", Long.parseLong(time) - System.currentTimeMillis()  + "");
        return time;
    }

    public String getTxtPost() {
        return txtPost;
    }

    public Post(String name, String userPhone, String voiceFile, String time, String txtPost) {

        this.userName = name;
        this.userPhone = userPhone;
        this.voiceFile = voiceFile;
        this.time = time;
        this.txtPost = txtPost;
    }
}
