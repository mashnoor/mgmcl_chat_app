package com.rajit.android.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.rajit.android.R;
import com.rajit.android.utils.HelperClass;
import com.rajit.android.utils.NetChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class SignupActivity extends AppCompatActivity {


    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.txtNickname)
    EditText txtNickname;
    @BindView(R.id.txtId)
    EditText txtIdNo;
    @BindView(R.id.txtDesignation)
    EditText txtDesignation;
    @BindView(R.id.txtPhone)
    EditText txtPhone;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
        if (!HelperClass.getPhone(this).equals("NO")) {
            Intent i = new Intent(SignupActivity.this, UserListActivity.class);
            startActivity(i);
            finish();
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

    @OnClick(R.id.btnSignup)
    public void signup() {
        if(!NetChecker.isNetworkAvailable(this))
        {
            showToast("Can't connect to server");
            return;
        }

        dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage("Creating account. Please wait ...");

        String name = txtName.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String nickname = txtNickname.getText().toString().trim();
        String designation = txtDesignation.getText().toString().trim();
        String idNo = txtIdNo.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(name))
        {
            txtName.setError("Name can't be empty");
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            txtPassword.setError("Password can't be empty");
            return;
        }
        if(TextUtils.isEmpty(phone))
        {
            txtPhone.setError("Phone can't be empty");
            return;
        }
        if(TextUtils.isEmpty(designation))
        {
            txtDesignation.setError("Designation can't be empty");
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_name", name);
        params.put("user_password", password);
        params.put("user_nickname", nickname);
        params.put("user_designation", designation);
        params.put("user_id_no", idNo);
        params.put("user_phone", phone);
        client.post(HelperClass.SIGNUP, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d("--------", response);
                if (response.equals("exists")) {
                    dialog.dismiss();
                    showToast("Already a user exists using this number");

                } else {
                    dialog.dismiss();
                    Gson usergson = new GsonBuilder().create();
                    com.rajit.android.models.User loggedInuser = usergson.fromJson(response, com.rajit.android.models.User.class);


                    Intent i = new Intent(SignupActivity.this, UserListActivity.class);
                    i.putExtra("user_phone", loggedInuser.getPhone());
                    HelperClass.savePhone(SignupActivity.this, loggedInuser.getPhone());
                    dialog.dismiss();
                    addUser(loggedInuser.getPhone(), loggedInuser.getName());
                    startActivity(i);
                    finish();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.d("--------", new String(responseBody));
                showToast("Can't connet to server");
                dialog.dismiss();
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
