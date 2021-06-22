package com.example.mathquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class IntroScreen extends AppCompatActivity {

    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    private Spinner spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        TextView resultLabel = findViewById(R.id.resultLabel);

        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);

        SharedPreferences shared = getSharedPreferences("QUIZ_DATA", Context.MODE_PRIVATE);

        resultLabel.setText("Your Score: " + score + " / 10");

        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("RIGHT_ANSWER_COUNT", score);
        editor.apply();

        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = QuestionDB.getAllDifficultyLevels();
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);

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
}