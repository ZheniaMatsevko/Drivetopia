package com.wheelie.co.levelTemplates.questionTemplates;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import DBWorkH.DBConstants;

public class NormalRelationsQuestion {
    //private LinkedList<ImageButton> images;

    private HashMap<ImageButton,String> imageTasks;
    private HashMap<String,String> textTasks;
    private String type;
    private String question;

    public NormalRelationsQuestion(int level){
        initialize(level);
    }
    public NormalRelationsQuestion(String type){
        question="Встановіть відповідність";
        this.type=type;
        imageTasks = new HashMap<>();
        textTasks = new HashMap<>();
        if(type=="text"){
            textTasks.put("попереджувальні знаки","інформують водіїв про наближення до небезпечної ділянки дороги і характер небезпеки.");
            textTasks.put("знаки пріоритету","встановлюють черговість проїзду перехресть, перехрещень проїзних частин або вузьких ділянок дороги;");
            textTasks.put("заборонні знаки","показують обов’язкові напрямки руху або дозволяють деяким категоріям учасників рух по проїзній частині чи окремих її ділянках, а також запроваджують або скасовують деякі обмеження;");
              /*попереджувальні знаки — інформують водіїв про наближення до небезпечної ділянки дороги і характер небезпеки. Під час руху по цій ділянці необхідно вжити заходів для безпечного проїзду;
знаки пріоритету — встановлюють черговість проїзду перехресть, перехрещень проїзних частин або вузьких ділянок дороги;
заборонні знаки — запроваджують або скасовують певні обмеження в русі;
наказові знаки — показують обов’язкові напрямки руху або дозволяють деяким категоріям учасників рух по проїзній частині чи окремих її ділянках, а також запроваджують або скасовують деякі обмеження;
інформаційно-вказівні знаки — запроваджують або скасовують певний режим руху, а також інформують учасників дорожнього руху про розташування населених пунктів, різних об’єктів, територій, де діють спеціальні правила;
знаки сервісу — інформують учасників дорожнього руху про розташування об’єктів обслуговування;
таблички до дорожніх знаків — уточнюють або обмежують дію знаків, разом з якими вони встановлені.
*/
        }
    }
    public void addImageTask(String text, String answer){
        Texture myTexture = new Texture(Gdx.files.internal(answer));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        ImageButton obj1 = new ImageButton(myTexRegionDrawable);
        obj1.setSize(300,300);
        imageTasks.put(obj1,text);
    }



    private void initialize(int level) {
        //read from Database
        question="Встановіть відповідність";
        imageTasks = new HashMap<>();
        String[] texts = new String[]{"Кінець велосипедної смуги","Місце для стоянки", "Виїзд на дорогу із смугою для руху маршрутних транспортних засобів"};
        for(int i=1;i<4;i++){

            Texture myTexture = new Texture(Gdx.files.internal("sign"+i+".png"));
            TextureRegion myTextureRegion = new TextureRegion(myTexture);
            TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

            ImageButton obj1 = new ImageButton(myTexRegionDrawable);
            obj1.setSize(300,300);

            imageTasks.put(obj1,texts[i-1]);
        }

    }

    public static LinkedList<NormalRelationsQuestion> extractNormalRelationsQuestionFromDB(SQLiteDatabase db, int levelNumb, String type) {
        String[] columns = {"text", "answer"};

        String selection = "levelNumb = ? AND type = ?";
        String[] selectionArgs = {String.valueOf(levelNumb), type};

        Cursor cursor = db.query(DBConstants.RELATIONS_TABLE, columns, selection, selectionArgs, null, null, null);

        HashMap<String, String> questions=new HashMap<>();

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                questions.put(text,answer);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return formRelationsQuestions(questions,type);
    }

    private static LinkedList<NormalRelationsQuestion> formRelationsQuestions(HashMap<String, String> questions, String type) {
        LinkedList<NormalRelationsQuestion> list= new LinkedList<>();
        Collection<String> keys = questions.keySet();

        ArrayList<String> keyList = new ArrayList<>(keys);

        Collections.shuffle(keyList);
        NormalRelationsQuestion normalRelationsQuestion = new NormalRelationsQuestion(type);
        for(int i=0;i<keyList.size();i++){
            if(i!=0 && i%3==0){
                list.add(normalRelationsQuestion);
                normalRelationsQuestion = new NormalRelationsQuestion(type);
            }
            if(type=="image")
                normalRelationsQuestion.addImageTask(keyList.get(i),questions.get(keyList.get(i)));
            else
                normalRelationsQuestion.addTextTask(keyList.get(i),questions.get(keyList.get(i)));

        }

        return list;
    }

    private void addTextTask(String question, String answer) {
        textTasks.put(question,answer);
    }

    public String getType() {
        return type;
    }

    public String findAnswer(ImageButton image){
        if(imageTasks.containsKey(image)){
            int index = -1;
            int currentIndex = 0;
            for (Map.Entry<ImageButton, String> entry : imageTasks.entrySet()) {
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
    public String findTextAnswer(String question){
        if(textTasks.containsKey(question)){
            int index = -1;
            int currentIndex = 0;
            for (Map.Entry<String, String> entry : textTasks.entrySet()) {
                if (entry.getKey().equals(question)) {
                    index = currentIndex;
                    break;
                }
                currentIndex++;
            }
            return String.valueOf(index + 1);
        }
        return "";
    }

    public NormalRelationsQuestion(HashMap<ImageButton,String> imageTasks, String question){
        this.imageTasks = imageTasks;
        this.question=question;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public HashMap<ImageButton, String> getImageTasks() {
        return imageTasks;
    }
    public ArrayList<String> getTexts() {
        if(type=="image")
            return new ArrayList<>(imageTasks.values());
        else
            return new ArrayList<>(textTasks.keySet());
    }
    public ArrayList<String> getAnswers() {
        return new ArrayList<>(textTasks.values());
    }
    public ArrayList<ImageButton> getImages() {
        return new ArrayList<>(imageTasks.keySet());
    }

    public void setImageTasks(HashMap<ImageButton, String> imageTasks) {
        this.imageTasks = imageTasks;
    }
}
