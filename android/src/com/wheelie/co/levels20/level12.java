package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.InteractiveCrosswalkScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;

/**Тема 12: Проїзд перехресть, пішохідних переходів
 * 2 тестових питань - 6 балів
 * 2 завдання на введення відповіді - 10 балів
 * 1 інтерактивне завдання - 10 балів
 *
 * Всього - 26 балів**/
public class level12 extends Level {
    public level12(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 12;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.userId = userID;
        this.currentscore = 0;
        this.maximumScore = 20;
        this.failureScore = 5;


       tasks.add(new InteractiveCrosswalkScreen(app,this,userID));

        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

        /**Обираємо 2 серед них**/
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
    public void start() {

    }


    @Override
    public void finish() {

    }
}

