package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;

import java.util.LinkedList;

/**
 Тема 15. Рух: у житловій та пішохідній зоні, по автомагістралях і дорогах для автомобілів
 * завдання з натисканням на картинку(причеп)  - 7 балів
**/

 public class level15 extends Level{
 public level15() {}

 public level15(Drivetopia app, int userID) {
  // (app,1,new SimpleTextChoiceQuestion()));
  final SQLiteDatabase db = app.getDatabase();

  this.levelNumb = 15;
  this.tasks=new LinkedList<>();
  this.app = app;
  this.currentscore = 0;
  this.maximumScore = 7;
  this.failureScore = 7;

  tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(levelNumb,false),this,userID));


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

