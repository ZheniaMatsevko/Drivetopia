package com.wheelie.co.levelTemplates.questionTemplates;

import static org.sqlite.SQLiteJDBCLoader.initialize;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Graphics.ProfileScreen;
import com.wheelie.co.Tools.Coordinates;

import java.util.HashMap;
import java.util.LinkedList;

public class HardPictureQuestion {
    private LinkedList<ImageButton> objects;
    private ImageButton correctAnswer;
    private String question;
    private String backgroundImagePath;

    public HardPictureQuestion(int level){
        initialize(level);
    }

    private void initialize(int level) {
        //read from Database
        question="Знайдіть червону машинку!";
        backgroundImagePath = "hard1b.png";
        objects = new LinkedList<>();
        Texture myTexture = new Texture(Gdx.files.internal("blue1.png"));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture));

        ImageButton obj1 = new ImageButton(style);

        obj1.setSize(320,180);
        obj1.setPosition(220, GraphicConstants.rowHeight*4.4F);
        objects.add(obj1);

        Texture myTexture1 = new Texture(Gdx.files.internal("car1.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(190,340);
        obj.setPosition(683, GraphicConstants.rowHeight*3.7F);
        correctAnswer=obj;

    }

    public HardPictureQuestion(LinkedList<ImageButton> objects, ImageButton correctAnswer, String question, String backgroundImagePath){
        this.objects = objects;
        this.question=question;
        this.backgroundImagePath=backgroundImagePath;
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

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public void setCorrectAnswer(ImageButton correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}

