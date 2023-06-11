package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;


/**Тема 1: Загальні положення
 * 5 тестових питань - 15 балів
 * 2 завдання де вводиш відповідь сам - 10 балів
 * Всього - 25 балів**/
public class level1 extends Level {

 //   Drivetopia app;
    public level1() {}
    public level1(Drivetopia app, int userID) {
       // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 1;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 20;



        /**Дістаємо всі тестові завдання з вибором, що відносяться до рівню 1**/
     LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,1);

     /**Обираємо 5 сердед них**/
    // LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,5);



    // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();
     /**Створюємо екрани для кожного запитання з вибором і додаємо в список екранів рівня**/
        for (SimpleTextChoiceQuestion q: choiceQuestions
             ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }


        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;




        //start();
    }











    public static LinkedList<SimpleTextChoiceQuestion> selectRandomSimpleChoiceQuestions(LinkedList<SimpleTextChoiceQuestion> questions, int c) {
        LinkedList<SimpleTextChoiceQuestion> selectedQuestions = new LinkedList<>();

        if (questions.size() <= c) {
            selectedQuestions.addAll(questions);
        } else {
            // Create a random number generator
            Random random = new Random();

            // Create a set to keep track of selected indices
            Set<Integer> selectedIndices = new HashSet<>();

            // Select 5 random questions
            while (selectedIndices.size() < c) {
                int randomIndex = random.nextInt(questions.size());
                if (!selectedIndices.contains(randomIndex)) {
                    selectedQuestions.add(questions.get(randomIndex));
                    selectedIndices.add(randomIndex);
                }
            }
        }

        return selectedQuestions;
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
