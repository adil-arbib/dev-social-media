package com.arbib.admin_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Loading extends AppCompatActivity {
    private MyDatabase database;
    private final int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        database = new MyDatabase(this);
        database.singOut();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectUser();
            }
        },TIME_OUT);



    }

    private void redirectUser(){
        if(database.checkUserLogin()){
            Intent intent = new Intent(Loading.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(Loading.this,Login.class);
            startActivity(intent);
            finish();
        }
    }
}