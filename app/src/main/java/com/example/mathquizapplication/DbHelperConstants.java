package com.example.mathquizapplication;

import android.provider.BaseColumns;

public final class DbHelperConstants {

    private DbHelperConstants() {}

    /**
     * Used for constants primarily for DbHelper.
     */
    public static class QuestionsStorage implements BaseColumns {

        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_NAME = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NMBR = "answer_nmbr";
    }
}
