package com.wheelie.co.levels20;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public abstract class Level {

    public int levelNumb;

    public Drivetopia app;
    //метод для кожного рівня який його запускає
    void start(){

        app.setScreen(new IntermediateScreen(app,this,userId,0,false));

    };

    public int currentscore = 0;
    public int userId = 2;

    public int numberOfTasks = 0;
    public int taskCounter = 0;

    public int maximumScore = 35;

    /**скільки очок потрібно втратити для провалу**/
    public int failureScore = 5;

    /**підрахунок втрачених очок**/

    public int failureScoreCount = 0;

    public LinkedList<ScreenAdapter> tasks;

    //список всіх завдань рівня
    public LinkedList<ScreenAdapter> getTasks() {
        return tasks;
    }

    public void increaseTaskCounter(){
        taskCounter++;
    };

    public int currentTaskNumber() {
        return taskCounter;
    };

    //хз чи це буде потрібно, метод для оновлення БД після закінчення
    void finish(){};




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
    public static LinkedList<NormalTextInputQuestion> selectRandomInputQuestions(LinkedList<NormalTextInputQuestion> questions, int c) {
        LinkedList<NormalTextInputQuestion> selectedQuestions = new LinkedList<>();

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




}
