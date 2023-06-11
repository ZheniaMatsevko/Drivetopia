package com.wheelie.co.levelTemplates.questionTemplates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wheelie.co.Graphics.GraphicConstants;

import java.util.Collections;
import java.util.LinkedList;

public class NormalFlashCardQuestion {
    private LinkedList<ImageButton> objects;
    private ImageButton correctAnswer;
    private String question;

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



        Texture myTexture1 = new Texture(Gdx.files.internal("zebra.png"));
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
