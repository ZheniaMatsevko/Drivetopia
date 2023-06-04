package com.wheelie.co;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wheelie.co.Graphics.BeginningScreen;
import com.wheelie.co.Graphics.MainMenuScreen;
import com.wheelie.co.Graphics.ProfileScreen;
import com.wheelie.co.Graphics.TheoryScreen;

public class Drivetopia extends Game {

	private ShapeRenderer shapeRenderer;

	/**
	 * Запускаєм початковий екран
	 */
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		//setScreen(new MainMenuScreen(this,1,1));
			setScreen(new TheoryScreen(this,1,1));
//	setScreen(new ProfileScreen(this,1,1));

	}

	/**
	 * Видаляєм shapeRenderer
	 */
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

}
