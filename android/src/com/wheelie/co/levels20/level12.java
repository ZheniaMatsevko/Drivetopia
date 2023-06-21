package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.InteractiveCrosswalkScreen;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;

/**Тема 12: Проїзд перехресть, пішохідних переходів
 ** інтерактивне завдання пішоходним переходом - 10 балів
 * * 2 завдання зі вводом - 10 балів
 * * 1 тестове запитання - 3 бали
 * * завдання з натисканням на картинку(перехрестя)  - 7 балів
 * *Всього: 30 балів, прохідний - 24**/
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
        this.failureScore = 7;


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

        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(levelNumb,false),this,userID));


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

