package com.wheelie.co.Graphics;

import static DBWorkH.DatabaseUtils.getLevelsWithStateZero;
import static DBWorkH.DatabaseUtils.getUserFailures;
import static DBWorkH.DatabaseUtils.getUserPassValue;

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


/**
 * Даний клас реалізовує логіку та графічний інтерфейс екрану перед початком фінального тесту
 */
public class FinalTestBeginningScreen extends ScreenAdapter {
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
                    app.setScreen(new finalTest(app,userID).tasks.get(0));
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
              text = "Ви вже склали фінальний тест, але можете перепройти його на більш високий результат.";
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
        label.setPosition(GraphicConstants.screenWidth*0.025F,GraphicConstants.centerY);
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

    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

}
