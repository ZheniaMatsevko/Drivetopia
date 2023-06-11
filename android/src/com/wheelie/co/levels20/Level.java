package com.wheelie.co.levels20;

import com.badlogic.gdx.ScreenAdapter;
import com.wheelie.co.Drivetopia;

import java.util.LinkedList;

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








}
