package com.chat.chatapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Menon on 7/24/16.
 */
public class HelperClass {


    //Constants
    final public static String ADD_USER_URL = "http://192.168.0.120/adduser";

    final public static String ADD_FRIEND_URL = "http://192.168.0.120/addfriends/";

    final public static String UPLOAD_FILE = "http://192.168.0.120/addpost/";
    final public static String FRIENDS_LATEST_POSTS = "http://192.168.0.120/latestposts/";

    final public static String GET_AUDIO_FILE = "http://192.168.0.120/getfile/";

    final public static String GET_IMAGE_FILE = "http://192.168.0.120/getimage/";

    final public static String UPLOAD_IMAGE= "http://192.168.0.120/addimage/";


    public void saveName(Context context, String name)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("ChatApp", Context.MODE_PRIVATE).edit();
        editor.putString("username", name);

        editor.apply();
    }


    public void savePhone(Context context, String phone)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("ChatApp", Context.MODE_PRIVATE).edit();
        editor.putString("userphone", phone);

        editor.apply();
    }

    public String getName(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("ChatApp", Context.MODE_PRIVATE);


            String name = prefs.getString("name", "NO");//"No name defined" is the default value.
           return name;

    }


    public String getPhone(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("ChatApp", Context.MODE_PRIVATE);


        String phone = prefs.getString("userphone", "NO");//"No name defined" is the default value.
        return phone;

    }

}
