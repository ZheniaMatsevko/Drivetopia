package com.wheelie.co.levelTemplates.questionTemplates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NormalRelationsQuestion {
    //private LinkedList<ImageButton> images;

    private HashMap<ImageButton,String> tasks;
    private String question;

    public NormalRelationsQuestion(int level){
        initialize(level);
    }

    private void initialize(int level) {
        //read from Database
        question="Встановіть відповідність";
        tasks = new HashMap<>();
        String[] texts = new String[]{"Кінець велосипедної смуги","Місце для стоянки", "Виїзд на дорогу із смугою для руху маршрутних транспортних засобів"};
        for(int i=1;i<4;i++){

            Texture myTexture = new Texture(Gdx.files.internal("sign"+i+".png"));
            TextureRegion myTextureRegion = new TextureRegion(myTexture);
            TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

            ImageButton obj1 = new ImageButton(myTexRegionDrawable);
            obj1.setSize(300,300);

            tasks.put(obj1,texts[i-1]);
        }

    }
    public String findAnswer(ImageButton image){
        if(tasks.containsKey(image)){
            int index = -1;
            int currentIndex = 0;
            for (Map.Entry<ImageButton, String> entry : tasks.entrySet()) {
                if (entry.getKey().equals(image)) {
                    index = currentIndex;
                    break;
                }
                currentIndex++;
            }
            return String.valueOf(index + 1);
        }
        return "";
    }

    public NormalRelationsQuestion(HashMap<ImageButton,String> tasks, String question){
        this.tasks = tasks;
        this.question=question;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public HashMap<ImageButton, String> getTasks() {
        return tasks;
    }
    public ArrayList<String> getTexts() {
        return new ArrayList<>(tasks.values());
    }
    public ArrayList<ImageButton> getImages() {
        return new ArrayList<>(tasks.keySet());
    }

    public void setTasks(HashMap<ImageButton, String> tasks) {
        this.tasks = tasks;
    }
}
