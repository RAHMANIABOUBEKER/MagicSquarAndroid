package com.example.booba.starttraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.net.Uri;

public class Welcompage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcompage);

        //

        }
    public void aller(View v){

            Intent monintent = new Intent(getApplicationContext(), Levelgame.class);
            startActivity(monintent);

    }


    public void ABoutGame(View v){
        Uri uri = Uri.parse("https://en.wikipedia.org/wiki/Magic_square");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
    public void Scoregame(View v){
        Intent monintent = new Intent(getApplicationContext(), ScoreGame.class);
        startActivity(monintent);
    }




}
