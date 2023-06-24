package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalRelationsTextQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;


/**
 * Даний клас реалізовує логіку роботи практики 8 рівня: формування завдань, підрахунок балів, навігація між завданнями
 Тема 8: Швидкість руху
 * 3 запитання з вводом - 15 балів
 * 2 тести - 6 балів
 * 1 запитання на відповідність (текст) - 6 балів
 *Всього: 27 балів, прохідний - 21
 * **/
 public class level8 extends Level {

    public level8(Drivetopia app, int userID) {

        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 8;
        this.tasks=new LinkedList<>();
        this.currentscore = 0;
        this.maximumScore = 27;
        this.failureScore = 7;



        /**Дістаємо питання з вводом**/
        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

        /**Обираємо 3 серед них**/
        LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,3);

        /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
        for (NormalTextInputQuestion q: finalInputQuestions
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }

        /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 8**/
        LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,levelNumb);

        /**Обираємо 2 серед них**/
        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,2);

        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (SimpleTextChoiceQuestion q: finalChoiceQuestions
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }




        LinkedList<NormalRelationsQuestion> relationsQuestions = NormalRelationsQuestion.extractNormalRelationsQuestionFromDB(db,levelNumb, "text");


        LinkedList<NormalRelationsQuestion> finalRelationsQuestions = selectRandomRelationsQuestions(relationsQuestions,1);

        Collections.shuffle(finalRelationsQuestions);

        /**Створюємо екрани для кожного запитання на відповідність і додаємо в список екранів рівня**/
        for (NormalRelationsQuestion q: finalRelationsQuestions
        ) {
            tasks.add(new NormalRelationsTextQuestionScreen(app,q,this,userID));
        }




        Collections.shuffle(tasks);

        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }

}
