package com.lish.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lish.annotation.BindPath;

@BindPath("mine/mine")
public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
    }
}
