package com.wheelie.co.levels20;

import android.database.sqlite.SQLiteDatabase;

import com.wheelie.co.Drivetopia;
import com.wheelie.co.levelTemplates.NormalTextInputQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;

import java.util.Collections;
import java.util.LinkedList;

/**Тема 9: Дистанція, інтервал, зустрічний роз'їзд
 *
 */
public class level9 extends Level {

    public level9(Drivetopia app, int userID) {

        final SQLiteDatabase db = app.getDatabase();

        this.levelNumb = 9;
        this.tasks=new LinkedList<>();
        this.currentscore = 0;
        this.maximumScore = 10;
        this.failureScore = 6;



        /**Дістаємо питання з вводом**/
        LinkedList<NormalTextInputQuestion> inputQuestions = NormalTextInputQuestion.extractNormalTextInputQuestionsFromDB(db,levelNumb);

        /**Обираємо 3 серед них**/
        LinkedList<NormalTextInputQuestion> finalInputQuestions = selectRandomInputQuestions(inputQuestions,3);

        Collections.shuffle(finalInputQuestions);

        /**Створюємо екрани для кожного запитання з вводом і додаємо в список екранів рівня**/
        for (NormalTextInputQuestion q: finalInputQuestions
        ) {
            tasks.add(new NormalTextInputQuestionScreen(app,q,this,userID));
        }

        // tasks.add(new HardPictureQuestionScreen(app,new HardPictureQuestion(4,false),this,userID));

        this.numberOfTasks = tasks.size();
        this.taskCounter = 1;

    }
}
