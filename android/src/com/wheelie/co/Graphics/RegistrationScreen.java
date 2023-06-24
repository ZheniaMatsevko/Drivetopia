package com.wheelie.co.Graphics;

import android.content.ContentValues;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;

import java.util.Locale;

/**
 * Даний клас реалізовує логіку та графічний інтерфейс екрану реєстрації
 */
public class RegistrationScreen extends ScreenAdapter {

    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private BitmapFont fieldFont;

    private BitmapFont prettyFont;


    private Skin skin;

    private Skin fieldSkin;

    private Skin prettySkin;

    private int score;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;
    private TextButton loginBtn;

    private TextField emailField;
    private TextField passwordField;

    private TextField confirmField;

    private Label emailExists;

    private Label regComplete;

    private Label wrongConfirm;

    int mode = 1;


    public RegistrationScreen(final Drivetopia app) {
        final SQLiteDatabase db = app.getDatabase();
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.app = app;
        backgroundOffset = 0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Zyana.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 135;
        parameter.borderWidth = 1.5F;

        parameter.color = Color.BLACK;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        stage = new Stage(new ScreenViewport());


        batch = new SpriteBatch();


        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font2 = fontFactory.getFont(enLocale, 1);
        font3 = fontFactory.getFont(ukrLocale, 1);
        fieldFont = fontFactory.getFont(enLocale, 6);
        prettyFont = fontFactory.getFont(ukrLocale, 10);
        prettyFont.setColor(Color.RED);


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        fieldSkin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        fieldSkin.add("font", fieldFont);

        fieldSkin.load(Gdx.files.internal("skin-composer-ui.json"));

        prettySkin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        prettySkin.add("font", prettyFont);

        prettySkin.load(Gdx.files.internal("skin-composer-ui.json"));

        // Create the email input field
        emailField = new TextField("", fieldSkin);
        emailField.setMessageText("email");
        emailField.setSize(GraphicConstants.screenWidth * 0.7F, GraphicConstants.rowHeight * 0.55F);
        emailField.setPosition(GraphicConstants.centerX - emailField.getWidth() / 2, GraphicConstants.rowHeight * 5);

        // Create the password input field
        passwordField = new TextField("", fieldSkin);
        passwordField.setMessageText("password");
        passwordField.setSize(GraphicConstants.screenWidth * 0.7F, GraphicConstants.rowHeight * 0.55F);
        passwordField.setPosition(GraphicConstants.centerX - passwordField.getWidth() / 2, GraphicConstants.rowHeight * 4 + passwordField.getHeight() / 2);

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        // Create the confirmation field
        confirmField = new TextField("", fieldSkin);
        confirmField.setMessageText("confirm password");
        confirmField.setSize(GraphicConstants.screenWidth * 0.7F, GraphicConstants.rowHeight * 0.55F);
        confirmField.setPosition(GraphicConstants.centerX - passwordField.getWidth() / 2, GraphicConstants.rowHeight * 3 + passwordField.getHeight());

        confirmField.setPasswordMode(true);
        confirmField.setPasswordCharacter('*');


        emailExists = new Label("Користувача з таким email\nуже зареєстровано.", prettySkin);
        emailExists.setPosition(GraphicConstants.centerX - passwordField.getWidth() / 2, GraphicConstants.rowHeight * 3);
        emailExists.setVisible(false);

        regComplete = new Label("Вас успішно зареєстровано!", prettySkin);
        regComplete.setPosition(GraphicConstants.centerX - passwordField.getWidth() / 2, GraphicConstants.rowHeight * 3);
        regComplete.setVisible(false);

        wrongConfirm = new Label("Паролі не співпадають.", prettySkin);
        wrongConfirm.setPosition(GraphicConstants.centerX - passwordField.getWidth() / 2, GraphicConstants.rowHeight * 3);
        wrongConfirm.setVisible(false);


        loginBtn = new TextButton("Зареєструватися", skin);
        loginBtn.setSize(GraphicConstants.colWidth * 6, GraphicConstants.rowHeight * 0.7F);
        loginBtn.setPosition(GraphicConstants.centerX - loginBtn.getWidth() / 2, GraphicConstants.rowHeight - loginBtn.getHeight() / 2);

        loginBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {


                //pre-reg
                if(mode==1) {

                    if(!passwordField.getText().equals(confirmField.getText())) {
                        emailExists.setVisible(false);
                        wrongConfirm.setVisible(true);}

                    else {


                boolean ex = checkEmailExists(emailField.getText(),db);
                if(ex) {
                    wrongConfirm.setVisible(false);
                    emailExists.setVisible(true);
                }
                else {
                    long id = insertCredentials(emailField.getText(),passwordField.getText(),db);
                    wrongConfirm.setVisible(false);
                    emailExists.setVisible(false);
                    regComplete.setVisible(true);


                    mode=2;
                    loginBtn.setText("Увійти");
                    emailField.setDisabled(true);
                    passwordField.setDisabled(true);
                    confirmField.setDisabled(true);



                }

                    }

            }
                //post-reg
            else {
                   int i = AuthorizationScreen.getCredentialsId(emailField.getText(),passwordField.getText(),db);
                    app.setScreen(new MainMenuScreen(app,i));
                    dispose();

                }
            }



        });

        stage.addActor(loginBtn);

        stage.addActor(emailField);
        stage.addActor(passwordField);
        stage.addActor(emailExists);
        stage.addActor(confirmField);
        stage.addActor(regComplete);
        stage.addActor(wrongConfirm);


        sprite = new Sprite(new Texture(Gdx.files.internal("b13.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "DRIVETOPIA");


    }

    /**
     * Малюємо екран реєстрації
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        sprite.draw(batch);
        font2.draw(batch, layout, GraphicConstants.centerX - layout.width / 2, GraphicConstants.rowHeight * 6.8F);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    public boolean checkEmailExists(String email, SQLiteDatabase db) {
        String[] columns = {"email"};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("userInfo", columns, selection, selectionArgs, null, null, null);

        boolean exists = cursor.moveToFirst();

        cursor.close();

        return exists;
    }


    public long insertCredentials(String email, String password, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);

        long userId = db.insert("userInfo", null, values);


        ContentValues basicValues = new ContentValues();
        basicValues.put("id", userId);
        basicValues.put("name", "Новий");
        basicValues.put("surname", "Створений");
        basicValues.put("fathername", "Користувач");
        basicValues.put("score", 0);
        basicValues.put("dateOfBirth", "1990-01-01");
        basicValues.put("failures", 0);
        basicValues.put("pass", 0);

        db.insert("users",null,basicValues);



        /**створюємо рядки рівень-кількість балів для нового юзера**/
        for (int levelNumb = 1; levelNumb <= 16; levelNumb++) {
            ContentValues valuess = new ContentValues();
            valuess.put("userId", userId);
            valuess.put("levelNumb", levelNumb);
            valuess.put("score", 0);
            valuess.put("state", 0);

            long rowId = db.insert("scores", null, valuess);

            if (rowId == -1) {
                Log.d("scores insert failure","-1");// Failed to insert the row, handle the error if needed
            }
        }


 return userId;



    }
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
