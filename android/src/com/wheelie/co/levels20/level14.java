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
        * 4 флеш-картки - 20 балів
        * 2 запитання на відповідність (з картинкою)  - 12 балів
 *      **Всього: 32 бали, прохідний - 24
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
        this.maximumScore = 32;
        this.failureScore = 8;



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

        /**Дістаємо всі флеш-картки, що відносяться до рівню 14**/
        LinkedList<NormalFlashCardQuestion> flashCardQuestions2 = NormalFlashCardQuestion.extractNormalFlashCardQuestionFromDB(db,levelNumb, "marking");

        /**Обираємо 2 серед них**/
        LinkedList<NormalFlashCardQuestion> finalFlashCardQuestions2 = selectRandomFlashCardQuestions(flashCardQuestions2,2);

        Collections.shuffle(finalFlashCardQuestions2);
        // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();
        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (NormalFlashCardQuestion q: finalFlashCardQuestions2
        ) {
            tasks.add(new NormalFlashCardQuestionScreen(app,q,this,userID));
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

