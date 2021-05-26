package com.example.mathquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class QuizQuestionScreen extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewCorrect;
    private TextView textViewIncorrect;
    private TextView textViewQuestionCount;
    private TextView textViewTimer;
    private RadioGroup radioBtnGrp;
    private RadioButton radioBtn1;
    private RadioButton radioBtn2;
    private RadioButton radioBtn3;
    private RadioButton radioBtn4;
    private Button submitBtn;

    private ColorStateList textColorDefaultRadioBtn;

    private List<QuestionDB> questionDBList;
    private int questionCounter;
    private int questionCounterTotal;
    private QuestionDB currentQuestion;

    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question_screen);

        textViewQuestion = findViewById(R.id.textViewQuestion1);
        textViewCorrect = findViewById(R.id.textViewCorrectCounter);
        textViewIncorrect = findViewById(R.id.textViewIncorrectCounter);
        textViewQuestionCount = findViewById(R.id.textViewCurrentQuestion);
        textViewTimer = findViewById(R.id.textViewTimer);
        radioBtnGrp = findViewById(R.id.radioGroup);
        radioBtn1 = findViewById(R.id.radioButton1Q1);
        radioBtn2 = findViewById(R.id.radioButton2Q1);
        radioBtn3 = findViewById(R.id.radioButton3Q1);
        radioBtn4 = findViewById(R.id.radioButton4Q1);
        submitBtn = findViewById(R.id.submitBtn);

        textColorDefaultRadioBtn = radioBtn1.getTextColors();

        DbHelper dbHelper = new DbHelper(this);
        questionDBList = dbHelper.getAllQuestions();
        questionCounterTotal = questionDBList.size();
        Collections.shuffle(questionDBList);

        showNextQuestion();
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
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCounterTotal);
            answered = false;
            submitBtn.setText("Submit");
        }
        else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        finish();
    }
}