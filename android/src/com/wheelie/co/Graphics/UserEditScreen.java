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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;

import java.util.Locale;

/**
 * Даний клас реалізовує логіку та графічний інтерфейс екрану редагування профілю користувача
 */
public class UserEditScreen extends ScreenAdapter{
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private BitmapFont font;
    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;
    private BitmapFont fieldFont;
    private Skin skin;
    private Skin skin2;
    private int score;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private TextButton backButton;
    private Skin fieldSkin;
    private TextField nameField, surnameField, fathernameField;
    private TextButton saveButton;
    private final GlyphLayout layout;
    private String name, surname, fathername;
    private MyDialog didEditWork;

    public UserEditScreen(final Drivetopia app, int userID) {
        final SQLiteDatabase db = app.getDatabase();
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.app = app;
        this.score = userID;
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
        font1 = fontFactory.getFont(ukrLocale, 1);
        font2 = fontFactory.getFont(enLocale, 1);
        font3 = fontFactory.getFont(ukrLocale, 2);
        fieldFont = fontFactory.getFont(enLocale, 6);


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));

        fieldSkin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        fieldSkin.add("font", fieldFont);

        fieldSkin.load(Gdx.files.internal("skin-composer-ui.json"));

        String[] columns = {"name", "surname", "fathername", "failures", "score", "dateOfBirth"};
        String selection = "id = ?";
        String arg = String.valueOf(userID);
        String[] selectionArgs = {arg};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Column", columnName);
            }
            name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            fathername = cursor.getString(cursor.getColumnIndexOrThrow("fathername"));
        }

        didEditWork = new MyDialog("Edit result", skin);
        //stage.addActor(didEditWork);

        nameField = new TextField(name, fieldSkin);
        nameField.setSize(GraphicConstants.screenWidth * 0.7F, GraphicConstants.rowHeight * 0.55F);
        nameField.setPosition(GraphicConstants.centerX - nameField.getWidth() / 2, GraphicConstants.rowHeight * 5);

        stage.addActor(nameField);

        surnameField = new TextField(surname, fieldSkin);
        surnameField.setSize(GraphicConstants.screenWidth * 0.7F, GraphicConstants.rowHeight * 0.55F);
        surnameField.setPosition(GraphicConstants.centerX - surnameField.getWidth() / 2, GraphicConstants.rowHeight * 4);

        stage.addActor(surnameField);

        fathernameField = new TextField(fathername, fieldSkin);
        fathernameField.setSize(GraphicConstants.screenWidth * 0.7F, GraphicConstants.rowHeight * 0.55F);
        fathernameField.setPosition(GraphicConstants.centerX - fathernameField.getWidth() / 2, GraphicConstants.rowHeight * 3);

        stage.addActor(fathernameField);

        saveButton = new TextButton("Зберегти", skin2);
        saveButton.setColor(Color.BLUE);
        saveButton.setSize(GraphicConstants.colWidth * 5, GraphicConstants.rowHeight * 0.7F);
        saveButton.setPosition(GraphicConstants.centerX - saveButton.getWidth() / 2, 500 - saveButton.getHeight() * 1.2F);

        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String newName = nameField.getText();
                String newSurname = surnameField.getText();
                String newFathername = fathernameField.getText();

                ContentValues values = new ContentValues();
                values.put("name", newName);
                values.put("surname", newSurname);
                values.put("fathername", newFathername);

                String selection = "id = ?";
                String arg = String.valueOf(userID);
                String[] selectionArgs = {arg};

                int rowsUpdated = db.update("users", values, selection, selectionArgs);

                if(rowsUpdated > 0) {
                    Log.d("it worked", "ayyyyy");
                    didEditWork.setMessage("Відредаговано!");
                    didEditWork.show(stage);
                } else {
                    Log.e("it didn't work", ":c");
                    didEditWork.setMessage("Щось пішло не так...");
                    didEditWork.show(stage);
                }
            }
        });
        stage.addActor(saveButton);

        backButton = new TextButton("Назад", skin2);
        backButton.setColor(Color.BLUE);
        backButton.setSize(GraphicConstants.colWidth * 5, GraphicConstants.rowHeight * 0.7F);
        backButton.setPosition(GraphicConstants.centerX - backButton.getWidth() / 2, 300 - backButton.getHeight() * 1.2F);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new ProfileScreen(app, userID));
                dispose();
            }
        });
        stage.addActor(backButton);

        sprite = new Sprite(new Texture(Gdx.files.internal("mountbgr.png")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "Edit profile");
    }

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

}