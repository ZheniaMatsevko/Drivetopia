package com.wheelie.co.levels20;

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
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Graphics.LevelsScreen;
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


    private BitmapFont finalFont;




    private Skin skin;

    private Skin skin2;

    private Skin skinfinal;
    private int userId;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private TextButton nextButton;

    private int state;

  //  private Level theLevel;
    private Label label;

    private GlyphLayout layout = new GlyphLayout();

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

        //для для фінального тесту
        finalFont=fontFactory.getFont(ukrLocale,11);




        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font4);
        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);
        skin2.load(Gdx.files.internal("skin-composer-ui.json"));

        skinfinal = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinfinal.add("font", finalFont);
        skinfinal.load(Gdx.files.internal("skin-composer-ui.json"));


        nextButton = new TextButton("Назад",skin2);
        if(state==0) nextButton.setText("Почати");
        else if(state==1) nextButton.setText("Далі");
        else nextButton.setText("Завершити");
        nextButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        nextButton.setPosition(GraphicConstants.centerX- nextButton.getWidth()/2,300- nextButton.getHeight()*1.2F);

        nextButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                /**якщо це екран завершення практики, повертати в головне меню**/
            if(state!=0)  {

                /**оновлення очків юзера за цю практику якщо він набрав більше за попередній результат**/
               if(getUserScoreForLevel(app.getDatabase(),userId,level.levelNumb)<level.currentscore && !failure) {
                   updateUserScoreForLevel(app.getDatabase(),userId, level.levelNumb, level.currentscore);
               }
                /**якщо пройдено на максимум, заноситься стан 2 в базу даних цього рівня і користувача**/
               if(!failure && level.failureScoreCount==0) {
                   updateUserStateForLevel(app.getDatabase(),userId,level.levelNumb,2);
               }
               /**якщо не на максимум, то стан 1 || ЯКЩО СТАН УЖЕ 2, ТО НЕ ОНОВЛЮЄТЬСЯ**/
               else if(!failure) {
                   updateUserStateForLevel(app.getDatabase(),userId,level.levelNumb,1);

               }


                app.setScreen(new LevelsScreen(app,userId));
dispose();


            }
            /**якщо це початок практики, запускати перший таск рівня**/
            else {app.setScreen(level.getTasks().get(0));
            dispose();}
            //else  app.setScreen(new SimpleTextChoiceQuestionScreen(app,1,new SimpleTextChoiceQuestion()));
            }
        });

        stage.addActor(nextButton);


        if(level.levelNumb!=16) {

            label = new Label("Практика рівню " + level.levelNumb, skin);


            switch (state) {

                //початок практики
                case 0:

                    break;

                //після звичайної таски
                // upd 11.06: ми не використовуємо це.
                case 1:
                    if (failure) {
                        label = new Label("Провал!", skin);
                        label.setColor(Color.RED);
                    } else {
                        label = new Label("Правильно!", skin);

                    }
                    break;


                //після завершення практики
                case 2:
                    String message;
                    if (failure) message = "\nПрактику провалено!\nПеречитайте теорію.\n";
                    else
                        message = "\nПрактику успішно\nзавершено!\n" + level.currentscore + "/" + level.maximumScore;
                    label.setText(message);


                    break;

            }

        }
        /**якщо це фінальний тест**/
        else {
            layout = new GlyphLayout(font2, "Final test");

            String finaltext="";
            if(failure) {
                finaltext += "На жаль, ви не склали фінальний тест! Передивіться теми, з якими у вас виникли труднощі," +
                        " і поверніться пізніше.\n";
                finaltext += "\nПід час проходження ви втратили балів: " + level.failureScoreCount;
                //додавання failures++ для юзера
            }
            else {
                finaltext += "Вітаємо з успішним проходженням фінального тесту!\n";
                //якщо складено на максимум
                if(level.failureScoreCount==0) {
                    finaltext += "Ви скали його на максимальний бал і можете переглянути свій кубок у профілі.";
                    //записується state 2 до рівню 16 юзеру в таблицю scores
                }
                else {
                    finaltext += "До ідеального результату вам не вистачило " + level.failureScoreCount + " балів. Ви можете перескласти тест пізніше на більш високий бал.";
                     //записується failureScoreCount і state=1 юзеру в таблицю до рівню 16
                }
            }
            label = new Label(finaltext, skinfinal);
            label.setWrap(true);
            label.setWidth(GraphicConstants.screenWidth-GraphicConstants.screenWidth*0.05F);
            label.setPosition(GraphicConstants.screenWidth*0.025F,GraphicConstants.centerY + label.getHeight()/2F);
            if(failure) label.setPosition(GraphicConstants.screenWidth*0.025F,nextButton.getY() + GraphicConstants.rowHeight + 3F*nextButton.getHeight());

            stage.addActor(label);

        }

        if(level.levelNumb!=16)sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        else sprite = new Sprite(new Texture(Gdx.files.internal("backsun.jpg")));
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
        if(level.levelNumb==16)font2.draw(batch, layout, GraphicConstants.centerX-layout.width/2,GraphicConstants.rowHeight*6.8F);

        if(state==0 && level.levelNumb!=16) {
            font4.draw(batch, label.getText(), GraphicConstants.centerX - label.getWidth() / 2, Gdx.graphics.getHeight() / 4F * 2);
        }
          else if(level.levelNumb!=16) {
            font3.draw(batch, label.getText(), GraphicConstants.centerX - label.getWidth() /2, Gdx.graphics.getHeight() / 4F * 2);
        }
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    public void updateUserScoreForLevel(SQLiteDatabase database, int userId, int levelNumb, int newScore) {
        String query = "UPDATE scores SET score = ? WHERE userId = ? AND levelNumb = ?";
        Object[] args = {newScore, userId, levelNumb};

        database.execSQL(query, args);
    }

    public void updateUserStateForLevel(SQLiteDatabase database, int userId, int levelNumb, int newState) {
        String query = "SELECT state FROM scores WHERE userId = ? AND levelNumb = ?";
        String[] args = {String.valueOf(userId), String.valueOf(levelNumb)};
        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            int currentState = cursor.getInt(cursor.getColumnIndexOrThrow("state"));
            if (newState > currentState) {
                String updateQuery = "UPDATE scores SET state = ? WHERE userId = ? AND levelNumb = ?";
                Object[] updateArgs = {newState, userId, levelNumb};

                database.execSQL(updateQuery, updateArgs);
                Log.d("state of user " + userId + ", level " + levelNumb, "set to " + newState);
            }
        }

        cursor.close();
    }


    public static int getUserScoreForLevel(SQLiteDatabase database, int userId, int levelNumb) {
        String query = "SELECT score FROM scores WHERE userId = ? AND levelNumb = ?";
        String[] selectionArgs = {String.valueOf(userId), String.valueOf(levelNumb)};
        int score = 0;

        Cursor cursor = database.rawQuery(query, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            int scoreIndex = cursor.getColumnIndex("score");
            if (scoreIndex != -1) {
                score = cursor.getInt(scoreIndex);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return score;
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

