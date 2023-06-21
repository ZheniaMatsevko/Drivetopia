package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsTextQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;

/**Тема 7: Розташування транспорту на дорозі
 * завдання з натисканням на картинку (вантажівка-порушник)  - 7 балів
 *???**/

 public class level7 extends Level{
 public level7() {}

 public level7(Drivetopia app, int userID) {
  // (app,1,new SimpleTextChoiceQuestion()));
  final SQLiteDatabase db = app.getDatabase();

  this.levelNumb = 7;
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

