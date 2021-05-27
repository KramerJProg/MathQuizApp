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

/**
 * The Database class that utilizes SQLite
 * and creates the database.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MathQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database using SQLite
     * @param db
     */
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

    // Still need to implement onUpgrade to be able to update database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsStorage.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Demo database test code
     */
    private void addQuestionsToTable() {
        QuestionDB question1 = new QuestionDB("The answer is A", "A", "B", "C", "D", 1);
        addQuestion(question1);
        QuestionDB question2 = new QuestionDB("The answer is B", "A", "B", "C", "D", 2);
        addQuestion(question2);
        QuestionDB question3 = new QuestionDB("The answer is C", "A", "B", "C", "D", 3);
        addQuestion(question3);
        QuestionDB question4 = new QuestionDB("The answer is D", "A", "B", "C", "D", 4);
        addQuestion(question4);
        QuestionDB question5 = new QuestionDB("The answer is A", "A", "B", "C", "D", 1);
        addQuestion(question5);
        QuestionDB question6 = new QuestionDB("The answer is D", "A", "B", "C", "D", 4);
        addQuestion(question6);
    }

    /*private void addQuestionsToTable() {
        QuestionDB question1 = new QuestionDB("5 * 5 = ?", "25", "10", "15", "50", 1);
        addQuestion(question1);
        QuestionDB question2 = new QuestionDB("50 - 8 = ?", "58", "42", "62", "12", 2);
        addQuestion(question2);
        QuestionDB question3 = new QuestionDB("2(6 * 5) = ?", "30", "120", "60", "45", 3);
        addQuestion(question3);
        QuestionDB question4 = new QuestionDB("4 * 5 - 5 = ?", "100", "25", "4", "15", 4);
        addQuestion(question4);
        QuestionDB question5 = new QuestionDB("8 * 7 = ?", "56", "15", "30", "1", 1);
        addQuestion(question5);
        QuestionDB question6 = new QuestionDB("40 * 2 = ?", "40", "20", "60", "80", 4);
        addQuestion(question6);
        QuestionDB question7 = new QuestionDB("9(8 * 1) - 6 + 14 * 2 = ?", "42", "58", "94", "86", 3);
        addQuestion(question7);
        QuestionDB question8 = new QuestionDB("7(8 * 4) - 36 = ?", "48", "188", "368", "402", 2);
        addQuestion(question8);
        QuestionDB question9 = new QuestionDB("4 - 5(8 - 14) = ?", "-84", "-34", "84", "34", 4);
        addQuestion(question9);
        QuestionDB question10 = new QuestionDB("64 * 9 - 576 = ?", "0", "-27", "274", "None of these", 1);
        addQuestion(question10);
    }*/

    /**
     * Adds new questions to the database.
     * @param question
     */
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

    /**
     * Gets the questions from the database using the SELECT * -> FROM QuestionStorage.
     * @return
     */
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
