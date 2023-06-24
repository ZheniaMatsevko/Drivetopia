package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.InteractiveCrosswalkScreen;
import com.wheelie.co.levelTemplates.NormalRelationsQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;

import java.util.Collections;
import java.util.LinkedList;


/**
 * Даний клас реалізовує логіку роботи практики 10 рівня: формування завдань, підрахунок балів, навігація між завданнями
 Тема 10: Обгін
 *3 запитання з вводом - 15 балів
 * **/
public class level10 extends Level{
    public level10(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 10;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.userId = userID;
        this.currentscore = 0;
        this.maximumScore = 15;
        this.failureScore = 5;





        /**Дістаємо питання з вводом**/
        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

        /**Обираємо 3 серед них**/
        LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,3);

        Collections.shuffle(finalInputQuestions);

        /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
        for (NormalTextInputQuestion q: finalInputQuestions
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }





        Collections.shuffle(tasks);


        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;




    }
    public void start() {

    }


    @Override
    public void finish() {

    }
}
