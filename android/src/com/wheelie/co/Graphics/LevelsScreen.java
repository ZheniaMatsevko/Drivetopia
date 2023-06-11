package com.wheelie.co.Graphics;

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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.level1;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class LevelsScreen extends ScreenAdapter implements InputProcessor {

    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private Skin skin;
    private int userID;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;

    private ImageButton mainMenuBtn;
    private List<ImageButton> levelsBtns;
    private Image soundBtn;

    private int soundState = 1;

    public LevelsScreen(final Drivetopia app, int userID) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.app = app;
        this.userID = userID;

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



        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        Texture myTexture0 = new Texture(Gdx.files.internal("mainMenu.png"));
        TextureRegion myTextureRegion0 = new TextureRegion(myTexture0);
        TextureRegionDrawable myTexRegionDrawable0 = new TextureRegionDrawable(myTextureRegion0);

        mainMenuBtn = new ImageButton(myTexRegionDrawable0);
        mainMenuBtn.setSize(280,170);
        mainMenuBtn.setPosition(0, GraphicConstants.rowHeight*7.3F);
        mainMenuBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new MainMenuScreen(app,2));
            }
        });
        stage.addActor(mainMenuBtn);


        /**звичайна іконка звуку**/
        Texture myTexture1 = new Texture(Gdx.files.internal("sound2.png"));
        TextureRegion myTextureRegion1 = new TextureRegion(myTexture1);
        final TextureRegionDrawable myTexRegionDrawable1 = new TextureRegionDrawable(myTextureRegion1);

        Texture myTexture2 = new Texture(Gdx.files.internal("sound2small.png"));
        TextureRegion myTextureRegion2 = new TextureRegion(myTexture2);
        final TextureRegionDrawable myTexRegionDrawable2 = new TextureRegionDrawable(myTextureRegion2);

        Texture myTexture3 = new Texture(Gdx.files.internal("sound2no.png"));
        TextureRegion myTextureRegion3 = new TextureRegion(myTexture3);
        final TextureRegionDrawable myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);


        if(soundState==1) {
            soundBtn = new Image(new Texture(Gdx.files.internal("sound2.png")));
        }
        else if(soundState==2){
            soundBtn = new Image(new Texture(Gdx.files.internal("sound2small.png")));

        }
        else if (soundState==3) {

            soundBtn = new Image(new Texture(Gdx.files.internal("sound2no.png")));

        }
        soundBtn.setSize(250,150);
        soundBtn.setPosition(GraphicConstants.colWidth*7-soundBtn.getWidth()/2, GraphicConstants.rowHeight*7.3F);
        soundState = 1;

        /**це чомусь не працює :( upd": працює**/
        soundBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                switch(soundState) {
                    case 1:
                        soundBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("sound2small.png")))));
                        soundState  = 2;
                        break;
                    case 2:
                        soundBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("sound2no.png")))));
                        soundState  = 3;
                        break;
                    default:
                        soundBtn.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("sound2.png")))));
                        soundState  = 1;
                        break;
                }
            }
        });



        stage.addActor(soundBtn);

        levelsBtns = new LinkedList<>();



        for(int i=0;i<20;i++){
            String path = "level" + (i+1) + ".png";
            Texture myTexture = new Texture(Gdx.files.internal(path));
            TextureRegion myTextureRegion = new TextureRegion(myTexture);
            final TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

            ImageButton levelBtn = new ImageButton(myTexRegionDrawable);
            levelBtn.setSize(310,240);
            stage.addActor(levelBtn);
            levelsBtns.add(levelBtn);
        }
        levelsBtns.get(0).setPosition(-10,GraphicConstants.rowHeight*6 - 55);
        levelsBtns.get(1).setPosition(levelsBtns.get(0).getWidth() - 65,GraphicConstants.rowHeight*6 - 55);
        levelsBtns.get(2).setPosition(levelsBtns.get(0).getWidth()*2 - 120,GraphicConstants.rowHeight*6 - 65);
        levelsBtns.get(3).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2-60,GraphicConstants.rowHeight*5+190);
        levelsBtns.get(4).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2,GraphicConstants.rowHeight*4 +215);
        levelsBtns.get(5).setPosition(levelsBtns.get(0).getWidth()*2 +140,GraphicConstants.rowHeight*4 + 5);
        levelsBtns.get(6).setPosition(levelsBtns.get(1).getX()+280,levelsBtns.get(5).getY());
        levelsBtns.get(7).setPosition(levelsBtns.get(0).getX()+300,levelsBtns.get(5).getY());
        levelsBtns.get(8).setPosition(70,levelsBtns.get(7).getY()-10);
        levelsBtns.get(8).setSize(280,210);
        levelsBtns.get(9).setPosition(-25,GraphicConstants.rowHeight*3 + 60);
        levelsBtns.get(9).setSize(290,220);
        levelsBtns.get(10).setPosition(-30,GraphicConstants.rowHeight*2+110);
        levelsBtns.get(10).setSize(290,220);
        levelsBtns.get(11).setPosition(185,GraphicConstants.rowHeight*2+30);
        levelsBtns.get(11).setSize(290,220);
        levelsBtns.get(12).setPosition(GraphicConstants.colWidth*3,GraphicConstants.rowHeight*2+30);
        levelsBtns.get(12).setSize(290,220);
        levelsBtns.get(13).setPosition(GraphicConstants.colWidth*5-40,GraphicConstants.rowHeight*2+30);
        levelsBtns.get(13).setSize(290,220);
        levelsBtns.get(14).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2+20,GraphicConstants.rowHeight*2-100);
        levelsBtns.get(14).setSize(290,220);

        levelsBtns.get(15).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2,GraphicConstants.rowHeight - 45);
        levelsBtns.get(15).setSize(290,220);
        levelsBtns.get(16).setPosition(levelsBtns.get(0).getWidth()*2+60,55);
        levelsBtns.get(16).setSize(290,220);
        levelsBtns.get(17).setPosition(levelsBtns.get(0).getWidth()*2 - 165,55);
        levelsBtns.get(17).setSize(290,220);
        levelsBtns.get(18).setSize(290,220);
        levelsBtns.get(18).setPosition(levelsBtns.get(0).getWidth() - 95,50);
        levelsBtns.get(19).setPosition(-20,50);
        levelsBtns.get(19).setSize(290,220);


        sprite = new Sprite(new Texture(Gdx.files.internal("levels1.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "DRIVETOPIA");


        final MyDialog dialog = new MyDialog("", skin);

        TextButton theoryBtn = new TextButton("Теорія", skin);
        TextButton practiceBtn = new TextButton("Практика", skin);

        theoryBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
                app.setScreen(new TheoryScreen(app,dialog.getMessage(),1,1,dialog.getLevel()));

            }
        });



       /**Запуск практики**/
        practiceBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(dialog.getLevel()==1) {
                    app.setScreen(new IntermediateScreen(app,new level1(app,userID),userID,0,false));
                }
                //    app.setScreen(new IntermediateScreen(app,new level1(app,userID),userID,0,false));
                   // app.setScreen(new SimpleTextChoiceQuestionScreen(app,1,new SimpleTextChoiceQuestion()));
            }
        });

        dialog.getButtonTable().add(theoryBtn).size(550f,120f).padRight(10f).padBottom(10f);
        dialog.getButtonTable().add(practiceBtn).size(500f,120f).padLeft(10f).padBottom(10f);
        dialog.setColor(Color.valueOf("#066c35"));

        dialog.setVisible(false);
        stage.addActor(dialog);
        final List<String> topics = new LinkedList<>();
        topics.add("Тема 1. Загальні положення");
        topics.add("Тема 2. Обов'язки та права\nа.водіїв   б.пасажирів");
        topics.add("Тема 3. Рух транспорту із \nспеціальними сигналами");
        topics.add("Тема 4. Регулювання \nдорожнього руху");
        topics.add("Тема 5. Попереджувальні\n сигнали");
        topics.add("Тема 6. Початок руху \nта зміна напрямку");
        topics.add("Тема 7. Розташування \nтранспорту на дорозі");
        topics.add("Тема 8. Швидкість руху");
        topics.add("Тема 9. Дистанція, інтервал,\n зустрічний роз'їзд");
        topics.add("Тема 10. Обгін");
        topics.add("Тема 11. Зупинка і стоянка");
        topics.add("Тема 12. Проїзд\n" +
                "а.перехресть\n" +
                "б.пішохідних переходів");
        topics.add("Тема 13. Проїзд ІІ\n" +
                "а.зупинок\n" +
                "б.через залізничні переїзди");
        topics.add("Тема 14. Користування\nсвітловими приладами");
        topics.add("Тема 15. Перевезення\n" +
                "а.пасажирів  " +
                "б.вантажу\n" +
                "в.інших транспортних засобів");
        topics.add("Тема 16. Номерні,\nрозпізнавальні знаки,\nнаписи і позначення");
        topics.add("Тема 17. Технічний стан\nтранспорту та обладнання");
        topics.add("Тема 18. Дорожні знаки\nі розмітка");
        topics.add("Тема 19. Рух\n" +
                "а.житлова/пішохідна зона\n" +
                "б.по автомагістралях");
        topics.add("Тема 20. Рух ІІ\n" +
                "а.по гірських дорогах\n" +
                "б.міжнародний");

        int counter=0;
        for(final String topic: topics){
            levelsBtns.get(counter).addListener(new ClickListener() {
                public void clicked(InputEvent event,float x, float y) {
                    dialog.setMessage(topic);
                    dialog.setLevel(topics.indexOf(topic)+1);
                    dialog.setVisible(true);
                    dialog.show(stage);

                }
            });
            counter++;
        }


    }

    /**
     * Малюємо головне меню
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        sprite.draw(batch);


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
        //   if(finalTestBtn.isPressed()) app.setScreen(new BeginningScreen(app,1,1));
        // if(hitActor==profileBtn.getImage()){
        //if(app.soundState) app.clicksound.play();
        //   System.out.println("Hit " + hitActor.getClass());
        //app.setScreen(new ProfileScreen(app,1,1));
        //} *else if(hitActor==helpButton.getImage()){
        //if(game.soundState)  game.clicksound.play();
        //  System.out.println("Hit " + hitActor.getClass());
        //game.setScreen(new HelpScreen(game,level,bonusScore));
        //}
        //else if(hitActor==settingsButton.getImage()){
        //if(game.soundState)  game.clicksound.play();
        //  System.out.println("Hit " + hitActor.getClass());
        //game.setScreen(new OptionsScreen(game,level,bonusScore,game.musicStage,game.soundState));
        //}*/
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
