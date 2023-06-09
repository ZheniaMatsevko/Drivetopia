package com.wheelie.co.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.Car;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.InteractiveCarController;
import com.wheelie.co.Tools.MyDialog;

import java.util.Locale;

public class InteractiveCrosswalkScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;
    private Car car;
    private Pedestrian justSomeDude;
    private boolean isDudePassed = false;
    private float roadLeftEdge, roadRightEdge;
    private Skin skin;
    private int score;
    private float carMovementSpeed = 700f;
    private float carSteerSpeed = 3f;
    private InteractiveCarController carControl;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;

    public InteractiveCrosswalkScreen(final Drivetopia app, int level, int score) {
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level = level;
        this.app = app;
        this.score = score;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Zyana.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 135;
        parameter.borderWidth = 1.5F;

        parameter.color= Color.BLACK;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();

        shapes = new ShapeRenderer();

        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,4);

        // Setting up the skin
        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);
        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        // Drawing and positioning the background
        sprite = new Sprite(new Texture(Gdx.files.internal("crosswalk.png")));
        sprite.setSize(sprite.getWidth()*2, sprite.getHeight()*2);
        sprite.setPosition(-GraphicConstants.centerX/1.5f, GraphicConstants.screenHeight/1.5f);
        sprite.setRotation(270);
        roadLeftEdge = 0;
        roadRightEdge = sprite.getRegionWidth()/1.5f;
        System.out.println(roadLeftEdge + " " + roadRightEdge);

        Texture guyTexture = new Texture(Gdx.files.internal("justSomeGuy.png"));
        TextureRegion guyReg = new TextureRegion(guyTexture);
        justSomeDude = new Pedestrian(guyReg, roadLeftEdge, roadRightEdge);
        float pedestrianWidth = Gdx.graphics.getWidth() / 7.5f;
        float pedestrianHeight = Gdx.graphics.getHeight() / 7.5f;
        justSomeDude.setSize(pedestrianWidth, pedestrianHeight);
        justSomeDude.setPosition(0, GraphicConstants.centerY * 1.05f);
        justSomeDude.setPedestrianBounds(justSomeDude.getX(), justSomeDude.getY(), pedestrianWidth, pedestrianHeight);

        stage.addActor(justSomeDude);

        Texture carTexture = new Texture(Gdx.files.internal("car.png"));
        TextureRegion carReg = new TextureRegion(carTexture);
        car = new Car(carReg);
        car.setPosition(GraphicConstants.centerX - (car.getWidth() / 5), 0);
        float carWidth = Gdx.graphics.getWidth() * 0.15f;
        float carHeight = Gdx.graphics.getHeight() * 0.15f;
        car.setSize(carWidth, carHeight);
        car.setCarBounds(car.getX(), car.getY(), carWidth, carHeight);

        stage.addActor(car);


        // Drawing and positioning the buttons to move the car
        carControl = new InteractiveCarController();
        stage.addActor(carControl);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(166/255f, 166/255f, 166/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);

        processCarMovement();

        checkPedestrianCollision();

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void checkPedestrianCollision() {
        justSomeDude.setPedestrianBounds(justSomeDude.getX(), justSomeDude.getY(), justSomeDude.getWidth(), justSomeDude.getHeight());

        if (car.getCarBounds().overlaps(justSomeDude.getPedestrianBounds()) && !isDudePassed) {
            showDialog("як ти смієш");
            isDudePassed = true;
        } else if (car.getY() >= justSomeDude.getY() + justSomeDude.getHeight() && !isDudePassed) {
            showDialog("молодець!");
            isDudePassed = true;
        }
    }

    private void processCarMovement() {
        float delta = Gdx.graphics.getDeltaTime();
        Vector2 direction = new Vector2(-MathUtils.sinDeg(car.getRotation()), MathUtils.cosDeg(car.getRotation())).nor();

        if (carControl.isMovingForward()) {
            car.setCarBounds(car.getX() + direction.x * carMovementSpeed * delta, car.getY() + direction.y * carMovementSpeed * delta, car.getWidth(), car.getHeight());
            car.setX(car.getX() + direction.x * carMovementSpeed * delta);
            car.setY(car.getY() + direction.y * carMovementSpeed * delta);
        } else if (carControl.isMovingBackward()) {
            car.setCarBounds(car.getX() - direction.x * (carMovementSpeed * 0.5f) * delta, car.getY() - direction.y * (carMovementSpeed * 0.5f) * delta, car.getWidth(), car.getHeight());
            car.setX(car.getX() - direction.x * (carMovementSpeed * 0.5f) * delta);
            car.setY(car.getY() - direction.y * (carMovementSpeed * 0.5f) * delta);
        }

        if(carControl.isTurningLeft()) {
            car.setRotation(car.getRotation() + carSteerSpeed);
        }
        if(carControl.isTurningRight()) {
            car.setRotation(car.getRotation() - carSteerSpeed);
        }
    }

    private void showDialog(String message) {
        final MyDialog dialog = new MyDialog("результат", skin);
        dialog.setMessage(message);
        dialog.getButtonTable().add("ок");
        dialog.setColor(Color.BLACK);

        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialog.setVisible(false);
                app.setScreen(new MainMenuScreen(app, 1, 1));
                return true;
            }
        });

        dialog.setVisible(true);
        dialog.show(stage);
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        return true;
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

class Pedestrian extends Actor {
    private boolean isGoingRight;
    private float moveSpeed;
    private float roadLeftEdge, roadRightEdge;
    private TextureRegion region;
    private Image img;
    private Rectangle bounds;
    private Texture tempTexture;

    public Pedestrian(TextureRegion pedestrianTexture, float leftEdge, float rightEdge) {
        this.region = pedestrianTexture;
        this.img = new Image(this.region);

        tempTexture = region.getTexture();
        this.bounds = new Rectangle(0, 0, tempTexture.getWidth(), tempTexture.getHeight());
        //this.bounds = new Rectangle();

        isGoingRight = true;
        moveSpeed = 200f;
        roadLeftEdge = leftEdge; roadRightEdge = rightEdge;

        this.setPosition(0, 0);
        this.setSize(this.img.getImageWidth(), this.img.getImageHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float movementAmount = moveSpeed * delta;
        if (isGoingRight) {
            moveBy(movementAmount, 0);
            if (getX() + getWidth() > roadRightEdge) {
                isGoingRight = false;
            }
        } else {
            moveBy(-movementAmount, 0);
            if (getX() < roadLeftEdge) {
                isGoingRight = true;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public Rectangle getPedestrianBounds() {
        return bounds;
    }

    public void setPedestrianBounds(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }
}