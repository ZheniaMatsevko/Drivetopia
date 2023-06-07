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

    private int level;
    public LinkedList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    private LinkedList<String> wrongAnswers;



    //констр. за замовчуванням, підходитиме для 1 завдання 1 рівню
    public SimpleTextChoiceQuestion() {
this.level = 1;
        LinkedList<String> list = new LinkedList<>();
        list.add("автомагістраль");
        list.add("головна дорога");
   //     list.add("Пєчкурова");
        list.add("Глибовець");
        list.add("велосипед");
        list.add("велосипедист");
   //     list.add("Корнійчук");
        list.add("дедлайн");
        list.add("президент");
    //    list.add("президент НаУКМА");

        this.wrongAnswers=list;
        this.correctAnswer="поліція";
         this.text=" Дозволи на будівництво та \n експлуатацію об'єктів біля\n доріг видають органи\n дорожнього управління, а \n платить і погоджує їх: ";
         this.picture="noPicture";
    }




    public SimpleTextChoiceQuestion(int i) {
       this.level = i;

       LinkedList<String> list = new LinkedList<>();
        switch(i) {
            case 1:
                list = new LinkedList<>();
              /**  list.add("автомагістраль");
                list.add("головна дорога");
                list.add("Пєчкурова");
                list.add("Кирієнко");
                list.add("велосипед");
                list.add("велосипедист");
                list.add("Корнійчук");
                list.add("дедлайн");
                list.add("президент");
                list.add("президент НаУКМА");**/

                list.add("президент");
                list.add("місцева влада");
                list.add("Глибовець");
                list.add("спілка водіїв");

                this.wrongAnswers=list;
                this.correctAnswer="поліція";
                this.text=" Дозволи на будівництво та \n експлуатацію об'єктів біля\n доріг видають органи\n дорожнього управління, а \n платить і погоджує їх: ";
                this.picture="noPicture";
                break;

            case 2:
               list = new LinkedList<>();
                list.add("Усі");
                list.add("Ніякі");
                list.add("Статті 91-93");
                list.add("Статті 4-5");
                list.add("Статті 45-46");
                list.add("Статті 7-8");
                list.add("Статті 52-53");
                list.add("Статті 1-10");




                this.wrongAnswers=list;
                this.correctAnswer="Статті 36-38";
                this.text=" Які статті Закону України\n “Про автомобільні дороги”\n стосуються використання\n доріг не за їх\n призначенням? ";
                this.picture="noPicture";
                break;


            case 3:
                list = new LinkedList<>();
                list.add("випередження");
                list.add("гальмування");
                list.add("засліплення");
                list.add("сесія");
                list.add("аварія");
                //   list.add("транспортировка");
                list.add("зупинка");
                list.add("обгін");


                this.wrongAnswers=list;
                this.correctAnswer="буксирування";
                this.text=" Переміщення одним\n транспортним засобом\n іншого, яке не\n належить до експлуатації\n автопоїздів, це: ";
                this.picture="noPicture";

                break;



            case 4:

                list = new LinkedList<>();
                list.add("мопед");
                list.add("потяг");
                list.add("катафалк");
                list.add("легковик");
                list.add("грузовик");
                list.add("велосипед");

                this.wrongAnswers=list;
                this.correctAnswer="автобус";
                this.text=" Автомобіль з кількістю\n місць для сидіння більше\n дев'яти з місцем водія \n включно, призначений для\n перевезення пасажирів: ";
                this.picture="noPicture";


                break;


            case 5:

                list = new LinkedList<>();

                list.add("гальмування");
                list.add("зупинка");
                list.add("перевага");
                list.add("засліпення");
                list.add("естакада");


                this.wrongAnswers=list;
                this.correctAnswer="обгін";
                this.text=" Випередження одного або\n кількох транспортних\n засобів, пов'язане з\n виїздом на смугу\n зустрічного руху: ";
                this.picture="noPicture";

                break;

        }
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


    public int getLevel()
    {
        return this.level;
    }

    public void setLevel(int level) {
        this.level=level;

    }
}
