package com.wheelie.co.Theory;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Tools.FileService;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;

import java.util.LinkedList;

import DBWorkH.DBConstants;

public class Slide {
    private String image;
    private String text;

    public Slide(String image, String text){
        this.text= FileService.readFile(text);
        this.image=image;
        //image = new Image(new Texture(path));
        //image.setSize(GraphicConstants.screenWidth-10,GraphicConstants.rowHeight*2.5f);
    }

    public static LinkedList<Slide> extractSlidesFromDB(SQLiteDatabase db, int levelNumb) {
        String[] columns = {"text", "image"};

        String selection = "levelNumb = ?";
        String[] selectionArgs = {String.valueOf(levelNumb)};

        // Query the table and get the cursor
        Cursor cursor = db.query(DBConstants.THEORY_TABLE, columns, selection, selectionArgs, null, null, null);

        LinkedList<Slide> list = new LinkedList<>();

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                 list.add(new Slide(image,text));

            } while (cursor.moveToNext());
            // Close the cursor
            cursor.close();

        }

        return list;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
