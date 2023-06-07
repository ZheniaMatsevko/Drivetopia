package com.wheelie.co.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyDialog extends Dialog {
    private Label message;
    private Integer level;


    public Integer getLevel() {
        return level;
    }
    public void increaseLevel() {
        level++;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }



    public MyDialog(String title, Skin skin) {
        super(title, skin);
        level=0;
        message = new Label("", skin);
        getContentTable().add(message).pad(20f,50f,20f,50f).row();
        TextButton closeButton = new TextButton("Х", skin);
        closeButton.getLabel().setFontScale(1.5f); // Increase the font size if needed
        closeButton.setColor(Color.PINK);

        // Add a click listener to the close button
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide(); // Close the dialog when the close button is clicked
            }
        });

        // Add the close button to the title table
        getTitleTable().add(closeButton).size(100f).pad(50f,10f,0,0f);
    }
    public void setMessage(String message) {
        this.message.setText(message);
    }

    public String getMessage() {return this.message.getText().toString();};



    @Override
    public float getPrefWidth() {
        return 1050f; // Задайте бажану ширину вікна
    }

    @Override
    public float getPrefHeight() {
        return 500f; // Задайте бажану висоту вікна
    }

    @Override
    protected void result(Object object) {
        this.setVisible(false);
    }
}