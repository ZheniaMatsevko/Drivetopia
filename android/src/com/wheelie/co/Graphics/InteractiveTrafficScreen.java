package com.wheelie.co.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.Car;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.InteractiveCarController;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.Level;

import java.util.Locale;
/**
 * Даний клас реалізовує логіку та графічний інтерфейс екрану з практичним завданням (переїзд світлофору)
 */
public class InteractiveTrafficScreen extends ScreenAdapter{
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private Level level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private Car car;
    private TrafficLight light;
    private Texture red, yellow, green;
    private Skin skin;
    private int userID;
    private float carMovementSpeed = 700f;
    private float carSteerSpeed = 3f;
    private static final float TRAFFIC_INTERVAL = 5F;
    private InteractiveCarController carControl;
    private boolean isLightPassed = false;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;

    private boolean failed;


    public InteractiveTrafficScreen(final Drivetopia app, Level level, int userID) {
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level = level;
        this.app = app;
        this.userID = userID;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Zyana.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 135;
        parameter.borderWidth = 1.5F;

        parameter.color= Color.BLACK;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();

        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,4);

        // Setting up the skin
        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);
        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        // Setting up Traffic Light (idk what i'm doiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiing)
        green = new Texture(Gdx.files.internal("traffic-green.png"));
        yellow = new Texture(Gdx.files.internal("traffic-yellow.png"));
        red = new Texture(Gdx.files.internal("traffic-red.png"));
        light = new TrafficLight(green, yellow, red, TRAFFIC_INTERVAL);
        stage.addActor(light);
        light.setBounds(GraphicConstants.centerX+light.getCurrent().getWidth()*1.5f, GraphicConstants.centerY-light.getCurrent().getHeight()*0.75f);

        // Drawing and positioning the background
        sprite = new Sprite(new Texture(Gdx.files.internal("road.png")));
        sprite.setSize(Gdx.graphics.getWidth()*1.5f, Gdx.graphics.getHeight()*1.5f);
        sprite.setPosition((Gdx.graphics.getWidth()/2.85f)*(-1), 0);

        // Drawing and positioning the car
        Texture carTexture = new Texture(Gdx.files.internal("car.png"));
        TextureRegion carReg = new TextureRegion(carTexture);
        car = new Car(carReg);
        car.setPosition(GraphicConstants.centerX-(car.getWidth()/5), 0);
        car.setSize(Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.15f);

        stage.addActor(car);


        // Drawing and positioning the button to move the car
        carControl = new InteractiveCarController();
        stage.addActor(carControl);

        MyDialog instruct = new MyDialog("Завдання", skin);
        instruct.setMessage("Проїдь світлофор!");
        instruct.getButtonTable().add("ок");
        instruct.setColor(Color.BLACK);

        instruct.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                instruct.setVisible(false);
                return true;
            }
        });

        instruct.setVisible(true);
        instruct.show(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        Gdx.gl.glClearColor(86/255f, 168/255f, 50/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);

        processCarMovement();

        incrementTrafficTimer(delta);

        batch.draw(light.current, GraphicConstants.centerX+light.getCurrent().getWidth()*1.5f, GraphicConstants.centerY-light.getCurrent().getHeight()*0.75f);

        verifyCarPassedTraffic();

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void verifyCarPassedTraffic() {
        car.setCarBounds(car.getCarBounds().getVertices());

        if (car.getCarBounds().getY() >= light.getBounds().getY() && light.getCurrent() == light.green && !isLightPassed) {
            failed = false;
            showDialog("Молодець!");
            isLightPassed = true;
        } else if (car.getCarBounds().getY() >= light.getBounds().getY() && light.getCurrent() != light.green && !isLightPassed) {
            failed = true;
            showDialog("Неправильно!");
            isLightPassed = true;
        } else if (car.getCarBounds().getY() < light.getBounds().getY()) {
            isLightPassed = false;
        }
    }

    private void incrementTrafficTimer(float delta) {
        light.timer += delta;
        if(light.timer >= light.interval) {
            light.timer = 0f;
            light.updateTrafficLight(TRAFFIC_INTERVAL);
        }
    }

    private void processCarMovement() {
        float delta = Gdx.graphics.getDeltaTime();
        Vector2 direction = new Vector2(-MathUtils.sinDeg(car.getRotation()), MathUtils.cosDeg(car.getRotation())).nor();

        if(carControl.isMovingForward()) {
            car.setX(car.getX() + direction.x * carMovementSpeed * delta);
            car.setY(car.getY() + direction.y * carMovementSpeed * delta);
        } else if(carControl.isMovingBackward()) {
            car.setX(car.getX() - direction.x * (carMovementSpeed*0.5f) * delta);
            car.setY(car.getY() - direction.y * (carMovementSpeed*0.5f) * delta);
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

               if(failed) {
                   level.failureScoreCount+=10;
                   if (level.failureScoreCount>=level.failureScore)app.setScreen(new IntermediateScreen(app,level,userID,2,true));

               }
               else {
                   if(level.getTasks().size()!=level.currentTaskNumber()) {
                       level.increaseTaskCounter();
                       level.currentscore += 10;
                       app.setScreen(level.tasks.get(level.currentTaskNumber() - 1));
                   }
                   else {
                       level.currentscore += 10;
                       app.setScreen(new IntermediateScreen(app,level,userID,2,false));

                   }
               }



               return true;
           }
        });

        dialog.setVisible(true);
        dialog.show(stage);
    }

 }

/**
 * Даний клас реалізовує логіку роботи світлофора у даному практичному завданні
 */
class TrafficLight extends Actor {
    Texture green, yellow, red, current, previous;
    float interval, timer;
    boolean isGreen;
    Rectangle bounds;

    public TrafficLight(Texture green, Texture yellow, Texture red, float interval) {
        this.green = green;
        this.yellow = yellow;
        this.red = red;
        this.interval = interval;

        this.timer = 0f;
        this.isGreen = false;
        this.current = red;
        this.previous = null;
        this.bounds = new Rectangle(this.getX(), this.getY(), this.getCurrent().getWidth(), this.getCurrent().getHeight());
    }

    void updateTrafficLight(float interval) {
        if (current == null || current == red) {
            previous = current;
            current = yellow;
            this.interval = interval * 0.25f;
        } else if (current == yellow) {
            if (previous == red) {
                previous = current;
                current = green;
                this.interval = interval;
            } else if (previous == green) {
                previous = current;
                current = red;
                this.interval = interval;
            }
        } else if (current == green) {
            previous = current;
            current = yellow;
            this.interval = interval * 0.5f;
        }

    }
    void setBounds(float x, float y) {
        this.bounds = new Rectangle(x, y, this.getCurrent().getWidth(), this.getCurrent().getHeight());
    }

    Rectangle getBounds() {
        return bounds;
    }

    Texture getCurrent() {
        return current;
    }
}