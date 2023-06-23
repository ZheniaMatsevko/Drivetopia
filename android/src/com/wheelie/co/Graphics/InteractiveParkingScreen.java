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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
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
 * Даний клас реалізовує логіку та графічний інтерфейс екрану з практичним завданням (паркування)
 */
public class InteractiveParkingScreen extends ScreenAdapter {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private Level level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private Car userCar, car1, car2;
    private Image carParkSign, onlyCarsPark, parkSign;
    private Rectangle correctParkingArea, incorrectParkingArea;
    private Skin skin;
    private int userID;
    private float carMovementSpeed = 400f;
    private float carSteerSpeed = 60f;
    private boolean isCarCollided = false;
    private boolean isCarParked = false;
    private InteractiveCarController carControl;
    private Locale enLocale;
    private Locale ukrLocale;
    private ShapeRenderer shapeRenderer;
    private FontFactory fontFactory;
    private boolean failed;

    public InteractiveParkingScreen(final Drivetopia app, Level level, int userID) {
        fontFactory = new FontFactory();
        fontFactory.initialize();
        this.level = level;

        this.app = app;
        this.userID = userID;

        shapeRenderer = new ShapeRenderer();

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

        // Drawing and positioning the background
        sprite = new Sprite(new Texture(Gdx.files.internal("parking.png")));
        sprite.setSize(Gdx.graphics.getWidth()*0.9f, Gdx.graphics.getHeight()*1.1f);
        sprite.setPosition(GraphicConstants.colWidth/2.5f, 0);
        sprite.flip(false, true);

        // Drawing and positioning the cars
        Texture carTexture = new Texture(Gdx.files.internal("truck.png"));
        TextureRegion carReg = new TextureRegion(carTexture);
        userCar = new Car(carReg);
        userCar.setPosition(GraphicConstants.centerX-(userCar.getWidth()*3.9f), 0);
        float carWidth = Gdx.graphics.getWidth()*0.13f;
        float carHeight = Gdx.graphics.getHeight()*0.23f;
        float[] carVertices = {
                0, 0,
                carWidth, 0,
                carWidth, carHeight,
                0, carHeight
        };
        userCar.setSize(carWidth, carHeight);
        userCar.setOrigin(userCar.getWidth()/2f, userCar.getHeight()/2f);
        userCar.setCarBounds(carVertices);
        stage.addActor(userCar);

        carTexture = new Texture(Gdx.files.internal("car.png"));
        carReg = new TextureRegion(carTexture);
        car1 = new Car(carReg);
        carWidth = Gdx.graphics.getWidth()*0.17f;
        carHeight = Gdx.graphics.getHeight()*0.17f;
        carVertices = new float[]{
                0, 0,
                carWidth, 0,
                carWidth, carHeight,
                0, carHeight
        };
        car1.setSize(carWidth, carHeight);
        car1.setRotation(270);
        car1.setPosition(GraphicConstants.centerX*1.2f, car1.getHeight()*1.43f);
        car1.setCarBounds(carVertices);
        stage.addActor(car1);

        carTexture = new Texture(Gdx.files.internal("car3.png"));
        carReg = new TextureRegion(carTexture);
        car2 = new Car(carReg);
        carWidth = Gdx.graphics.getWidth()*0.17f;
        carHeight = Gdx.graphics.getHeight()*0.17f;
        carVertices = new float[]{
                0, 0,
                carWidth, 0,
                carWidth, carHeight,
                0, carHeight
        };
        car2.setSize(carWidth, carHeight);
        car2.setRotation(90);
        car2.setPosition(GraphicConstants.screenWidth*0.8f, car2.getHeight()*0.92f);
        car2.setCarBounds(carVertices);
        stage.addActor(car2);

        // Drawing and positioning the button to move the car
        carControl = new InteractiveCarController();
        stage.addActor(carControl);
       // Gdx.input.setInputProcessor(stage);

        onlyCarsPark = new Image(new Texture(Gdx.files.internal("onlyCarsPark.png")));
        onlyCarsPark.setSize(onlyCarsPark.getWidth()*1.2f, onlyCarsPark.getHeight()*1.2f);
        onlyCarsPark.setPosition(GraphicConstants.centerX, GraphicConstants.centerY/6.5f);
        stage.addActor(onlyCarsPark);

        carParkSign = new Image(new Texture(Gdx.files.internal("parkSign.png")));
        carParkSign.setSize(carParkSign.getWidth()*2.5f, carParkSign.getHeight()*2.5f);
        carParkSign.setPosition(GraphicConstants.centerX, GraphicConstants.centerY/6.5f+onlyCarsPark.getHeight());
        stage.addActor(carParkSign);

        parkSign = new Image(new Texture(Gdx.files.internal("parkSign.png")));
        parkSign.setSize(parkSign.getWidth()*2.5f, parkSign.getHeight()*2.5f);
        parkSign.setPosition(GraphicConstants.centerX*1.05f, GraphicConstants.centerY*1.1f);
        stage.addActor(parkSign);

        correctParkingArea = new Rectangle(GraphicConstants.centerX*1.05f, GraphicConstants.centerY*1.3f, userCar.getWidth()*1.25f, userCar.getHeight()*1.2f);
        incorrectParkingArea = new Rectangle(GraphicConstants.centerX*0.8f, GraphicConstants.centerY*0.76f, userCar.getHeight()*1.15f, userCar.getWidth()*1.2f);

        MyDialog instruct = new MyDialog("Завдання", skin);

        instruct.setMessage("Припаркуй вантажівку!");
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
        Gdx.gl.glClearColor(166/255f, 166/255f, 166/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);

        processCarMovement();
        processCarCollisions();
        processParking();

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(1, 0, 0, 0.33f));
        shapeRenderer.rect(correctParkingArea.x, correctParkingArea.y, correctParkingArea.width, correctParkingArea.height);
        shapeRenderer.rect(incorrectParkingArea.x, incorrectParkingArea.y, incorrectParkingArea.width, incorrectParkingArea.height);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void processCarMovement() {
        float delta = Gdx.graphics.getDeltaTime();
        Vector2 direction = new Vector2(-MathUtils.sinDeg(userCar.getRotation()), MathUtils.cosDeg(userCar.getRotation())).nor();

        if (carControl.isMovingForward()) {
            float[] vertices = userCar.getCarBounds().getVertices();
            for (int i = 0; i < vertices.length; i += 2) {
                vertices[i] += direction.x * carMovementSpeed * delta;
                vertices[i + 1] += direction.y * carMovementSpeed * delta;
            }
            userCar.setPosition(userCar.getX() + direction.x * carMovementSpeed * delta, userCar.getY() + direction.y * carMovementSpeed * delta);
            userCar.updateBounds();
        } else if (carControl.isMovingBackward()) {
            float[] vertices = userCar.getCarBounds().getVertices();
            for (int i = 0; i < vertices.length; i += 2) {
                vertices[i] -= direction.x * (carMovementSpeed * 0.5f) * delta;
                vertices[i + 1] -= direction.y * (carMovementSpeed * 0.5f) * delta;
            }
            userCar.setPosition(userCar.getX() - direction.x * (carMovementSpeed * 0.5f) * delta, userCar.getY() - direction.y * (carMovementSpeed * 0.5f) * delta);
            userCar.updateBounds();
        }

        if(carControl.isTurningLeft()) {
            userCar.setRotation(userCar.getRotation() + carSteerSpeed * delta);
            userCar.updateBounds();
        }
        if(carControl.isTurningRight()) {
            userCar.setRotation(userCar.getRotation() - carSteerSpeed * delta);
            userCar.updateBounds();
        }
    }

