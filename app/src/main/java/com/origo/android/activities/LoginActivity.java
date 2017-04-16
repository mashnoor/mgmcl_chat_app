package com.origo.android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.origo.android.R;
import com.origo.android.utils.HelperClass;
import com.origo.android.utils.NetChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity {


    @BindView(R.id.etUserPhone)
    EditText etUserPhone;
    @BindView(R.id.etUserpassword)
    EditText etUserPassword;

    AsyncHttpClient client;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!HelperClass.getPhone(this).equals("NO")) {
            Intent i = new Intent(LoginActivity.this, UserListActivity.class);
            startActivity(i);
            finish();
        }
        ButterKnife.bind(this);
        client = new AsyncHttpClient();

    }

    //Handle When Join Button is Pressed
    public void join(View v) {
        if(!NetChecker.isNetworkOnline(this))
        {
            showToast("Can't connect to the internet");
            return;
        }
        //Fetch the Name
        if (etUserPhone.getText().toString().equals("")) {
            etUserPhone.setError("Phone can't be blank");
        } else if (etUserPassword.getText().toString().equals("")) {
            etUserPassword.setError("Password can't be blank");
        } else {

            dialog = new ProgressDialog(this);
            dialog.setMessage("Loggin In. Please wait ....");
            dialog.show();
            sendAndAddUser();

        }

    }

    private void addUser(String userid, String userName) {
        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                // After successful registration with Applozic server the callback will come here
                ApplozicSetting.getInstance(context).showStartNewButton();//To show contact list.
                //ApplozicSetting.getInstance(context).enableRegisteredUsersContactCall();//To enable the applozic Registered Users Contact Note:for disable that you can comment this line of code
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                // If any failure in registration the callback  will come here
            }
        };

        User user = new User();
        user.setUserId(userid); //userId it can be any unique user identifier
        user.setDisplayName(userName); //displayName is the name of the user which will be shown in chat messages

        // user.setImageLink(HelperClass.GET_IMAGE_FILE + user_phone.getText().toString().trim());//optional,pass your image link
        new UserLoginTask(user, listener, this).execute((Void) null);
    }

    private void sendAndAddUser() {
        // RequestParams userPhone = new RequestParams("user_phone", user_phone.getText().toString().trim());
        RequestParams params = new RequestParams();
        params.put("user_phone", etUserPhone.getText().toString().trim());
        params.put("user_password", etUserPassword.getText().toString().trim());

        client.post(HelperClass.LOGIN, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (response.equals("failed")) {
                    showToast("Failed to login! Please check credentials!");
                    dialog.dismiss();
                    return;
                }

                Gson usergson = new GsonBuilder().create();
                com.origo.android.models.User loggedInuser = usergson.fromJson(response, com.origo.android.models.User.class);


                HelperClass.savePhone(LoginActivity.this, loggedInuser.getPhone());
                dialog.dismiss();
                addUser(loggedInuser.getPhone(), loggedInuser.getName());
                Intent i = new Intent(LoginActivity.this, UserListActivity.class);
                startActivity(i);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                Log.d("--------", response);
                dialog.dismiss();
                showToast("Can't connect to server");
            }
        });


    }


    public void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


}
