package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class level5 extends Level{
    public level5() {}
    public level5(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));


        this.levelNumb = 5;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 10;
        this.failureScore = 6;




      tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(1),this,userID));

        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }



    @Override
    public void start() {

/**запускає початок практики, передаючи цей рівень і айді юзера**/
//      app.setScreen(new IntermediateScreen(app,this,userId,0,false));

    }



    @Override
    public void finish() {

    }
}
