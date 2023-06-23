package com.wheelie.co.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Даний клас реалізовує логіку роботи актора-машини в усіх практичних завданнях інтерактивного типу
 * **/
public class Car extends Actor {
    private Polygon carBounds;
    private TextureRegion carTexture;

    private Image car;

    public Car(TextureRegion texture) {
        this.carTexture = texture;
        car = new Image(this.carTexture);

        Texture tempTexture = carTexture.getTexture();

        float[] vertices = {
                0, 0,                      // Bottom-left
                tempTexture.getWidth(), 0, // Bottom-right
                tempTexture.getWidth(), tempTexture.getHeight(),  // Top-right
                0, tempTexture.getHeight() // Top-left
        };

        this.carBounds = new Polygon(vertices);
        car.setOrigin(car.getWidth() / 2f, car.getHeight() / 2f);

        this.setPosition(0, 0);
        this.setSize(car.getWidth(), car.getHeight());

        this.setOrigin(this.getWidth() / 5f, this.getHeight() / 5f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(carTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public Polygon getCarBounds() {
        return carBounds;
    }

    public void setCarBounds(float[] vertices) {
        this.carBounds = new Polygon(vertices);
        this.carBounds.setPosition(getX(), getY());
        this.carBounds.setOrigin(getOriginX(), getOriginY());
        this.carBounds.setRotation(getRotation());
    }

    public void updateBounds() {
        float carWidth = getWidth();
        float carHeight = getHeight();
        float[] carVertices = {
                0, 0,
                carWidth, 0,
                carWidth, carHeight,
                0, carHeight
        };

        carBounds = new Polygon(carVertices);
        carBounds.setPosition(getX(), getY());
        carBounds.setRotation(getRotation());
        carBounds.setOrigin(getOriginX(), getOriginY());
    }

}
