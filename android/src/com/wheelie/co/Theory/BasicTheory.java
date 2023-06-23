package com.wheelie.co.Theory;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;

import java.util.LinkedList;

/**
 * Даний клас реалізовує взаємодію слайдів теорії з базою даних
 * **/
public class BasicTheory {
    private static SQLiteDatabase db;
    private static Drivetopia app;

    public static void setApp(Drivetopia app) {
        BasicTheory.app = app;
        db = app.getDatabase();
    }

    public static LinkedList<Slide> getSlides(int level){
        LinkedList<Slide> slides = Slide.extractSlidesFromDB(db,level);
        return slides;
    }
}
