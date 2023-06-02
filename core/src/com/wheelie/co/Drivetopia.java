package com.wheelie.co;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wheelie.co.Graphics.MainMenuScreen;

public class Drivetopia extends Game {

	private ShapeRenderer shapeRenderer;

	/**
	 * Запускаєм початковий екран
	 */
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		setScreen(new MainMenuScreen(this,1,0));
	}

	/**
	 * Видаляєм shapeRenderer
	 */
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

}
