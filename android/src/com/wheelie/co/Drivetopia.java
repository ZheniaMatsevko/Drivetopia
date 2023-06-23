package com.wheelie.co;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wheelie.co.Graphics.AuthorizationScreen;
import com.wheelie.co.Graphics.BeginningScreen;
import com.wheelie.co.Graphics.LevelsScreen;
import com.wheelie.co.Graphics.MainMenuScreen;
import com.wheelie.co.Graphics.RegistrationScreen;
import com.wheelie.co.Theory.Slide;
import com.wheelie.co.Theory.TheoryScreen;
import com.wheelie.co.Tools.FileService;
import com.wheelie.co.levelTemplates.HardPictureQuestionScreen;
import com.wheelie.co.levelTemplates.NormalRelationsTextQuestionScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.finalTest;
import com.wheelie.co.levels20.level1;
import com.wheelie.co.levels20.level3;
import com.wheelie.co.levels20.level4;

import java.util.ArrayList;
import java.util.LinkedList;

import DBWorkH.DatabaseHelperH;

public class Drivetopia extends Game {

	private SQLiteDatabase database;


	private ShapeRenderer shapeRenderer;
	public Drivetopia(SQLiteDatabase database) {
		this.database = database;
	}


	public SQLiteDatabase getDatabase() {
		return database;
	}

	/**
	 * Запускаєм початковий екран
	 */
	@Override
	public void create () {




		shapeRenderer = new ShapeRenderer();

		//LinkedList<SimpleTextChoiceQuestion> q = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(database,1);
		//setScreen(new SimpleTextChoiceQuestionScreen(this,q.get(0),new level1(this,1),1));
        //level1 l = new level1(this,1);
		//setScreen(l.tasks.get(0));
	  // setScreen(new SimpleTextChoiceQuestionScreen(this,q.get(0),new level1(this,1),1));
		finalTest f = new finalTest(this,1);
		//f.failureScoreCount+=6;
       setScreen(new IntermediateScreen(this,f,2,2,false));

		//setScreen(new RegistrationScreen(this));

		//setScreen(new AuthorizationScreen(this));
	//	setScreen(new MainMenuScreen(this,1));
		//setScreen(new NormalRelationsTextQuestionScreen(this,new NormalRelationsQuestion("text"), new level4(),1));
//	setScreen(new LevelsScreen(this,1));
		//setScreen(new HardPictureQuestionScreen(this, new HardPictureQuestion(11, true),new level3(),1));
		//	setScreen(new LevelsScreen(this,2));
		/*LinkedList<Slide> slides = new LinkedList<>();
		slides.add(new Slide("theory1.jpg", FileService.readTheory(1)));
		slides.add(new Slide("theory2-3.jpg", FileService.readTheory(2)));
		slides.add(new Slide("theory3-2.jpg", FileService.readTheory(3)));
		slides.add(new Slide("theory4.jpg", FileService.readTheory(4)));
		slides.add(new Slide("theory5.jpg", FileService.readTheory(5)));
		slides.add(new Slide("theory6.jpg", FileService.readTheory(6)));
		//setScreen(new TheoryScreen(this,slides,1));
*/
	//setScreen(new SimpleTextChoiceQuestionScreen(this,1,q));

		//setScreen(new SimpleTextChoiceQuestionScreen(this,1,q));


	}




	/**
	 * Видаляєм shapeRenderer
	 */
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

}
