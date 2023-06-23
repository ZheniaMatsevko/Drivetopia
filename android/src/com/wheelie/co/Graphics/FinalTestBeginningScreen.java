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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FileService;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.finalTest;
import com.wheelie.co.levels20.level6;

import java.util.Locale;

/**екран перед початком фінального тесту**/
public class FinalTestBeginningScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;

    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;

    private Skin skin;

    private Skin skin2;
    private int userID;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private GlyphLayout layout;
    private TextButton backButton;

    private TextButton startButton;



    public FinalTestBeginningScreen(final Drivetopia app, int userID) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.app = app;
        this.userID = userID;
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
        font1=fontFactory.getFont(ukrLocale,1);
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,11);
      //  BitmapFont verySmall =fontFactory.getFont(ukrLocale,6);

        Skin skinForText = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinForText.add("font", font3);

        skinForText.load(Gdx.files.internal("skin-composer-ui.json"));


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));
        backButton = new TextButton("Назад",skin2);
        backButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        backButton.setPosition(GraphicConstants.centerX- backButton.getWidth()/2,300- backButton.getHeight()*1.2F);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MainMenuScreen(app,userID));
                dispose();
            }
        });

        stage.addActor(backButton);



        int[] p = getLevelsWithStateZero(app.getDatabase(),userID);
        String text = "життя бентежне";
        /**якщо всі практики пройдені, можна розпочати фінальний тест**/
        if(p.length==0) {

            startButton = new TextButton("Розпочати",skin2);
            startButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
            startButton.setPosition(GraphicConstants.centerX- backButton.getWidth()/2,backButton.getY() + backButton.getHeight()*1.2F);

            startButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    app.setScreen(new IntermediateScreen(app,new finalTest(app,userID),userID,0,false));
                    dispose();
                }
            });

            stage.addActor(startButton);

            /**якщо фінальний тест ще не було успішно пройдено**/
            if(getUserPassValue(app.getDatabase(),userID)==0) {
                text = "Вітаємо з проходженням усіх рівнів! Настав час перевірити ваші знання на фінальному тесті. Він буде охоплювати" +
                        " всі попередньо вивчені теми. За бажанням можете переглянути теорію ще раз перед тим, як продовжити.\n" +
                        "Якщо ж впевнені у своїх силах, бажаємо удачі!";
                int i = getUserFailures(app.getDatabase(), userID);
                if (i != 0) text += "\nВже було спроб скласти фінальний тест: " + i;
            }
            /**інакше**/
            else {

            }

        }

        /**якщо не всі практики пройдені, фінальний тест не можна розпочати**/
        else {
             text = "Ви не допущені до фінального тесту, поки не пройдете практику до всіх рівнів!\n" +
                    "Ви не завершили наступні практики: ";
            for (int i = 0; i < p.length; i++) {
                text = text + p[i];
                if (i == p.length - 1) text = text + ".";
                else text = text + ", ";
            }
        }
        Label label = new Label(text, skinForText);
        label.setWrap(true);
        label.setWidth(GraphicConstants.screenWidth-GraphicConstants.screenWidth*0.05F);
        label.setPosition(GraphicConstants.screenWidth*0.025F,GraphicConstants.centerY + label.getHeight()/2F);
        stage.addActor(label);








        //stage.addActor(PIB);




        sprite = new Sprite(new Texture(Gdx.files.internal("backsun.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "Final test");



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

    /**повертає кількість завалів фінального тесту юзером**/
    public int getUserFailures(SQLiteDatabase database, int userId) {
        String query = "SELECT failures FROM users WHERE id = ?";
        int failures = 0;

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            failures = cursor.getInt(cursor.getColumnIndexOrThrow("failures"));
        }

        if (cursor != null) {
            cursor.close();
        }

        return failures;
    }

/**повертає, чи пройдено вже фінальний тест юзером; passValue=0 ні, passValue=1 - так**/
    public int getUserPassValue(SQLiteDatabase database, int userId) {
        String query = "SELECT pass FROM users WHERE id = ?";
        int passValue = 0;

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            passValue = cursor.getInt(cursor.getColumnIndexOrThrow("pass"));
        }

        if (cursor != null) {
            cursor.close();
        }

        return passValue;
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
    public int[] getLevelsWithStateZero(SQLiteDatabase database, int userId) {
        String query = "SELECT levelNumb FROM scores WHERE userId = ? AND state = ? AND levelNumb != ?";
        String[] args = {String.valueOf(userId), "0", "16"};
        Cursor cursor = database.rawQuery(query, args);

        int[] levelNumbers;

        if (cursor.moveToFirst()) {
            levelNumbers = new int[cursor.getCount()];
            int index = 0;
            do {
                levelNumbers[index] = cursor.getInt(cursor.getColumnIndexOrThrow("levelNumb"));
                index++;
            } while (cursor.moveToNext());
        } else {
            levelNumbers = new int[0]; // No levels found with state 0
        }

        cursor.close();
        return levelNumbers;
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
            app.setScreen(new MainMenuScreen(app,userID));
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
