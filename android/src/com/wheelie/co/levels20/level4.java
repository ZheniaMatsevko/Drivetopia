package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsTextQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class level4 extends Level{
    public level4() {}
    /**Тема 4: Регулювання дорожнього руху
     * 2 флеш-картки - 10 балів
     * 2 запитання з вводом - 10 балів
     * 1 тест - 3 бали
     * 1 запитання на відповідність - 6 балів
     * Всього: 23 бали**/
    public level4(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 4;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 29;
        this.failureScore = 11;




        /**Дістаємо всі флеш-картки, що відносяться до рівню 4**/
        LinkedList<NormalFlashCardQuestion> flashCardQuestions = NormalFlashCardQuestion.extractNormalFlashCardQuestionFromDB(db,4, "sign");

        /**Обираємо 2 сердед них**/
        LinkedList<NormalFlashCardQuestion> finalFlashCardQuestions = selectRandomFlashCardQuestions(flashCardQuestions,2);

        Collections.shuffle(finalFlashCardQuestions);
        // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();
        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (NormalFlashCardQuestion q: finalFlashCardQuestions
        ) {
            tasks.add(new NormalFlashCardQuestionScreen(app,q,this,userID));
        }


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

        /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 1**/
        LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,4);

        /**Обираємо 1 серед них**/
        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,1);

        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (SimpleTextChoiceQuestion q: finalChoiceQuestions
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }

        LinkedList<NormalRelationsQuestion> relationsQuestions = NormalRelationsQuestion.extractNormalRelationsQuestionFromDB(db,levelNumb, "text");


        LinkedList<NormalRelationsQuestion> finalRelationsQuestions = selectRandomRelationsQuestions(relationsQuestions,1);

        Collections.shuffle(finalRelationsQuestions);

        /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (NormalRelationsQuestion q: finalRelationsQuestions
        ) {
            tasks.add(new NormalRelationsTextQuestionScreen(app,q,this,userID));
        }


       Collections.shuffle(tasks);



        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

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
