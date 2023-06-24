package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.InteractiveParkingScreen;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsTextQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Даний клас реалізовує логіку роботи фінального тесту: формування завдань, підрахунок балів, навігація між завданнями
 */
public class finalTest extends Level {

    public finalTest(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 16;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 100;
        this.failureScore = 10;



        /**1) завдання на відповідність з рівню 4 **/
        LinkedList<NormalRelationsQuestion> relationsQuestions = NormalRelationsQuestion.extractNormalRelationsQuestionFromDB(db,4, "text");


        LinkedList<NormalRelationsQuestion> finalRelationsQuestions = selectRandomRelationsQuestions(relationsQuestions,1);

        /**Створюємо екрани для кожного запитання на відповідність і додаємо в список екранів рівня**/
        for (NormalRelationsQuestion q: finalRelationsQuestions
        ) {
            tasks.add(new NormalRelationsTextQuestionScreen(app,q,this,userID));
        }

        /**2) завдання з картинкою з рівню 4**/
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(4,false),this,userID));



        /**3-4) 2 флеш-картки з рівню 14**/
        LinkedList<NormalFlashCardQuestion> flashCardQuestions2 = NormalFlashCardQuestion.extractNormalFlashCardQuestionFromDB(db,levelNumb, "marking");

        LinkedList<NormalFlashCardQuestion> finalFlashCardQuestions2 = selectRandomFlashCardQuestions(flashCardQuestions2,2);

        Collections.shuffle(finalFlashCardQuestions2);

        for (NormalFlashCardQuestion q: finalFlashCardQuestions2
        ) {
            tasks.add(new NormalFlashCardQuestionScreen(app,q,this,userID));
        }

        /**5) завдання з паркуванням**/
        tasks.add(new InteractiveParkingScreen(app,this,userID));







        Collections.shuffle(tasks);



        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }




}
