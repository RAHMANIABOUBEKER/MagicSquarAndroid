package com.example.booba.starttraining;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreGame extends AppCompatActivity {
    long score;
    String scoree;
    TextView tvHighScoreEasy, tvScoreEasy, tvHighScoreMeduim, tvScoreMeduim, tvHighScoreHard, tvScoreHard;
    long EasyLevelHighscores, MeduimLevelHighscores, HardLevelHighscores;
    long LEScore, LMScore, LHScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_game);
        //**************TextVieu of score****************/
        tvHighScoreEasy = findViewById(R.id.tvHighScoreEasy);
        tvScoreEasy = findViewById(R.id.tvScoreEasy);
        tvHighScoreMeduim = findViewById(R.id.tvHighScoreMeduim);
        tvScoreMeduim = findViewById(R.id.tvScoreMeduim);
        tvHighScoreHard = findViewById(R.id.tvHighScoreHard);
        tvScoreHard = findViewById(R.id.tvScoreHard);

        SharedPreferences sharedPref = getSharedPreferences("LevelScores",
                Context.MODE_PRIVATE);
        EasyLevelHighscores = sharedPref.getLong("HSEasy",0);
        LEScore = sharedPref.getLong("LEasy",0);
        if (EasyLevelHighscores < LEScore && EasyLevelHighscores != 0 ) {
            tvHighScoreEasy.setText(Long.toString(EasyLevelHighscores));
            tvScoreEasy.setText(Long.toString(LEScore));
        } else {
            EasyLevelHighscores = LEScore;
            tvHighScoreEasy.setText(Long.toString(EasyLevelHighscores));
            tvScoreEasy.setText(Long.toString(LEScore));
            sharedPref.edit().putLong("HSEasy", EasyLevelHighscores).apply();
        }
        MeduimLevelHighscores = sharedPref.getLong("HSMeduim",0);
        LMScore = sharedPref.getLong("LMeduim",0);
        if (MeduimLevelHighscores < LMScore && MeduimLevelHighscores != 0 ) {
            tvHighScoreMeduim.setText(Long.toString(MeduimLevelHighscores));
            tvScoreMeduim.setText(Long.toString(LMScore));
        } else {
            MeduimLevelHighscores = LMScore;
            tvHighScoreMeduim.setText(Long.toString(MeduimLevelHighscores));
            tvScoreMeduim.setText(Long.toString(LMScore));
            sharedPref.edit().putLong("HSMeduim", MeduimLevelHighscores).apply();
        }
        HardLevelHighscores = sharedPref.getLong("HSHard",0);
        LHScore = sharedPref.getLong("LHard",0);
        if ( HardLevelHighscores < LHScore && HardLevelHighscores != 0 ) {
            tvHighScoreHard.setText(Long.toString( HardLevelHighscores));
            tvScoreHard.setText(Long.toString(LHScore));
        } else {
            HardLevelHighscores= LHScore;
            tvHighScoreHard.setText(Long.toString( HardLevelHighscores));
            tvScoreHard.setText(Long.toString(LHScore));
            sharedPref.edit().putLong("HSHard", HardLevelHighscores).apply();
        }
    }
}

