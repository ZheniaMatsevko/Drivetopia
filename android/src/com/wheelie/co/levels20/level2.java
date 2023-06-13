package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;



/**Тема 2: Загальні положення
 * 3 тестових питань - 9 балів
 * 3 завдання де вводиш відповідь сам - 15 балів
 * Всього - 24 бали**/
public class level2 extends Level {
     public level2(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 2;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.userId = userID;
        this.currentscore = 0;
        this.maximumScore = 24;
        this.failureScore = 6;




        /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 1**/
        LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,levelNumb);
         Log.d("size",String.valueOf(choiceQuestions.size()));

        /**Обираємо 3 серед них**/
        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,3);


        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (SimpleTextChoiceQuestion q: finalChoiceQuestions
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }


         LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

         /**Обираємо 3 серед них**/
         LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,3);

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
    public void start() {

    }


    @Override
    public void finish() {

    }
}
