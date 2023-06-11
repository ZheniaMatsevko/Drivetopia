package com.wheelie.co.levels20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Graphics.MainMenuScreen;
import com.wheelie.co.Tools.FontFactory;

import java.util.Locale;


/**
 * Цей екран використовується для початку практики і виведення результатів після закінчення практики.
 */
public class IntermediateScreen extends ScreenAdapter implements InputProcessor {



    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private Level level;

    private BitmapFont font;

    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;

    private BitmapFont font4;


    private Skin skin;

    private Skin skin2;
    private int userId;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private TextButton nextButton;

    private int state;

  //  private Level theLevel;
    private Label label;

    private boolean failure;


 //state
    // 0 - початок практики
    // 1 - проміжний рівень
    // 2 - кінцевий рівень

    //taskNumber - номер таски яка була перед цим екраном
    //потрібна щоб знати на яку таску з левела відсилати далі
      //0 - не було ще тасків
      //при stage = 2, параметр взагалі не впливає ні на що

    //failure - якщо це проміжний рівень, вказує чи попередню таску було пройдено чи ні

    public IntermediateScreen(final Drivetopia app, Level level, int userId, final int state, final boolean failure) {
        // Initialize FontFactory

        fontFactory = new FontFactory();
        fontFactory.initialize();

        //this.theLevel=new level1(app);
        this.failure=failure;
        this.level =level;
        this.app = app;
        this.userId = userId;
        this.state = state;
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

        //для повідомлень поменше
        font3=fontFactory.getFont(ukrLocale,2);

        //для великих назв
        font4=fontFactory.getFont(ukrLocale,3);



        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font4);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));




        nextButton = new TextButton("Назад",skin2);
        if(state==0) nextButton.setText("Почати");
        else if(state==1) nextButton.setText("Далі");
        else nextButton.setText("Завершити");
        nextButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        nextButton.setPosition(GraphicConstants.centerX- nextButton.getWidth()/2,300- nextButton.getHeight()*1.2F);

        nextButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                /**якщо це екран завершення практики, повертати в головне меню**/
            if(state!=0)    app.setScreen(new MainMenuScreen(app,userId));
            /**якщо це початок практики, запускати перший таск рівня**/
            else app.setScreen(level.getTasks().get(1));
            //else  app.setScreen(new SimpleTextChoiceQuestionScreen(app,1,new SimpleTextChoiceQuestion()));
            }
        });

        stage.addActor(nextButton);



        label = new Label("Практика рівню " + level.levelNumb,skin);



        switch(state) {

            //початок практики
            case 0:

                break;

            //після звичайної таски
            // upd 11.06: ми не використовуємо це.
            case 1:
            if (failure) {
               label = new Label("Провал!",skin);
                label.setColor(Color.RED);
            } else {
                label = new Label("Правильно!",skin);

            }
            break;


            //після завершення практики
            case 2:
                String message;
                if(failure) message="\nПрактику провалено!\nПеречитайте теорію.\n";
                else message="\nПрактику успішно\n завершено!\n" + level.currentscore + "/" + level.maximumScore;
             label.setText(message);


                break;

        }


        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);



    }

    /**
     * Малюємо головне меню
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        sprite.draw(batch);
        if(state==0) {
            font4.draw(batch, label.getText(), GraphicConstants.centerX - label.getWidth() / 2, Gdx.graphics.getHeight() / 4F * 2);
        }
          else {
            font3.draw(batch, label.getText(), GraphicConstants.centerX - label.getWidth() /2, Gdx.graphics.getHeight() / 4F * 2);
        }
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        if(hitActor== nextButton){
            System.out.println("Hit " + hitActor.getClass());
            app.setScreen(new MainMenuScreen(app,userId));
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

