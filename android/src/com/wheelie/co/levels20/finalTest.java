package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.InteractiveCrosswalkScreen;
import com.wheelie.co.Graphics.InteractiveParkingScreen;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalFlashCardQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsTextQuestionScreen;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Даний клас реалізовує логіку роботи фінального тесту: формування завдань, підрахунок балів, навігація між завданнями
 */
public class finalTest extends Level {

    public finalTest(Drivetopia app, int userID) {
        // (app,1,new SimpleTextChoiceQuestion()));
        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 16;
        this.tasks=new LinkedList<>();
        this.app = app;
        this.currentscore = 0;
        this.maximumScore = 104;
        this.failureScore = 10;



        /**1)1 тест з рівню 4 **/

        LinkedList<SimpleTextChoiceQuestion> choiceQuestions4 = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,4);


        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions4 = selectRandomSimpleChoiceQuestions(choiceQuestions4,1);


        for (SimpleTextChoiceQuestion q: finalChoiceQuestions4
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }

        /**2) завдання з картинкою з рівню 4**/
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(4,false),this,userID));

        /**3) завдання з картинкою з рівню 7**/
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(7,false),this,userID));

        /**4-5) 2 завдання з картинкою з рівню 11**/
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(11,false),this,userID));
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(11,true),this,userID));

        /**6) завдання з картинкою з рівню 12**/
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(12,false),this,userID));

        /**7) завдання з картинкою з рівню 15**/
        tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(15,false),this,userID));

        /**8-9) 2 флеш-картки з рівню 14**/
        LinkedList<NormalFlashCardQuestion> flashCardQuestions2 = NormalFlashCardQuestion.extractNormalFlashCardQuestionFromDB(db,14, "marking");

        LinkedList<NormalFlashCardQuestion> finalFlashCardQuestions2 = selectRandomFlashCardQuestions(flashCardQuestions2,2);

        Collections.shuffle(finalFlashCardQuestions2);

        for (NormalFlashCardQuestion q: finalFlashCardQuestions2
        ) {
            tasks.add(new NormalFlashCardQuestionScreen(app,q,this,userID));
        }

        /**10) завдання з паркуванням**/
        tasks.add(new InteractiveParkingScreen(app,this,userID));

        /**11-12
         * 2 тести з рівня 2
         */
        LinkedList<SimpleTextChoiceQuestion> choiceQuestions = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,2);
        Log.d("size",String.valueOf(choiceQuestions.size()));


        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions = selectRandomSimpleChoiceQuestions(choiceQuestions,2);


        for (SimpleTextChoiceQuestion q: finalChoiceQuestions
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }

        /**13-14
         * 1 завдання на введення і 1 відповідність з рівня 3
         */
        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,3);

        LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,1);

        Collections.shuffle(finalInputQuestions);

        for (NormalTextInputQuestion q: finalInputQuestions
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }

        LinkedList<NormalRelationsQuestion> relationsQuestions2 = NormalRelationsQuestion.extractNormalRelationsQuestionFromDB(db,3, "text");


        LinkedList<NormalRelationsQuestion> finalRelationsQuestions2 = selectRandomRelationsQuestions(relationsQuestions2,1);

        Collections.shuffle(finalRelationsQuestions2);

        for (NormalRelationsQuestion q: finalRelationsQuestions2
        ) {
            tasks.add(new NormalRelationsTextQuestionScreen(app,q,this,userID));
        }

        /**15
         * 1 завдання на введення рівня 4
         */
        LinkedList<NormalTextInputQuestion> inputQuestions2 = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,4);


        LinkedList<NormalTextInputQuestion> finalInputQuestions2 = selectRandomInputQuestions(inputQuestions2,1);

        Collections.shuffle(finalInputQuestions2);
        // LinkedList<SimpleTextChoiceQuestionScreen> simpleScreens = new LinkedList<>();

        for (NormalTextInputQuestion q: finalInputQuestions2
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }

        /**15-17
         *2 тест рівня 5
         */

        LinkedList<SimpleTextChoiceQuestion> choiceQuestions2 = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,6);


        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions2 = selectRandomSimpleChoiceQuestions(choiceQuestions2,2);
        for (SimpleTextChoiceQuestion q: finalChoiceQuestions2
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }

        /**18
         * флешкарта рівня 6
         */

        LinkedList<NormalFlashCardQuestion> flashCardQuestions = NormalFlashCardQuestion.extractNormalFlashCardQuestionFromDB(db,6, "turn");

        LinkedList<NormalFlashCardQuestion> finalFlashCardQuestions = selectRandomFlashCardQuestions(flashCardQuestions,1);

        for (NormalFlashCardQuestion q: finalFlashCardQuestions
        ) {
            tasks.add(new NormalFlashCardQuestionScreen(app,q,this,userID));
        }
        //13


        /**19-20
         * 2 тести до рівню 8**/
        LinkedList<SimpleTextChoiceQuestion> choiceQuestions3 = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(db,8);

        LinkedList<SimpleTextChoiceQuestion> finalChoiceQuestions3 = selectRandomSimpleChoiceQuestions(choiceQuestions3,2);

        for (SimpleTextChoiceQuestion q: finalChoiceQuestions3
        ) {
            tasks.add(new SimpleTextChoiceQuestionScreen(app,q,this,userID));
        }

        Collections.shuffle(tasks);



        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }




}
