package com.example.booba.starttraining;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.widget.Toast;

public class Magic extends Activity {
    HashMap<String, EditText> list = new HashMap<>();// liste de edittest pour les digits
    HashMap<String, TextView> somme = new HashMap<>(); // liste de textview pour les somme des lignes et des colonne
    long EasyLevelHighscores, MeduimLevelHighscores, HardLevelHighscores;
    long LastScore;
    int carremagic[][] = solution();  //
    EditText helpEdit ;
    Button NewGame, Submit, Help;
    Chronometer mChronometer;
    long timeWhenStopped, timeWhenpause;
    int levelgame;
    boolean NewgameState;
    long elapsedMillis = 0 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);
        // recovering the instance state
        setContentView(R.layout.activity_magic);
        //****************les digit **********************/
        list.put("00", (EditText) findViewById(R.id.x00));
        list.put("01", (EditText) findViewById(R.id.x02));
        list.put("02", (EditText) findViewById(R.id.x04));
        list.put("10", (EditText) findViewById(R.id.x20));
        list.put("11", (EditText) findViewById(R.id.x22));
        list.put("12", (EditText) findViewById(R.id.x24));
        list.put("20", (EditText) findViewById(R.id.x40));
        list.put("21", (EditText) findViewById(R.id.x42));
        list.put("22", (EditText) findViewById(R.id.x44));
        //***************Les sommes***********************/
        somme.put("03", (TextView) findViewById(R.id.x06));
        somme.put("13", (TextView) findViewById(R.id.x26));
        somme.put("23", (TextView) findViewById(R.id.x46));
        somme.put("30", (TextView) findViewById(R.id.x60));
        somme.put("31", (TextView) findViewById(R.id.x62));
        somme.put("32", (TextView) findViewById(R.id.x64));

        //****************Button*************************/
        Help = findViewById(R.id.help);
        Submit = findViewById(R.id.Submit);
        NewGame =findViewById(R.id.New);
        //***********************chronometre**************/
        this.mChronometer = (Chronometer) findViewById(R.id.chronometer1);
        this.timeWhenStopped = 0;
        mChronometer.start();
        this.levelgame = getIntent().getIntExtra("niveau",0);
        Game(carremagic, this.levelgame);

        for (Map.Entry<String, EditText> E : this.list.entrySet()) {
            EditText Case = (EditText) E.getValue();
            Case.addTextChangedListener(MyTextwatcher);
        }
        checkFieldsForEmptyValues();



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //savedInstanceState.putSerializable("HashView", somme);
        //savedInstanceState.putSerializable("HashEdit", list);
        savedInstanceState.putSerializable("magic", carremagic);
        timeWhenStopped = mChronometer.getBase();
        savedInstanceState.putLong("pausechroo",timeWhenpause);// quand on met en pause le chrono
        savedInstanceState.putLong("chrono",timeWhenStopped);
        savedInstanceState.putBoolean("NewgameSate",NewGame.isEnabled());
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
           // this.somme = (HashMap<String, TextView>) savedInstanceState.getSerializable("HashView");
           // this.list = (HashMap<String, EditText>) savedInstanceState.getSerializable("HashEdit");
            this.carremagic = (int[][]) savedInstanceState.getSerializable("magic");
            this.timeWhenStopped = (long) savedInstanceState.getLong("chrono");
            this.mChronometer.setBase(timeWhenStopped);
            this.timeWhenpause = (long) savedInstanceState.getLong("pausechroo");
            Game(this.carremagic, this.levelgame);
            NewgameState = (boolean) savedInstanceState.getBoolean("NewgameSate");
            NewGame.setEnabled(NewgameState);
        }
    }
    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onPause(){
        super.onPause();
        timeWhenpause = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
    }
    @Override
    public void onResume() {
        super.onResume();
        mChronometer.setBase(SystemClock.elapsedRealtime() +  timeWhenpause);
        mChronometer.start();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    public void Game(int n[][] , int level) {
        somme.get("03").setText(Integer.toString(n[0][0] + n[0][1] + n[0][2]));
        somme.get("13").setText(Integer.toString(n[1][0] + n[1][1] + n[1][2]));
        somme.get("23").setText(Integer.toString(n[2][0] + n[2][1] + n[2][2]));
        somme.get("30").setText(Integer.toString(n[0][0] + n[1][0] + n[2][0]));
        somme.get("31").setText(Integer.toString(n[0][1] + n[1][1] + n[2][1]));
        somme.get("32").setText(Integer.toString(n[0][2] + n[1][2] + n[2][2]));
        for (int i = 0; i < (9-3*level) ; i++){
            help();
        }
        NewGame.setEnabled(false);
    }
    public void NewGame(View view){
        this.carremagic = solution();
        ClearAlldigits();
        Game(this.carremagic , this.levelgame);
        // je ne oublier pas de remmettre le chrono a zéro
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();



    }
    public void Helpme(View view){
        help();
    }
    public void submitme(View view){
            int a[][] = new int[4][4];
            /////// debut de la matrice des entier entre 1 et 9
            a[0][0] = Integer.parseInt(list.get("00").getText().toString());
            a[0][1] = Integer.parseInt(list.get("01").getText().toString());
            a[0][2] = Integer.parseInt(list.get("02").getText().toString());
            a[1][0] = Integer.parseInt(list.get("10").getText().toString());
            a[1][1] = Integer.parseInt(list.get("11").getText().toString());
            a[1][2] = Integer.parseInt(list.get("12").getText().toString());
            a[2][0] = Integer.parseInt(list.get("20").getText().toString());
            a[2][1] = Integer.parseInt(list.get("21").getText().toString());
            a[2][2] = Integer.parseInt(list.get("22").getText().toString());
            //////////////// // les sommes
            if (condition(a)) {
                Congratulation();
                elapsedMillis = (SystemClock.elapsedRealtime() - mChronometer.getBase()) % 1000;
                NewGame.setEnabled(true);
                mChronometer.stop();
                LastScore = elapsedMillis;
                SharedPreferences sharedPref = getSharedPreferences("LevelScores",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong("HSEasy",EasyLevelHighscores);
                editor.putLong("HSMeduim" , MeduimLevelHighscores);
                editor.putLong("HSHard", HardLevelHighscores);

                switch (levelgame){
                    case 1:
                        editor.putLong("LEasy", LastScore);
                        editor.commit();
                        break;
                    case 2:
                        editor.putLong("LMeduim", LastScore);
                        editor.commit();
                        break;
                    case 3:
                        editor.putLong("LHard", LastScore);
                        editor.commit();
                        break;
                        default:
                }

            } else {
                Dommage( );
            }
    }
    public void Congratulation( ) {
        Toast.makeText(this, "Congratulation", Toast.LENGTH_SHORT).show();
    }
    public void Dommage( ) {
        Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
    }
    public int[][] solution() {
        Integer[] digit = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        ArrayList<Integer> Alldigit = new ArrayList<>(digit.length);
        Alldigit.addAll(Arrays.asList(digit));
        int[][] matrice = new int[3][3];
        for (int i = 0; i < matrice.length; i++)
            for (int j = 0; j < matrice[i].length; j++) {
                int index = rand.nextInt(Alldigit.size());
                matrice[i][j] = Alldigit.get(index);
                Alldigit.remove(index);
            }
        return matrice;
    }
    public void help(){
        for (Map.Entry<String, EditText> E : this.list.entrySet()) {
            EditText Case = (EditText) E.getValue();
            int line = Character.getNumericValue(E.getKey().charAt(0));
            int col = Character.getNumericValue(E.getKey().charAt(1));
            if (Case.getText().toString().equals("")) {
                Case.setText(Integer.toString(this.carremagic[line][col]));
                Case.setKeyListener(null);
                Case.setTextColor(getResources().getColor(R.color.coloredit));
                helpEdit = Case;
                break;
            }
        }
    }
    public void ClearAlldigits(){
        for (Map.Entry<String, EditText> E : this.list.entrySet()) {
            EditText Case = (EditText) E.getValue();
            Case.setText("");
            }
    }
    public boolean condition(int a[][]) {
        return ((   a[0][0] + a[0][1] + a[0][2] == Integer.parseInt(somme.get("03").getText().toString()))
                && (a[1][0] + a[1][1] + a[1][2] == Integer.parseInt(somme.get("13").getText().toString()))
                && (a[2][0] + a[2][1] + a[2][2] == Integer.parseInt(somme.get("23").getText().toString()))
                && (a[0][0] + a[1][0] + a[2][0] == Integer.parseInt(somme.get("30").getText().toString()))
                && (a[0][1] + a[1][1] + a[2][1] == Integer.parseInt(somme.get("31").getText().toString()))
                && (a[0][2] + a[1][2] + a[2][2] == Integer.parseInt(somme.get("32").getText().toString())));
    }
    void checkFieldsForEmptyValues(){
        for (Map.Entry<String, EditText> E : this.list.entrySet()){
            EditText Case = (EditText) E.getValue();
            if (Case.getText().toString().equals("")){
                Submit.setEnabled(false);
            }else{
                Submit.setEnabled(true);
            }
        }
    }

    private TextWatcher MyTextwatcher =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

}