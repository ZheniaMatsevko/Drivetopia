package com.wheelie.co.Tools;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class InteractiveCarController implements InputProcessor {
    private float steerAngle, acceleration;
    private ImageButton moveForward, moveBackward, turnLeft, turnRight;
    boolean isMovingForward, isMovingBackward, isTurningLeft, isTurningRight;

    public InteractiveCarController() {
        steerAngle = 0f;
        acceleration = 0f;
    }

    public float getSteerAngle() {
        return steerAngle;
    }

    public float getAcceleration() {
        return acceleration;
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
        return true;
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