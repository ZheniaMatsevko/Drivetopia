package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;


/**Тема 1: Загальні положення
 * 5 тестових питань - 15 балів
 * 2 завдання де вводиш відповідь сам - 10 балів
 * Всього - 25 балів, прохідний - 20**/
/**ДОРОБЛЕНО**/

public class level1 extends Level {

 //   Drivetopia app;
    public level1() {}
    public level1(Drivetopia app, int userID) {
       // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 1;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 25;
        this.failureScore = 6;




        /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 1**/
     LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,1);

     /**Обираємо 5 серед них**/
    LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,5);

        Collections.shuffle(finalChoiceQuestions);
    // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();
     /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (SimpleTextChoiceQuestion q: finalChoiceQuestions
             ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }

        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,1);

        /**Обираємо 5 серед них**/
        LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,2);

        Collections.shuffle(finalInputQuestions);
        // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();
        /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
        for (NormalTextInputQuestion q: finalInputQuestions
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }
        Collections.shuffle(tasks);


        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;




        //start();
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
