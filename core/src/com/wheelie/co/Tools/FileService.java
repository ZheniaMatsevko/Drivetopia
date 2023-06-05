package com.wheelie.co.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileService {
    public static String readTheory(int level){

        String filePath = "theory" + level + ".txt"; // Replace with the path to your .txt file

        FileHandle fileHandle = Gdx.files.internal(filePath);
        String theory = fileHandle.readString();

        return theory;
    }

    public static String readFile(String path){

        FileHandle fileHandle = Gdx.files.internal(path);
        String text = fileHandle.readString();

        return text;
    }

}