    private void processCarCollisions() {
        Polygon userCarBounds = userCar.getCarBounds();
        Polygon car1Bounds = car1.getCarBounds();
        Polygon car2Bounds = car2.getCarBounds();

        if ((Intersector.overlapConvexPolygons(userCarBounds, car1Bounds) || Intersector.overlapConvexPolygons(userCarBounds, car2Bounds)) && !isCarCollided) {
            isCarCollided = true;
            failed = true;
            showDialog("Задів машину!");
        }
    }

    private void processParking() {
        Polygon carHitbox = userCar.getCarBounds();

        if(isPolygonFullyInsideRectangle(carHitbox, correctParkingArea) && !isCarParked) {
            isCarParked = true;
            failed = false;
            showDialog("Молодець!");
        } else if(isPolygonFullyInsideRectangle(carHitbox, incorrectParkingArea) && !isCarParked) {
            isCarParked = true;
            failed = true;
            showDialog("Не туди!");
        }
    }

    // Method to check if a polygon is fully inside a rectangle
    private boolean isPolygonFullyInsideRectangle(Polygon polygon, Rectangle rectangle) {
        // Check if the polygon and rectangle overlap
        if (Intersector.overlapConvexPolygons(polygon, convertToPolygon(rectangle))) {
            // Check if all vertices of the polygon are contained within the rectangle
            float[] vertices = polygon.getTransformedVertices();
            for (int i = 0; i < vertices.length; i += 2) {
                float x = vertices[i];
                float y = vertices[i + 1];
                if (!rectangle.contains(x, y)) {
                    return false; // At least one vertex is outside the rectangle
                }
            }

            return true; // All vertices are inside the rectangle
        } else {
            return false; // Polygon and rectangle do not overlap
        }
    }

    private Polygon convertToPolygon(Rectangle rectangle) {
        Pool<Polygon> polygonPool = Pools.get(Polygon.class);
        Polygon polygon = polygonPool.obtain();
        polygon.setVertices(new float[]{
                rectangle.x, rectangle.y,
                rectangle.x + rectangle.width, rectangle.y,
                rectangle.x + rectangle.width, rectangle.y + rectangle.height,
                rectangle.x, rectangle.y + rectangle.height
        });
        return polygon;
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

             //   app.setScreen(new MainMenuScreen(app, userID));
                dispose();
                return true;
            }
        });

        dialog.setVisible(true);
        dialog.show(stage);
    }

}
