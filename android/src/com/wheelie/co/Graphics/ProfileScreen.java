package com.wheelie.co.Graphics;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfileScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;

    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;

    private BitmapFont emailFont;

    private Skin skin;

    private Skin skin2;

    private Skin emailSkin;
    private int score;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;
    private TextButton backButton;

    private TextButton editButton;

    private Label PIB;

    private Label age;

    private Label failures;

    private Label points;

    private Label email;

    private Integer ageBD = 19;

    private String PIBBD = "Зубенко Михайло Петрович";

    private Integer failuresBD = 1;

    private Integer pointsBD = 0;

    private String emailBD = "zubenko@gmail.com";




    public ProfileScreen(final Drivetopia app, int userId) {
         final SQLiteDatabase db = app.getDatabase();
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level =level;
        this.app = app;
        this.score = userId;
        backgroundOffset=0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Zyana.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 135;
        parameter.borderWidth = 1.5F;

        parameter.color=Color.BLACK;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        stage = new Stage(new ScreenViewport());


        batch = new SpriteBatch();


        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font1=fontFactory.getFont(ukrLocale,1);
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,2);
        emailFont=fontFactory.getFont(enLocale,5);


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));

        emailSkin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        emailSkin.add("font", emailFont);

        emailSkin.load(Gdx.files.internal("skin-composer-ui.json"));


        editButton = new TextButton("Редагувати",skin2);
        editButton.setColor(Color.BLUE);
        editButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        editButton.setPosition(GraphicConstants.centerX-editButton.getWidth()/2,500-editButton.getHeight()*1.2F);

        editButton.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new MainMenuScreen(app,userId));
            }
        });

        stage.addActor(editButton);



        backButton = new TextButton("Назад",skin2);
        backButton.setColor(Color.BLUE);
        backButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        backButton.setPosition(GraphicConstants.centerX- backButton.getWidth()/2,300- backButton.getHeight()*1.2F);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new MainMenuScreen(app,userId));
            }
        });

        stage.addActor(backButton);









        String[] columns = {"name", "surname", "fathername", "failures", "score","dateOfBirth"};
        String selection = "id = ?";
        String arg= String.valueOf(userId);
        String[] selectionArgs = {arg};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }

            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            String fathername = cursor.getString(cursor.getColumnIndexOrThrow("fathername"));
            int failures = cursor.getInt(cursor.getColumnIndexOrThrow("failures"));
            int points = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
            String dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow("dateOfBirth"));


            String userInfo = name + " " + surname + " " + fathername;
            Log.d("User Info", userInfo);
            PIBBD = userInfo;
            failuresBD = failures;
            pointsBD = points;

            //вирахування віку
            ageBD=calculateAge(dateOfBirth);

            emailBD = getEmailById(userId,db);

        }







        if (cursor != null) {
            cursor.close();
        }

      //  app.getDatabase().close();






        PIB = new Label("ПІБ: \n" + PIBBD,skin);
        age = new Label(ageBD + " років",skin);
        points = new Label("Кількість очок (практика): " + pointsBD,skin);
        failures = new Label("Кількість перездач: " + failuresBD,skin);
        email = new Label(emailBD,emailSkin);

        PIB.setSize(40,25);
        PIB.setPosition(GraphicConstants.centerX- backButton.getWidth()/2 - 195,1500);
        email.setPosition(GraphicConstants.centerX- backButton.getWidth()/2 - 195,1250);
        age.setPosition(GraphicConstants.centerX- backButton.getWidth()/2 - 195,1150);
        points.setPosition(GraphicConstants.centerX- backButton.getWidth()/2 - 195,1000);
        failures.setPosition(GraphicConstants.centerX- backButton.getWidth()/2 - 195,900);

        stage.addActor(PIB);
        stage.addActor(age);
        stage.addActor(failures);
        stage.addActor(points);
        stage.addActor(email);




        sprite = new Sprite(new Texture(Gdx.files.internal("mountbgr.png")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "User profile");


    }

    /**
     * Малюємо головне меню
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        sprite.draw(batch);
        //font2.draw(batch, "DRIVETOPIA", Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/4*3);
        font2.draw(batch, layout, GraphicConstants.centerX-layout.width/2,GraphicConstants.rowHeight*6.8F);

        //fontFactory.getFont(ukrLocale).draw(batch, "Приав", Gdx.graphics.getWidth()/4-20,Gdx.graphics.getHeight()/4*3+200);



        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**Розраховує вік користувача**/
    public int calculateAge(String dateOfBirth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate;
        try {
            birthDate = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error occurred
        }

        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar currentCalendar = Calendar.getInstance();

        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        // Check if the current date is before the birth date in the current year
        // to account for cases where the user's birthday hasn't occurred yet
        if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }


    public String getEmailById(int userId, SQLiteDatabase db) {
        String email = "email";

        String[] columns = {"email"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("userInfo", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow("email");
            email = cursor.getString(columnIndex);
        }

        cursor.close();

        return email;
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

    /**
     * Відбувається дія при натисканні на екран лівою кнопкою миші
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 coord = stage.screenToStageCoordinates(new Vector2((float)screenX,(float) screenY));
        Actor hitActor = stage.hit(coord.x,coord.y,true);
        if(hitActor== backButton){
            System.out.println("Hit " + hitActor.getClass());
            app.setScreen(new MainMenuScreen(app,2));
        }
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

