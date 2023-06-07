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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;

import java.util.Locale;

public class InteractiveTrafficScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private Image car;
    private TrafficLight light;
    private Texture red, yellow, green;
    private TextButton moveButton;
    private Skin skin;
    private int score;
    private float carSpeed = 700f;
    private Rectangle carBounds;
    private static final float TRAFFIC_INTERVAL = 5F;
    private boolean isMoving = false;
    private boolean isLightPassed = false;
    private Locale enLocale;
    private Locale ukrLocale;

    private FontFactory fontFactory;

    public InteractiveTrafficScreen(final Drivetopia app, int level, int score) {
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
        car = new Image(carTexture);
        car.setSize(Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.15f);
        car.setPosition(GraphicConstants.centerX - (car.getWidth()/2), 0);
        stage.addActor(car);


        // Drawing and positioning the button to move the car
        moveButton = new TextButton("Рух", skin);
        moveButton.setPosition(Gdx.graphics.getWidth()-moveButton.getWidth()*1.5f, moveButton.getHeight()*0.5f);
        // Setting up movement behaviour
        moveButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Moving the car when button is pressed
                isMoving = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Stopping movement when button is released
                isMoving = false;
            }
        });
        stage.addActor(moveButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(86/255f, 168/255f, 50/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);

        if(isMoving)
            moveCar();

        light.timer += delta;
        if(light.timer >= light.interval) {
            light.timer = 0f;
            light.updateTrafficLight(TRAFFIC_INTERVAL);
        }

        batch.draw(light.current, GraphicConstants.centerX+light.getCurrent().getWidth()*1.5f, GraphicConstants.centerY-light.getCurrent().getHeight()*0.75f);

        verifyCarPassedTraffic();

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void verifyCarPassedTraffic() {
        carBounds = new Rectangle(car.getX(), car.getY(), car.getImageWidth(), car.getImageHeight());

        if (carBounds.getY() >= light.bounds.getY() && light.getCurrent() == light.green && !isLightPassed) {
            showDialog("молодець!");
            isLightPassed = true;
        } else if (carBounds.getY() >= light.bounds.getY() && light.getCurrent() != light.green && !isLightPassed) {
            showDialog("шо ти робиш");
            isLightPassed = true;
        } else if (carBounds.getY() < light.bounds.getY()) {
            isLightPassed = false;
        }
    }

    private void moveCar() {
        car.setY(car.getY() + carSpeed * Gdx.graphics.getDeltaTime());
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

    Texture getCurrent() {
        return current;
    }
}