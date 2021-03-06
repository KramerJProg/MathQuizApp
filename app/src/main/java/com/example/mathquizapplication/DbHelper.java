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
                QuestionsStorage.COLUMN_ANSWER_NMBR + " INTEGER, " +
                QuestionsStorage.COLUMN_DIFFICULTY + " TEXT" +
                ")";

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
    // BEGINNER QUESTIONS
    private void addQuestionsToTable() {
        QuestionDB question1 = new QuestionDB("5 * 5 = ?", "25", "10", "15", "50", 1, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question1);
        QuestionDB question2 = new QuestionDB("50 - 8 = ?", "58", "42", "62", "12", 2, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question2);
        QuestionDB question3 = new QuestionDB("42 - 21", "20", "18", "21", "12", 3, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question3);
        QuestionDB question4 = new QuestionDB("30 - 90 = ?", "-120", "-30", "30", "-60", 4, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question4);
        QuestionDB question5 = new QuestionDB("8 * 7 = ?", "56", "15", "30", "1", 1, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question5);
        QuestionDB question6 = new QuestionDB("40 * 2 = ?", "40", "20", "60", "80", 4, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question6);
        QuestionDB question7 = new QuestionDB("96 / 4 = ?", "42", "22", "24", "44", 3, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question7);
        QuestionDB question8 = new QuestionDB("30 - 15 = ?", "25", "15", "45", "-15", 2, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question8);
        QuestionDB question9 = new QuestionDB("100 / 5 = ?", "5", "25", "50", "20", 4, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question9);
        QuestionDB question10 = new QuestionDB("200 / 4 = ?", "50", "100", "25", "None of these", 1, QuestionDB.DIFFICULTY_EASY);
        addQuestion(question10);

        // INTERMEDIATE QUESTIONS
        QuestionDB question11 = new QuestionDB("9 - 15 + 4 + 12 = ?", "10", "22", "31", "24", 1, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question11);
        QuestionDB question12 = new QuestionDB("2 + 54 - 74 = ?", "-126", "-18", "38", "130", 2, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question12);
        QuestionDB question13 = new QuestionDB("22 - 22 - 22 + 22 = ?", "22", "44", "0", "-44", 3, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question13);
        QuestionDB question14 = new QuestionDB("10 + 1 - 42 - 6 = ?", "21", "28", "-16", "-37", 4, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question14);
        QuestionDB question15 = new QuestionDB("99 + 21 - 1.5 = ?", "118.5", "94.5", "121.5", "82.5", 1, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question15);
        QuestionDB question16 = new QuestionDB("86.8 - 144 + 63.6 = ?", "87.2", "-22.6", "8.8", "6.4", 4, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question16);
        QuestionDB question17 = new QuestionDB("3.14 + 6.87 - 2 = ?", "2.67", "1.1", "8.01", "14.75", 3, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question17);
        QuestionDB question18 = new QuestionDB("67.5 + 67.5 = ?", "143", "135", "125.5", "143.5", 2, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question18);
        QuestionDB question19 = new QuestionDB("9000 - 8000 / 2 = ?", "500", "3500", "6700", "5000", 4, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question19);
        QuestionDB question20 = new QuestionDB("16 * 4 + 3 / 2 = ?", "65.5", "41.1", "82.7", "105.1", 1, QuestionDB.DIFFICULTY_MEDIUM);
        addQuestion(question20);

        // EXPERT QUESTIONS
        QuestionDB question21 = new QuestionDB("6(3 / 5) + 80 - 4 = ?", "79.6", "82.2", "42.8", "0", 1, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question21);
        QuestionDB question22 = new QuestionDB("9(8 * 1) - 6 + 14 * 2 = ?", "90", "94", "84", "78", 2, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question22);
        QuestionDB question23 = new QuestionDB("4 - 5(8 - 14) / 3 = ?", "-34", "-84", "14", "84", 3, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question23);
        QuestionDB question24 = new QuestionDB("2(-5 * 2) + 15 - 6 = ?", "30", "11", "0", "-11", 4, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question24);
        QuestionDB question25 = new QuestionDB("40 / 8(6 * 3) = ?", "90", "40", "70", "20", 1, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question25);
        QuestionDB question26 = new QuestionDB("7(8 * 4) - 36 = ?", "48", "188", "368", "402", 2, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question26);
        QuestionDB question27 = new QuestionDB("4 * 5 - 5 = ?", "100", "25", "4", "15", 4, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question27);
        QuestionDB question28 = new QuestionDB("2(6 * 5) = ?", "30", "60", "25", "45", 2, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question28);
        QuestionDB question29 = new QuestionDB("64 * 9 - 576 = ?", "-182", "274", "-27", "0", 4, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question29);
        QuestionDB question30 = new QuestionDB("91(8 * 7) / 40", "127.4", "81.9", "95.55", "109.2", 1, QuestionDB.DIFFICULTY_HARD);
        addQuestion(question30);
    }

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
        content.put(QuestionsStorage.COLUMN_DIFFICULTY, question.getDifficulty());
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
                questionDB.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_DIFFICULTY)));
                questionDbList.add(questionDB);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return questionDbList;
    }

    public List<QuestionDB> getQuestions(String difficulty) {
        List<QuestionDB> questionDbList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsStorage.TABLE_NAME +
                " WHERE " + QuestionsStorage.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                QuestionDB questionDB = new QuestionDB();
                questionDB.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_NAME)));
                questionDB.setOption1(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION1)));
                questionDB.setOption2(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION2)));
                questionDB.setOption3(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION3)));
                questionDB.setOption4(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_OPTION4)));
                questionDB.setAnswerNmbr(cursor.getInt(cursor.getColumnIndex(QuestionsStorage.COLUMN_ANSWER_NMBR)));
                questionDB.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionsStorage.COLUMN_DIFFICULTY)));
                questionDbList.add(questionDB);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return questionDbList;
    }
}
