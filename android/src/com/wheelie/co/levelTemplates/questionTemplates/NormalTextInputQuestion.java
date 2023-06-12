package com.wheelie.co.levelTemplates.questionTemplates;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;

import DBWorkH.DBConstants;

public class NormalTextInputQuestion {
    private String question;
    private String answer;
    private int level;

    public NormalTextInputQuestion(String question, String answer){
        this.question=question;
        this.answer=answer;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static LinkedList<NormalTextInputQuestion> extractNormalTextInputQuestionsFromDB(SQLiteDatabase db, int levelNumb) {
        String[] columns = {"id","text", "answer"};

        // Define the selection criteria
        String selection = "levelNumb = ?";
        String[] selectionArgs = {String.valueOf(levelNumb)};

        // Query the table and get the cursor
        Cursor cursor = db.query(DBConstants.TEXT_INPUT_QUESTION_TABLE, columns, selection, selectionArgs, null, null, null);

        LinkedList<NormalTextInputQuestion> list = new LinkedList<>();

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                NormalTextInputQuestion s = new NormalTextInputQuestion(text, answer);
                list.add(s);

            } while (cursor.moveToNext());
            // Close the cursor
            cursor.close();

        }
        return list;
    }

    public int getLevel() {
        return level;
    }

    public NormalTextInputQuestion(){
        question="Забезпечувати, що їх дії / бездіяльність не створять загрозу життю та здоров’ю громадян та інших водіїв";
        answer="Привіт";
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
