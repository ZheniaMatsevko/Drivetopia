package com.wheelie.co;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wheelie.co.Graphics.BeginningScreen;
import com.wheelie.co.Graphics.MainMenuScreen;
import com.wheelie.co.Graphics.ProfileScreen;
import com.wheelie.co.levelTemplates.SimpleTextChoiceQuestionScreen;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;

import java.util.ArrayList;

public class Drivetopia extends Game {

	private ShapeRenderer shapeRenderer;

	/**
	 * Запускаєм початковий екран
	 */
	@Override
	public void create () {

		ArrayList<String> list = new ArrayList<>();
		list.add("автомагістраль");
		list.add("головна дорога");
		list.add("Пєчкурова");



		SimpleTextChoiceQuestion q = new SimpleTextChoiceQuestion();

		shapeRenderer = new ShapeRenderer();
		//setScreen(new MainMenuScreen(this,1,1));
			setScreen(new BeginningScreen(this,1,1));
//	setScreen(new ProfileScreen(this,1,1));
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
