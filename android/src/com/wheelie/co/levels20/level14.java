package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;

import java.util.Collections;
import java.util.LinkedList;


/**Тема 14. Дорожні знаки і розмітка
        * 2 флеш-картки - 10 балів
        * 2 запитання на відповідність (з картинкою)  - 12 балів
        **1 запитання з вводом - 5 балів (не зроблено)
 *      **Всього: 27 балів, прохідний - 22
**/
public class level14 extends Level{
    public level14(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 14;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.userId = userID;
        this.currentscore = 0;
        this.maximumScore = 27;
        this.failureScore = 6;



        LinkedList<NormalRelationsQuestion> relationsQuestions = NormalRelationsQuestion.extractNormalRelationsQuestionFromDB(db,levelNumb, "image");


        LinkedList<NormalRelationsQuestion> finalRelationsQuestions = selectRandomRelationsQuestions(relationsQuestions,2);

        Collections.shuffle(finalRelationsQuestions);

        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (NormalRelationsQuestion q: finalRelationsQuestions
        ) {
            tasks.add(new NormalRelationsQuestionScreen(app,q,this,userID));
        }


        /**Дістаємо всі флеш-картки, що відносяться до рівню 14**/
        LinkedList<NormalFlashCardQuestion> flashCardQuestions = NormalFlashCardQuestion.extractNormalFlashCardQuestionFromDB(db,14, "sign");

        /**Обираємо 2 серед них**/
        LinkedList<NormalFlashCardQuestion> finalFlashCardQuestions = selectRandomFlashCardQuestions(flashCardQuestions,2);

        Collections.shuffle(finalFlashCardQuestions);
        // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();
        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (NormalFlashCardQuestion q: finalFlashCardQuestions
        ) {
            tasks.add(new NormalFlashCardQuestionScreen(app,q,this,userID));
        }


        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }
    public void start() {

    }


    @Override
    public void finish() {

    }
}

