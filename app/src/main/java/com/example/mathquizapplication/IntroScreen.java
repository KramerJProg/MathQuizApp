package com.example.mathquizapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class IntroScreen extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED = "shared";
    public static final String BESTSCORE = "bestScore";

    private TextView textViewBestScore;
    private Spinner spinnerDifficulty;
    private int bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        textViewBestScore = findViewById(R.id.textViewBestScore);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = QuestionDB.getAllDifficultyLevels();
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);

        loadBestScore();

        Button startQuizBtn = findViewById(R.id.startQuizBtn);
        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizStart();
            }
        });
    }

    private void quizStart() {
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(IntroScreen.this, QuizQuestionScreen.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizQuestionScreen.EXTRA_SCORE, 0);
                if (score > bestScore) {
                    updateBestScore(score);
                }
            }
        }
    }

    private void loadBestScore() {
        SharedPreferences shared = getSharedPreferences(SHARED, MODE_PRIVATE);
        bestScore = shared.getInt(BESTSCORE, 0);
        textViewBestScore.setText("Best Score: " + bestScore);
    }

    private void updateBestScore(int newBestScore) {
        bestScore = newBestScore;
        textViewBestScore.setText("Best Score: " + bestScore);

        SharedPreferences shared = getSharedPreferences(SHARED, MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putInt(BESTSCORE,bestScore);
        edit.apply();
    }
}