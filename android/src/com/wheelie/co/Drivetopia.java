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
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.level1;

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

		ArrayList<String> list = new ArrayList<>();
		list.add("автомагістраль");
		list.add("головна дорога");
		list.add("Пєчкурова");







		shapeRenderer = new ShapeRenderer();

		//LinkedList<SimpleTextChoiceQuestion> q = SimpleTextChoiceQuestion.extractSimpleTextChoiceQuestionsFromDB(database,1);
		//setScreen(new SimpleTextChoiceQuestionScreen(this,q.get(0),new level1(this,1),1));
        //level1 l = new level1(this,1);
		//setScreen(l.tasks.get(0));
	  // setScreen(new SimpleTextChoiceQuestionScreen(this,q.get(0),new level1(this,1),1));
       // setScreen(new IntermediateScreen())

		//setScreen(new RegistrationScreen(this));

		//setScreen(new AuthorizationScreen(this));
	//	setScreen(new MainMenuScreen(this,1));
		setScreen(new BeginningScreen(this));
//	setScreen(new ProfileScreen(this,1,1));
	//setScreen(new SimpleTextChoiceQuestionScreen(this,1,q));
		//setScreen(new LevelsScreen(this,1,1));

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
