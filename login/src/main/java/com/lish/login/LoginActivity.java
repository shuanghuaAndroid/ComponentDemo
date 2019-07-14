package com.lish.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lish.annotation.BindPath;

@BindPath("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
