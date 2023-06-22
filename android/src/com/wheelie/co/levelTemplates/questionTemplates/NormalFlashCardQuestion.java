package com.wheelie.co.levelTemplates.questionTemplates;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.LinkedList;
import java.util.Random;

import DBWorkH.DBConstants;

public class NormalFlashCardQuestion {
    private LinkedList<ImageButton> objects;
    private ImageButton correctAnswer;
    private String question;
    private int level;

    public int getLevel() {
        return level;

    }

    public void setLevel(int level) {
        this.level = level;
    }

    public NormalFlashCardQuestion(int level){
        initialize(level);
    }

    private void initialize(int level) {
        //read from Database
        question="Пішохідний перехід";
        objects = new LinkedList<>();
        for(int i=1;i<4;i++){
            Texture myTexture = new Texture(Gdx.files.internal("sign"+i+".png"));
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture));

            ImageButton obj1 = new ImageButton(style);

            obj1.setSize(500,500);
            objects.add(obj1);
        }



        Texture myTexture1 = new Texture(Gdx.files.internal("sign15.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(500,500);
        correctAnswer=obj;
        objects.add(correctAnswer);

    }

    public NormalFlashCardQuestion(LinkedList<ImageButton> objects, ImageButton correctAnswer, String question){
        this.objects = objects;
        this.question=question;
        this.correctAnswer=correctAnswer;
    }

    public NormalFlashCardQuestion(LinkedList<String> objects, String correctAnswer, String question, int level){
        this.question=question;
        this.objects = new LinkedList<>();
        this.level=level;
        int size=500;
        if(level==6)
            size=1000;
        for(int i=0;i<3;i++){
            Texture myTexture = new Texture(Gdx.files.internal(objects.get(i)));
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture));

            ImageButton obj1 = new ImageButton(style);

            obj1.setSize(size,size);
            this.objects.add(obj1);
        }

        Texture myTexture1 = new Texture(Gdx.files.internal(correctAnswer));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(size,size);
        this.correctAnswer=obj;
        this.objects.add(this.correctAnswer);

    }

    public static LinkedList<NormalFlashCardQuestion> extractNormalFlashCardQuestionFromDB(SQLiteDatabase db, int levelNumb, String type) {
        String[] columns = {"text", "answer"};

        String selection = "levelNumb = ? AND type = ?";
        String[] selectionArgs = {String.valueOf(levelNumb), type};
        LinkedList<String> allImages = getAllImagesFromBD(db, type);

        // Query the table and get the cursor
        Cursor cursor = db.query(DBConstants.FLASHCARD_QUESTION_TABLE, columns, selection, selectionArgs, null, null, null);

        LinkedList<NormalFlashCardQuestion> list = new LinkedList<>();

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                LinkedList<String> wrongAnswers = getWrongAnswers(allImages, answer);

                NormalFlashCardQuestion s = new NormalFlashCardQuestion(wrongAnswers,answer,text, levelNumb);
                list.add(s);

            } while (cursor.moveToNext());
            // Close the cursor
            cursor.close();

        }

        return list;
    }

    private static LinkedList<String> getWrongAnswers(LinkedList<String> allImages, String answer) {

        LinkedList<String> rez = new LinkedList<>();
        while(rez.size()<3){
            Random random = new Random();
            String temp = allImages.get(random.nextInt(allImages.size()));
            if(!temp.equals(answer) && !rez.contains(temp))
                rez.add(temp);
        }
        return rez;
    }

    private static LinkedList<String> getAllImagesFromBD(SQLiteDatabase db, String type) {
        LinkedList<String> images = new LinkedList<>();
        String[] columns = {"image"};

        String selection = "type = ?";
        String[] selectionArgs = {type};

        // Query the table and get the cursor
        Cursor cursor = db.query(DBConstants.FLASHCARD_IMAGES_TABLE, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            do {
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                images.add(image);

            } while (cursor.moveToNext());
            // Close the cursor
            cursor.close();

        }

        return images;
    }

    public ImageButton getCorrectAnswer() {
        return correctAnswer;
    }

    public LinkedList<ImageButton> getObjects() {

        return objects;
    }


    public void setObjects(LinkedList<ImageButton> objects) {
        this.objects = objects;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(ImageButton correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}
