package com.wheelie.co.levelTemplates.questionTemplates;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class SimpleTextChoiceQuestion {


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String text;
    private String correctAnswer;
    private String picture;

    public LinkedList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    private LinkedList<String> wrongAnswers;



    //констр. за замовчуванням, підходитиме для 1 завдання 1 рівню
    public SimpleTextChoiceQuestion() {

        LinkedList<String> list = new LinkedList<>();
        list.add("автомагістраль");
        list.add("головна дорога");
        list.add("Пєчкурова");
        list.add("Кирієнко");
        list.add("велосипед");
        list.add("велосипедист");
        list.add("Корнійчук");
        list.add("дедлайн");
        list.add("президент");
        list.add("президент НаУКМА");

        this.wrongAnswers=list;
        this.correctAnswer="поліція";
         this.text=" Дозволи на будівництво та \n експлуатацію об'єктів біля\n доріг видають органи\n дорожнього управління, а \n платить і погоджує їх: ";
         this.picture="noPicture";
    }

    public LinkedList<String> generateWrongAnswers(LinkedList<String> answers) {
        LinkedList<String> randomElements = new LinkedList<>();
        Random random = new Random();
        Set<Integer> selectedIndices = new HashSet<>();

        int listSize = answers.size();
        if (listSize <= 3) {
            randomElements.addAll(answers);
        } else {
            while (selectedIndices.size() < 3) {
                int randomIndex = random.nextInt(listSize);
                if (!selectedIndices.contains(randomIndex)) {
                    randomElements.add(answers.get(randomIndex));
                    selectedIndices.add(randomIndex);
                }
            }
        }

        return randomElements;
    }

    public void setWrongAnswers(LinkedList<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }




    public SimpleTextChoiceQuestion (String text,String correctAnswer,String picture,LinkedList<String> wrongAnswers){
this.text = text;
this.correctAnswer=correctAnswer;
this.picture=picture;
this.wrongAnswers=wrongAnswers;
    }





}
