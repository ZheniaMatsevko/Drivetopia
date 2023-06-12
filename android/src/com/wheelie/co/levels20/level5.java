package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class level5 extends Level{
    public level5() {}
    public level5(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));


        this.levelNumb = 5;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 10;
        this.failureScore = 6;




      tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(1),this,userID));

        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }

    private LinkedList<NormalFlashCardQuestion> selectRandomFlashCardQuestions(LinkedList<NormalFlashCardQuestion> questions, int c) {
        LinkedList<NormalFlashCardQuestion> selectedQuestions = new LinkedList<>();

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
