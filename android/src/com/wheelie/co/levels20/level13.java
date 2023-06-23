package com.wheelie.co.levels20;


import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;


/**
 * Даний клас реалізовує логіку роботи практики 13 рівня: формування завдань, підрахунок балів, навігація між завданнями
 Тема 13: Номерні, розпізнавальні знаки, написи і позначення
 * запитання на відповідність (з картинкою)  - 6 балів
 *3 тести - 9 балів
 *2 запитання з вводом - 10 балів
 Всього: 25, прохідний 20
 * **/
public class level13 extends Level{
    public level13(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 13;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.userId = userID;
        this.currentscore = 0;
        this.maximumScore = 25;
        this.failureScore = 6;



        LinkedList<NormalRelationsQuestion> relationsQuestions = NormalRelationsQuestion.extractNormalRelationsQuestionFromDB(db,levelNumb, "image");


        LinkedList<NormalRelationsQuestion> finalRelationsQuestions = selectRandomRelationsQuestions(relationsQuestions,1);

        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (NormalRelationsQuestion q: finalRelationsQuestions
        ) {
            tasks.add(new NormalRelationsQuestionScreen(app,q,this,userID));
        }


        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

        /**Обираємо 2 серед них**/
        LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,2);

        Collections.shuffle(finalInputQuestions);

        /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
        for (NormalTextInputQuestion q: finalInputQuestions
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }




        /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 13**/
        LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,levelNumb);

        /**Обираємо 3 серед них**/
        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,3);

        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (SimpleTextChoiceQuestion q: finalChoiceQuestions
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
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

