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

    public HardPictureQuestion(int level, boolean second){
        switch (level){
            case 4:
                initializeLevel4();
                break;
            case 7:
                initializeLevel7();
                break;
            case 11:
                if(second)
                    initializeLevel11_2();
                else
                    initializeLevel11_1();
                break;
            case 12:
                initializeLevel12();
                break;
            case 15:
                initializeLevel15();
                break;
        }

    }

    private void initializeLevel4() {
        //read from Database
        question="Хто порушує правила дорожнього руху?";
        backgroundImagePath = "level4_PQ.png";
        objects = new LinkedList<>();
        Texture myTexture = new Texture(Gdx.files.internal("blue1.png"));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture));

        ImageButton obj1 = new ImageButton(style);

        obj1.setSize(320,180);
        obj1.setPosition(GraphicConstants.colWidth*5, GraphicConstants.rowHeight*3.3F);
        objects.add(obj1);

        Texture myTexture2 = new Texture(Gdx.files.internal("motorcycle.png"));
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture2));

        ImageButton obj2 = new ImageButton(style2);

        obj2.setSize(120,270);
        obj2.setPosition(GraphicConstants.colWidth*3.1f, GraphicConstants.rowHeight*5.5F);
        objects.add(obj2);

        Texture myTexture1 = new Texture(Gdx.files.internal("CarToRightWithLeftLights.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(350,200);
        obj.setPosition(GraphicConstants.colWidth*0.4f, GraphicConstants.rowHeight*3.3F);
        correctAnswer=obj;

    }

    private void initializeLevel7() {
        //read from Database
        question="Хто порушує правила дорожнього руху?";
        backgroundImagePath = "level7_PQ.jpg";
        objects = new LinkedList<>();
        Texture myTexture = new Texture(Gdx.files.internal("blue1.png"));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture));

        ImageButton obj1 = new ImageButton(style);

        obj1.setSize(230,150);
        obj1.setPosition(GraphicConstants.colWidth*5.6f, GraphicConstants.rowHeight*3.15F);
        objects.add(obj1);

        Texture myTexture4 = new Texture(Gdx.files.internal("carWithTrailer.png"));
        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture4));

        ImageButton obj4 = new ImageButton(style4);

        obj4.setSize(410,200);
        obj4.setPosition(GraphicConstants.colWidth*4.7f, GraphicConstants.rowHeight*3.54F);
        objects.add(obj4);

        Texture myTexture2 = new Texture(Gdx.files.internal("carPQ.png"));
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture2));

        ImageButton obj2 = new ImageButton(style2);

        obj2.setSize(250,200);
        obj2.setPosition(GraphicConstants.colWidth*2.8f, GraphicConstants.rowHeight*5.5F);
        objects.add(obj2);

        Texture myTexture1 = new Texture(Gdx.files.internal("LKW.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(520,220);
        obj.setPosition(GraphicConstants.colWidth*0.1f, GraphicConstants.rowHeight*3.95F);
        correctAnswer=obj;

    }

    private void initializeLevel11_1() {
        //read from Database
        question="Хто порушує правила дорожнього руху?";
        backgroundImagePath = "level11_PQ.jpg";
        objects = new LinkedList<>();

        Texture myTexture4 = new Texture(Gdx.files.internal("carWithTrailer.png"));
        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture4));

        ImageButton obj4 = new ImageButton(style4);

        obj4.setSize(500,240);
        obj4.setPosition(GraphicConstants.colWidth*3.5f, GraphicConstants.rowHeight*2.9F);
        objects.add(obj4);

        Texture myTexture2 = new Texture(Gdx.files.internal("carPQ.png"));
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture2));

        ImageButton obj2 = new ImageButton(style2);

        obj2.setSize(260,220);
        obj2.setPosition(GraphicConstants.colWidth*3.6f, GraphicConstants.rowHeight*4.15F);
        objects.add(obj2);

        Texture myTexture1 = new Texture(Gdx.files.internal("truck11.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(400,180);
        obj.setPosition(GraphicConstants.colWidth*0.2f, GraphicConstants.rowHeight*4.25F);
        correctAnswer=obj;

    }

    //TODO add right coordinates
    private void initializeLevel11_2() {
        //read from Database
        question="Яка машина зупинилась правильно?";
        backgroundImagePath = "level11_PQ2.png";
        objects = new LinkedList<>();

        Texture myTexture2 = new Texture(Gdx.files.internal("carPQ.png"));
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture2));

        ImageButton obj2 = new ImageButton(style2);

        obj2.setSize(280,240);
        obj2.setPosition(GraphicConstants.colWidth*3.6f, GraphicConstants.rowHeight*4.5F);
        objects.add(obj2);

        Texture myTexture4 = new Texture(Gdx.files.internal("carToRight.png"));
        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture4));

        ImageButton obj4 = new ImageButton(style4);

        obj4.setSize(300,180);
        obj4.setPosition(GraphicConstants.colWidth*1.2f, GraphicConstants.rowHeight*3.8F);
        objects.add(obj4);

        Texture myTexture1 = new Texture(Gdx.files.internal("blue1.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(280,160);
        obj.setPosition(GraphicConstants.colWidth*5f, GraphicConstants.rowHeight*3.3F);
        correctAnswer=obj;
    }

    private void initializeLevel12() {
        //read from Database
        question="Який транспортний засіб проїде перехрестя другим?";
        backgroundImagePath = "level12_PQ.jpg";
        objects = new LinkedList<>();

        Texture myTexture2 = new Texture(Gdx.files.internal("motorcycle.png"));
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture2));

        ImageButton obj2 = new ImageButton(style2);

        obj2.setSize(80,180);
        obj2.setPosition(GraphicConstants.colWidth*3.2f, GraphicConstants.rowHeight*3.2F);
        objects.add(obj2);
        Texture myTexture1 = new Texture(Gdx.files.internal("CarToRightWithLeftLights.png"));
        TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(myTexture1));

        ImageButton obj = new ImageButton(t);
        obj.setSize(300,160);
        obj.setPosition(GraphicConstants.colWidth*0.55f, GraphicConstants.rowHeight*4.05F);
        objects.add(obj);

        Texture myTexture4 = new Texture(Gdx.files.internal("carPQ.png"));
        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture4));

        ImageButton obj4 = new ImageButton(style4);

        obj4.setSize(280,150);
        obj4.setPosition(GraphicConstants.colWidth*4.95f, GraphicConstants.rowHeight*4.6F);

        correctAnswer=obj4;
    }

    private void initializeLevel15() {
        //read from Database
        question="Який транспортний засіб порушує ПДР?";
        backgroundImagePath = "level7_PQ.jpg";
        objects = new LinkedList<>();

        Texture myTexture = new Texture(Gdx.files.internal("carWithTrailer.png"));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture));

        ImageButton obj1 = new ImageButton(style);

        obj1.setSize(430,210);
        obj1.setPosition(GraphicConstants.colWidth*4f, GraphicConstants.rowHeight*3.5F);
        objects.add(obj1);

        Texture myTexture2 = new Texture(Gdx.files.internal("truck11.png"));
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture2));

        ImageButton obj2 = new ImageButton(style2);

        obj2.setSize(340,190);
        obj2.setPosition(GraphicConstants.colWidth*2.8f, GraphicConstants.rowHeight*5.45F);
        objects.add(obj2);



        Texture myTexture4 = new Texture(Gdx.files.internal("level15_wrongCar.png"));
        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.imageUp = new TextureRegionDrawable(new TextureRegion(myTexture4));

        ImageButton obj4 = new ImageButton(style4);

        obj4.setSize(700,250);
        obj4.setPosition(GraphicConstants.colWidth*1.8f, GraphicConstants.rowHeight*2.95F);

        correctAnswer=obj4;
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

