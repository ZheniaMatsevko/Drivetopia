package com.wheelie.co.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wheelie.co.Graphics.GraphicConstants;

/**
 * Даний клас відповідає за можливість керування машиною в інтерактивних рівнях
 * **/
public class InteractiveCarController extends Group implements InputProcessor {
    private Image moveForward, moveBackward, turnLeft, turnRight;
    Table btnTable;
    private Texture buttonSprite;

    boolean isMovingForward, isMovingBackward, isTurningLeft, isTurningRight;

    public InteractiveCarController() {
        btnTable = new Table();
        // Using one sprite
        buttonSprite = new Texture(Gdx.files.internal("moveButton.png"));

        // Defining right button
        turnRight = new Image(new TextureRegion(buttonSprite));
        turnRight.setSize(buttonSprite.getWidth()*0.2f, buttonSprite.getHeight()*0.2f);
        turnRight.setPosition(GraphicConstants.screenWidth-turnRight.getWidth()*1.05f, turnRight.getHeight()*1.15f);
        turnRight.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Turning the car right when button is pressed
                isTurningRight = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Stopping turning right when button is released
                isTurningRight = false;
            }
        });

        // Defining left button
        turnLeft = new Image(new TextureRegion(buttonSprite));
        turnLeft.rotateBy(180);
        turnLeft.setSize(buttonSprite.getWidth()*0.2f, buttonSprite.getHeight()*0.2f);
        turnLeft.setPosition(GraphicConstants.screenWidth-turnRight.getWidth()*2.05f, turnRight.getHeight()*2.15f);
        turnLeft.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Turning the car left when button is pressed
                isTurningLeft = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Stopping turning left when button is released
                isTurningLeft = false;
            }
        });

        // Defining down button
        moveBackward = new Image(new TextureRegion(buttonSprite));
        moveBackward.rotateBy(270);
        moveBackward.setSize(buttonSprite.getWidth()*0.2f, buttonSprite.getHeight()*0.2f);
        moveBackward.setPosition(GraphicConstants.screenWidth-turnRight.getWidth()*2.05f, turnRight.getHeight()*1.15f);
        moveBackward.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Moving the car back when button is pressed
                isMovingBackward = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Stopping movement when button is released
                isMovingBackward = false;
            }
        });

        // Defining up button
        moveForward = new Image(new TextureRegion(buttonSprite));
        moveForward.rotateBy(90);
        moveForward.setSize(buttonSprite.getWidth()*0.2f, buttonSprite.getHeight()*0.2f);
        moveForward.setPosition(GraphicConstants.screenWidth-turnRight.getWidth()*1.05f, turnRight.getHeight()*2.15f);
        moveForward.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Moving car forward when button is pressed
                isMovingForward = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Stopping movement when button is released
                isMovingForward = false;
            }
        });

        addActor(turnRight);
        addActor(turnLeft);
        addActor(moveBackward);
        addActor(moveForward);
    }

    public boolean isMovingForward() {
        return isMovingForward;
    }

    public boolean isMovingBackward() {
        return isMovingBackward;
    }

    public boolean isTurningLeft() {
        return isTurningLeft;
    }

    public boolean isTurningRight() {
        return isTurningRight;
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
        return true;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
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