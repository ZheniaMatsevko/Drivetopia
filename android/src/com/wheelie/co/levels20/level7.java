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


/**
 * Даний клас реалізовує логіку роботи практики 7 рівня: формування завдань, підрахунок балів, навігація між завданнями
 Тема 7: Розташування транспорту на дорозі
 *  3 запитання з вводом - 15 балів
 * завдання з натисканням на картинку (вантажівка-порушник)  - 7 балів
 *1 тест -  3 бали
 * Всього: 25 балів, прохідний - 19
 * **/

 public class level7 extends Level{
 public level7() {}

 public level7(Drivetopia app, int userID) {
  // (app,1,new SimpleTextChoiceQuestion()));
  final SQLiteDatabase db = app.getDatabase();

  this.levelNumb = 7;
  this.tasks=new LinkedList<>();
  this.app = app;
  this.currentscore = 0;
  this.maximumScore = 25;
  this.failureScore = 7;

  tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(levelNumb,false),this,userID));

  LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

  /**Обираємо 3 серед них**/
  LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,3);

  Collections.shuffle(finalInputQuestions);

  /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
  for (NormalTextInputQuestion q: finalInputQuestions
  ) {
   tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
  }


  /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 7**/
  LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,levelNumb);

  /**Обираємо 1 серед них**/
  LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,1);

  /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
  for (SimpleTextChoiceQuestion q: finalChoiceQuestions
  ) {
   tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
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

