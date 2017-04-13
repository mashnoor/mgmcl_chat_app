package com.origo.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.StringBuilderPrinter;

/**
 * Created by Menon on 7/24/16.
 */
public class HelperClass {


    //Constants
    final public static String BASE_URL = "http://192.163.255.182/api/";
    final public static String ADD_USER_URL =  BASE_URL + "signup/";

    final public static String GET_USERS = "http://192.163.255.182/api/users/";



    public void saveName(Context context, String name)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("rajit", Context.MODE_PRIVATE).edit();
        editor.putString("username", name);

        editor.apply();
    }


    public static void savePhone(Context context, String phone)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("rajit", Context.MODE_PRIVATE).edit();
        editor.putString("userphone", phone);

        editor.apply();
    }

    public String getName(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("rajit", Context.MODE_PRIVATE);


            String name = prefs.getString("name", "NO");//"No name defined" is the default value.
           return name;

    }


    public static String getPhone(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("rajit", Context.MODE_PRIVATE);


        String phone = prefs.getString("userphone", "NO");//"No name defined" is the default value.
        return phone;

    }

}
