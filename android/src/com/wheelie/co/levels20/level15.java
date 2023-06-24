package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;

import java.util.Collections;
import java.util.LinkedList;


/**
 * Даний клас реалізовує логіку роботи практики 14 рівня: формування завдань, підрахунок балів, навігація між завданнями
 Тема 15. Рух: у житловій та пішохідній зоні, по автомагістралях і дорогах для автомобілів
 * завдання з натисканням на картинку(причеп)  - 7 балів
 * 3 завдання з вводом - 15 балів
 * Всього: 22 бали
 * **/
 public class level15 extends Level{
 public level15() {}

 public level15(Drivetopia app, int userID) {
  // (app,1,new SimpleTextChoiceQuestion()));
  final SQLiteDatabase db = app.getDatabase();

  this.levelNumb = 15;
  this.tasks=new LinkedList<>();
  this.app = app;
  this.currentscore = 0;
  this.maximumScore = 22;
  this.failureScore = 6;

  tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(levelNumb,false),this,userID));

  /**Дістаємо питання з вводом**/
  LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

  /**Обираємо 3 серед них**/
  LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,3);

  /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
  for (NormalTextInputQuestion q: finalInputQuestions
  ) {
   tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
  }

  Collections.shuffle(tasks);

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

