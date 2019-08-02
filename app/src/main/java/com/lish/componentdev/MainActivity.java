package com.lish.componentdev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lish.comminlib.Arouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toJump(View view) {

        Arouter.getInstance().toJumpActivity("login/login",null);
        //ceshig
    }
}
