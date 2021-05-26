package com.example.mathquizapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mathquizapplication.DbHelperConstants.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MathQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsStorage.TABLE_NAME + " ( " +
                QuestionsStorage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsStorage.COLUMN_NAME + " TEXT, " +
                QuestionsStorage.COLUMN_OPTION1 + " TEXT, " +
                QuestionsStorage.COLUMN_OPTION2 + " TEXT, " +
                QuestionsStorage.COLUMN_OPTION3 + " TEXT, " +
                QuestionsStorage.COLUMN_OPTION4 + " TEXT, " +
                QuestionsStorage.COLUMN_ANSWER_NMBR + " INTEGER" + ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        addQuestionsToTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsStorage.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestionsToTable() {
        QuestionDB question1 = new QuestionDB("A is correct", "A", "B", "C", "D", 1);
        addQuestion(question1);
        QuestionDB question2 = new QuestionDB("B is correct", "A", "B", "C", "D", 2);
        addQuestion(question2);
        QuestionDB question3 = new QuestionDB("C is correct", "A", "B", "C", "D", 3);
        addQuestion(question3);
        QuestionDB question4 = new QuestionDB("D is correct", "A", "B", "C", "D", 4);
        addQuestion(question4);
        QuestionDB question5 = new QuestionDB("A is correct", "A", "B", "C", "D", 1);
        addQuestion(question5);
        QuestionDB question6 = new QuestionDB("D is correct", "A", "B", "C", "D", 4);
        addQuestion(question6);
    }

    private void addQuestion(QuestionDB question) {
        ContentValues content = new ContentValues();
        content.put(QuestionsStorage.COLUMN_NAME, question.getQuestion());
        content.put(QuestionsStorage.COLUMN_OPTION1, question.getOption1());
        content.put(QuestionsStorage.COLUMN_OPTION2, question.getOption2());
        content.put(QuestionsStorage.COLUMN_OPTION3, question.getOption3());
        content.put(QuestionsStorage.COLUMN_OPTION4, question.getOption4());
        content.put(QuestionsStorage.COLUMN_ANSWER_NMBR, question.getAnswerNmbr());
        db.insert(QuestionsStorage.TABLE_NAME, null, content);

    }

    public List<QuestionDB> getAllQuestions() {
        List<QuestionDB> questionDbList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsStorage.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                QuestionDB questionDB = new QuestionDB();
                questionDB.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_NAME)));
                questionDB.setOption1(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION1)));
                questionDB.setOption2(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION2)));
                questionDB.setOption3(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION3)));
                questionDB.setOption4(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION4)));
                questionDB.setAnswerNmbr(cursor.getInt(cursor.getColumnIndex(QuestionsStorage.COLUMN_ANSWER_NMBR)));
                questionDbList.add(questionDB);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return questionDbList;
    }
}
