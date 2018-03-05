package com.rajit.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Menon on 7/24/16.
 */
public class HelperClass {


    //Constants
    final public static String BASE_URL = "http://192.163.255.182/api/";
    final public static String LOGIN =  BASE_URL + "login/";

    final public static String GET_USERS = "http://192.163.255.182/api/users/";

    final public static String SIGNUP = BASE_URL + "signup/";



    public void saveUserJson(Context context, String userJson)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("rajit", Context.MODE_PRIVATE).edit();
        editor.putString("userjson", userJson);

        editor.apply();
    }
    public String getUserJson(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("rajit", Context.MODE_PRIVATE);


        return prefs.getString("userjson", "NO");

    }


    public static void savePhone(Context context, String phone)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("rajit", Context.MODE_PRIVATE).edit();
        editor.putString("userphone", phone);

        editor.apply();
    }



    public static String getPhone(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("rajit", Context.MODE_PRIVATE);

        return prefs.getString("userphone", "NO");//"No name defined" is the default value.


    }

}
