package com.wheelie.co.levelTemplates.questionTemplates;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class SimpleTextChoiceQuestion {


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String text;
    private String correctAnswer;
    private String picture;

    private int level;
    public LinkedList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    private LinkedList<String> wrongAnswers;



    //констр. за замовчуванням, підходитиме для 1 завдання 1 рівню
    public SimpleTextChoiceQuestion() {

    }




    public SimpleTextChoiceQuestion(int i) {

    }

    public LinkedList<String> generateWrongAnswers(LinkedList<String> answers) {
        LinkedList<String> randomElements = new LinkedList<>();
        Random random = new Random();
        Set<Integer> selectedIndices = new HashSet<>();

        int listSize = answers.size();
        if (listSize <= 3) {
            randomElements.addAll(answers);
        } else {
            while (selectedIndices.size() < 3) {
                int randomIndex = random.nextInt(listSize);
                if (!selectedIndices.contains(randomIndex)) {
                    randomElements.add(answers.get(randomIndex));
                    selectedIndices.add(randomIndex);
                }
            }
        }

        return randomElements;
    }

    public void setWrongAnswers(LinkedList<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }




    public SimpleTextChoiceQuestion (String text,String correctAnswer,String picture,LinkedList<String> wrongAnswers){
this.text = text;
this.correctAnswer=correctAnswer;
this.picture=picture;
this.wrongAnswers=wrongAnswers;
    }



    public static LinkedList<SimpleTextChoiceQuestion> extractSimpleTextChoiceQuestionsFromDB(SQLiteDatabase db, int levelNumb) {
        String[] columns = {"id","text", "answer", "picture"};

        // Define the selection criteria
        String selection = "levelNumb = ?";
        String[] selectionArgs = {String.valueOf(levelNumb)};

        // Query the table and get the cursor
        Cursor cursor = db.query("simpleChoiceQuestion", columns, selection, selectionArgs, null, null, null);

        LinkedList<SimpleTextChoiceQuestion> list = new LinkedList<>();

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            while (cursor.moveToNext()) {
                int questionId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                String picture = cursor.getString(cursor.getColumnIndexOrThrow("picture"));

                LinkedList<String> wrongAnswers = SimpleTextChoiceQuestion.getWrongAnswersFromDB(db,questionId);

                SimpleTextChoiceQuestion s = new SimpleTextChoiceQuestion(text, answer, picture,wrongAnswers);
                list.add(s);

            }
            // Close the cursor
            cursor.close();

        }
        return list;
    }


    public static LinkedList<String> getWrongAnswersFromDB(SQLiteDatabase db, int questionId) {
        String[] columns = {"wrongAnswer"};

        // Define the selection criteria
        String selection = "questionId = ?";
        String[] selectionArgs = {String.valueOf(questionId)};

        // Query the table and get the cursor
        Cursor cursor = db.query("wrongChoices", columns, selection, selectionArgs, null, null, null);

        LinkedList<String> list = new LinkedList<>();

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            while (cursor.moveToNext()) {
                String wrongAnswer = cursor.getString(cursor.getColumnIndexOrThrow("wrongAnswer"));

                list.add(wrongAnswer);
            }
            cursor.close();



        }

        return list;

    }



    public int getLevel()
    {
        return this.level;
    }

    public void setLevel(int level) {
        this.level=level;

    }
}
