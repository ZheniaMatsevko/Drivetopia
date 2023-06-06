package com.wheelie.co.levels20;

import com.badlogic.gdx.ScreenAdapter;

import java.util.LinkedList;

public interface Level {

    //метод для кожного рівня який його запускає
    void start();

     
    //список всіх завдань рівня
    public LinkedList<ScreenAdapter> getTasks();


    //хз чи це буде потрібно, метод для оновлення БД після закінчення
    void finish();
}
