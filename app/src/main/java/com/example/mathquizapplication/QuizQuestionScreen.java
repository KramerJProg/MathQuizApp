package com.example.mathquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizQuestionScreen extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long TIMER = 30000;  // Implements 30 seconds on the timer.

    private TextView textViewQuestion;
    private TextView textViewCorrect;
    private TextView textViewIncorrect;
    private TextView textViewQuestionCount;
    private TextView textViewDifficulty;
    private TextView textViewTimer;
    private RadioGroup radioBtnGrp;
    private RadioButton radioBtn1;
    private RadioButton radioBtn2;
    private RadioButton radioBtn3;
    private RadioButton radioBtn4;
    private Button submitBtn;

    private ColorStateList textColorDefaultRadioBtn;
    private ColorStateList textColorDefaultTimer;

    private CountDownTimer timer;
    private long timeRemaining;

    private List<QuestionDB> questionDBList;
    private int questionCounter;
    private int questionCounterTotal;
    private QuestionDB currentQuestion;

    private int correctScore;
    private int incorrectScore;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question_screen);

        textViewQuestion = findViewById(R.id.textViewQuestion1);
        textViewCorrect = findViewById(R.id.textViewCorrectCounter);
        textViewIncorrect = findViewById(R.id.textViewIncorrectCounter);
        textViewQuestionCount = findViewById(R.id.textViewCurrentQuestion);
        textViewDifficulty = findViewById(R.id.textViewDifficulty);
        textViewTimer = findViewById(R.id.textViewTimer);
        radioBtnGrp = findViewById(R.id.radioGroup);
        radioBtn1 = findViewById(R.id.radioButton1Q1);
        radioBtn2 = findViewById(R.id.radioButton2Q1);
        radioBtn3 = findViewById(R.id.radioButton3Q1);
        radioBtn4 = findViewById(R.id.radioButton4Q1);
        submitBtn = findViewById(R.id.submitBtn);

        textColorDefaultRadioBtn = radioBtn1.getTextColors();
        textColorDefaultTimer = textViewTimer.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(IntroScreen.EXTRA_DIFFICULTY);

        textViewDifficulty.setText("Level: " + difficulty);

        if (savedInstanceState == null) {
            DbHelper dbHelper = new DbHelper(this);
            questionDBList = dbHelper.getQuestions(difficulty);
            questionCounterTotal = questionDBList.size();
            Collections.shuffle(questionDBList);

            showNextQuestion();
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (radioBtn1.isChecked() || radioBtn2.isChecked() || radioBtn3.isChecked() || radioBtn4.isChecked()) {
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(QuizQuestionScreen.this, "Please select an option.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        radioBtn1.setTextColor(textColorDefaultRadioBtn);
        radioBtn2.setTextColor(textColorDefaultRadioBtn);
        radioBtn3.setTextColor(textColorDefaultRadioBtn);
        radioBtn4.setTextColor(textColorDefaultRadioBtn);
        radioBtnGrp.clearCheck();

        if (questionCounter < questionCounterTotal) {
            currentQuestion = questionDBList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            radioBtn1.setText(currentQuestion.getOption1());
            radioBtn2.setText(currentQuestion.getOption2());
            radioBtn3.setText(currentQuestion.getOption3());
            radioBtn4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + " of " + questionCounterTotal);
            answered = false;
            submitBtn.setText("Submit");

            timeRemaining = TIMER;
            startTimer();
        }
        else {
            finishQuiz();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timeRemaining = 0;
                updateTimerText();
                checkAnswer();
            }
        }.start();
    }

    private void updateTimerText() {
        int minutes = (int) (timeRemaining / 1000) / 60;
        int seconds = (int) (timeRemaining / 1000) % 60;

        String timeFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeFormat);

        if (timeRemaining < 10000) {  //  Timer turns red once below 10 seconds.
            textViewTimer.setTextColor(Color.RED);
        }
        else {
            textViewTimer.setTextColor(textColorDefaultTimer);
        }
    }

    private void checkAnswer() {
        answered = true;

        timer.cancel();

        RadioButton radioBtnPicked = findViewById(radioBtnGrp.getCheckedRadioButtonId());
        int answerNumber = radioBtnGrp.indexOfChild(radioBtnPicked) + 1;

        if (answerNumber == currentQuestion.getAnswerNmbr()) {
            correctScore++;
            textViewCorrect.setText("Correct: " + correctScore);
        }
        else if (answerNumber != currentQuestion.getAnswerNmbr()) {
            incorrectScore++;
            textViewIncorrect.setText("Incorrect: " + incorrectScore);
        }

        showAnswer();
    }

    private void showAnswer() {
        radioBtn1.setTextColor(Color.RED);
        radioBtn2.setTextColor(Color.RED);
        radioBtn3.setTextColor(Color.RED);
        radioBtn4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNmbr()) {
            case 1:
                radioBtn1.setTextColor(Color.GREEN);
                break;
            case 2:
                radioBtn2.setTextColor(Color.GREEN);
                break;
            case 3:
                radioBtn3.setTextColor(Color.GREEN);
                break;
            case 4:
                radioBtn4.setTextColor(Color.GREEN);
                break;
        }

        if (questionCounter < questionCounterTotal) {
            submitBtn.setText("Next Question");
        }
        else {
            submitBtn.setText("Finish Quiz");
        }
    }

    private void finishQuiz() {
        if (questionCounter == questionCounterTotal) {
            Intent intent = new Intent(getApplicationContext(), IntroScreen.class);
            intent.putExtra("RIGHT_ANSWER_COUNT", correctScore);
            setResult(RESULT_OK, intent);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}