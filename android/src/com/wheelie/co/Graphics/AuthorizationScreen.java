package com.wheelie.co.Graphics;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;

import java.util.Locale;

public class AuthorizationScreen extends ScreenAdapter implements InputProcessor {

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

    private Label wrongCredMes;


    public AuthorizationScreen(final Drivetopia app) {
        final SQLiteDatabase db = app.getDatabase();
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.app = app;
        backgroundOffset=0;
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
        font3=fontFactory.getFont(ukrLocale,1);
        fieldFont=fontFactory.getFont(enLocale,6);
        prettyFont=fontFactory.getFont(ukrLocale,10);
        prettyFont.setColor(Color.RED);


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        fieldSkin=new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        fieldSkin.add("font", fieldFont);

        fieldSkin.load(Gdx.files.internal("skin-composer-ui.json"));

        prettySkin=new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        prettySkin.add("font", prettyFont);

        prettySkin.load(Gdx.files.internal("skin-composer-ui.json"));

        // Create the email input field
        emailField = new TextField("", fieldSkin);
        emailField.setMessageText("email");
        emailField.setSize(GraphicConstants.screenWidth*0.7F, GraphicConstants.rowHeight*0.55F);
        emailField.setPosition(GraphicConstants.centerX - emailField.getWidth()/2, GraphicConstants.rowHeight*5);

        // Create the password input field
        passwordField = new TextField("", fieldSkin);
        passwordField.setMessageText("password");
        passwordField.setSize(GraphicConstants.screenWidth*0.7F, GraphicConstants.rowHeight*0.55F);
        passwordField.setPosition(GraphicConstants.centerX - passwordField.getWidth()/2, GraphicConstants.rowHeight*4 + passwordField.getHeight()/2);

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');


        wrongCredMes = new Label("Невірні пароль або email.",prettySkin);
        wrongCredMes.setPosition(GraphicConstants.centerX - passwordField.getWidth()/2, GraphicConstants.rowHeight*4);
        wrongCredMes.setVisible(false);

        loginBtn = new TextButton("Увійти",skin);
        loginBtn.setSize(GraphicConstants.colWidth*6,GraphicConstants.rowHeight*0.7F);
        loginBtn.setPosition(GraphicConstants.centerX- loginBtn.getWidth()/2,GraphicConstants.rowHeight - loginBtn.getHeight()/2);

        loginBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
             int i = getCredentialsId(emailField.getText(),passwordField.getText(),db);
             if(i==-1) {
                 wrongCredMes.setVisible(true);
             }
             else {
                 wrongCredMes.setVisible(false);
                 app.setScreen(new MainMenuScreen(app,i));
             }
            }
        });

        stage.addActor(loginBtn);

        stage.addActor(emailField);
        stage.addActor(passwordField);
        stage.addActor(wrongCredMes);

        sprite = new Sprite(new Texture(Gdx.files.internal("b13.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "DRIVETOPIA");


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




    public boolean checkEmailExists(String email, SQLiteDatabase db) {
        String[] columns = {"email"};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("userInfo", columns, selection, selectionArgs, null, null, null);

        boolean exists = cursor.moveToFirst();

        cursor.close();

        return exists;
    }

    public static int getCredentialsId(String email, String password, SQLiteDatabase db) {
        String[] columns = {"id"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("userInfo", columns, selection, selectionArgs, null, null, null);

        int credentialsId = -1;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow("id");
            credentialsId = cursor.getInt(columnIndex);
        }

        cursor.close();

        return credentialsId;
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
