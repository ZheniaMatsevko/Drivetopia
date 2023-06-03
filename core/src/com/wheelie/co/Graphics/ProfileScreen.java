package com.wheelie.co.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;

public class ProfileScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private BitmapFont font;
    private GlyphLayout layout;
    private Skin skin;

    public ProfileScreen(Drivetopia app) {
        this.app = app;
        batch = new SpriteBatch();

        stage = new Stage(new ScreenViewport());

        sprite = new Sprite(new Texture(Gdx.files.internal("b13.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



        // Create and position labels
        float labelY = Gdx.graphics.getHeight() * 0.75f;
        float labelSpacing = Gdx.graphics.getHeight() * 0.1f;
        float labelX = Gdx.graphics.getWidth() * 0.1f;

        Label nameLabel = new Label("Name", skin);
        nameLabel.setPosition(labelX, labelY);
        stage.addActor(nameLabel);

        Label surnameLabel = new Label("Surname", skin);
        surnameLabel.setPosition(labelX, labelY - labelSpacing);
        stage.addActor(surnameLabel);

        Label fathernameLabel = new Label("Fathername", skin);
        fathernameLabel.setPosition(labelX, labelY - (2 * labelSpacing));
        stage.addActor(fathernameLabel);

        Label dobLabel = new Label("Date of birth", skin);
        dobLabel.setPosition(labelX, labelY - (3 * labelSpacing));
        stage.addActor(dobLabel);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    // Implement other InputProcessor methods as needed

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}