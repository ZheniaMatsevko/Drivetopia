package com.wheelie.co.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Car extends Actor {
    private Rectangle carBounds;
    private TextureRegion carTexture;
    private Texture tempTexture;
    private float carRotation;
    private Image car;

    public Car(TextureRegion texture) {
        this.carTexture = texture;
        this.car = new Image(this.carTexture);

        tempTexture = carTexture.getTexture();

        this.carBounds = new Rectangle(0, 0, tempTexture.getWidth(), tempTexture.getHeight());
        //this.carBounds = new Rectangle();
        this.car.setOrigin(this.car.getWidth() / 5f, this.car.getHeight() / 5f);
        this.car.setRotation(this.carRotation);

        this.setPosition(0, 0);
        this.setSize(this.car.getWidth(), this.car.getHeight());

        this.setOrigin(this.getWidth() / 5f, this.getHeight() / 5f);
    }

    public Car(TextureRegion texture, float x, float y, float rotation) {
        this.car = new Image(texture);
        this.car.setPosition(x, y);
        this.carRotation = rotation;
        this.carBounds = new Rectangle(x, y, this.car.getWidth(), this.car.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(carTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public Rectangle getCarBounds() {
        return carBounds;
    }

    public void setCarBounds(float x, float y, float width, float height) {
        this.carBounds = new Rectangle(x, y, width, height);
    }
}