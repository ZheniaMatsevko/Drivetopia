package com.wheelie.co.levels20;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.LinkedList;

public class level1 implements Level {

    LinkedList<ScreenAdapter> tasks;
    Drivetopia app;
    public level1(Drivetopia app) {
        this.tasks = new LinkedList<>();
        tasks.add(new SimpleTextChoiceQuestionScreen(app,1,new SimpleTextChoiceQuestion()));


        start();
    }





    @Override
    public void start() {

            this.app.setScreen(this.tasks.getFirst());


    }

    @Override
    public LinkedList<ScreenAdapter> getTasks() {
        return null;
    }

    @Override
    public void finish() {

    }
}
