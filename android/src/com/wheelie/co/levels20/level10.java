package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.InteractiveCrosswalkScreen;
import com.wheelie.co.levelTemplates.NormalRelationsQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;

import java.util.Collections;
import java.util.LinkedList;

public class level10 extends Level{
    public level10(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        //final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 10;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.userId = userID;
        this.currentscore = 0;
        this.maximumScore = 2;
        this.failureScore = 1;


        tasks.add(new NormalRelationsQuestionScreen(app,new NormalRelationsQuestion(1),this,userID));

        Collections.shuffle(tasks);


        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;




        //start();
    }
    public void start() {

    }


    @Override
    public void finish() {

    }
}
