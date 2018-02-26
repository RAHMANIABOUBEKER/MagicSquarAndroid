package com.example.booba.starttraining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Levelgame extends Activity {

    EditText Editlevelgame ;
    Button PlayeGame;
    //int levelgame ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelgame);

        Editlevelgame = (EditText)findViewById(R.id.Leveledittext);
        PlayeGame = (Button)findViewById(R.id.play);


        Editlevelgame.addTextChangedListener(MyEye);
        checkFieldsForEmptyValues();

        //this.levelgame = Integer.parseInt(Editlevelgame.getText().toString());

    }
    public void Playgame(View v){
        if(Integer.parseInt(Editlevelgame.getText().toString())!= 0 && Integer.parseInt(Editlevelgame.getText().toString())< 4){

        Intent playintent = new Intent(getApplicationContext(), Magic.class);
        playintent.putExtra("niveau", Integer.parseInt(Editlevelgame.getText().toString()));
        startActivity(playintent);}else {
            Toast.makeText(this, " entre 1 or 2 or 3", Toast.LENGTH_SHORT).show();
        }

    }
    private TextWatcher MyEye  = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };
    void  checkFieldsForEmptyValues(){
        if (Editlevelgame.getText().toString().equals("")){
            PlayeGame.setEnabled(false);
        }else{
            PlayeGame.setEnabled(true);
        }
    }

}
