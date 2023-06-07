package com.wheelie.co.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.wheelie.co.Graphics.GraphicConstants;

import java.util.LinkedList;

public class TheoryScrollPane {
    public final ScrollPane scrollPane  = new ScrollPane(null);
    public TheoryScrollPane(int level, String title,Skin skin, Skin skinForText, LinkedList<String> texts, LinkedList<Image> images, String topic){
        scrollPane.setBounds(10, 10, GraphicConstants.screenWidth - 20, GraphicConstants.screenHeight - 20);
        if(level>10)
            return;
        Image image = new Image(new Texture("theory" + level + ".jpg"));
        image.setSize(2000f,1000);

        String text = FileService.readTheory(level);

        Label titleLabel = new Label(title,skin);
        titleLabel.setWrap(true);

        Label label = new Label(text, skinForText);
        //label.setWidth(1000f); // Adjust the width according to your needs
        label.setWrap(true);

        /*Image image1 = new Image(new Texture("b13.jpg"));
        image1.setSize(300,300);
        Label label1= new Label("Your large amount of text gext 1 dkihawK CKLdaclj LD CALD clext 1 dkihawK CKLdaclj LD CALD clext 1 dkihawK CKLdaclj LD CALD cl\\next 1 dkihawK CKLdaclj LD CALD clext 1 dkihawK CKLdaclj LD CALD cloes here... fffffffffffffff ffffffffffffffffffff ffffffffffffffffffffffff ffffffff", skin1);
        //label1.setWidth(GraphicConstants.screenWidth-10); // Adjust the width according to your needs
        label1.setWrap(true);*/

        Table table = new Table();
        table.defaults().pad(10,10,200,10);

        table.add(image).width(GraphicConstants.screenWidth-20).row();
        table.add(titleLabel).width(GraphicConstants.screenWidth-20).row();
        table.add(label).width(GraphicConstants.screenWidth-20).row();
        //table.add(image1).row();
        //table.add(label1).width(GraphicConstants.screenWidth-20).row();
        //table.setFillParent(true);
        //table.pack();
        scrollPane.setActor(table);
        scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling

    }

}
