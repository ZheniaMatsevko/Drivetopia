package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;

import java.util.LinkedList;

/**Тема 11. Зупинка і стоянка
        * інтерактивне завдання з паркуванням  - 10 балів
        * 2 завдання з натисканням на картинку  - 14 балів
        * * 2 тести - 6 балів (не зроблено)
        *Всього: 30, прохідний - 24**/

public class level11 extends Level{
    public level11() {}

    public level11(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 11;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 30;
        this.failureScore = 7;

        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(levelNumb,false),this,userID));
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(levelNumb,true),this,userID));


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

