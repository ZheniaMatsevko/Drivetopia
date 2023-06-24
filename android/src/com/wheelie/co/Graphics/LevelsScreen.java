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
import com.wheelie.co.Theory.BasicTheory;
import com.wheelie.co.Theory.TheoryScreen;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.level1;
import com.wheelie.co.levels20.level10;
import com.wheelie.co.levels20.level11;
import com.wheelie.co.levels20.level12;
import com.wheelie.co.levels20.level13;
import com.wheelie.co.levels20.level14;
import com.wheelie.co.levels20.level15;
import com.wheelie.co.levels20.level2;
import com.wheelie.co.levels20.level3;
import com.wheelie.co.levels20.level4;
import com.wheelie.co.levels20.level5;
import com.wheelie.co.levels20.level6;
import com.wheelie.co.levels20.level7;
import com.wheelie.co.levels20.level8;
import com.wheelie.co.levels20.level9;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import DBWorkH.DatabaseUtils;

/**
 * Даний клас реалізовує логіку та графічний інтерфейс екрану з рівнями
 */
public class LevelsScreen extends ScreenAdapter{

    Drivetopia app;

    private Skin skinDialog;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private BitmapFont fontDialog;

    private Skin skin;
    private int userID;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;

    private ImageButton mainMenuBtn;
    private ImageButton finalTestButton;

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
        fontDialog = fontFactory.getFont(ukrLocale, 11);




        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));


        skinDialog = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinDialog.add("font", fontDialog);

        skinDialog.load(Gdx.files.internal("skin-composer-ui.json"));

        final MyDialog dialogF = new MyDialog("", skinDialog);
        dialogF.setMessage("Ви вже пройшли\nфінальний тест\nна максимальний бал!\n" +
                "Відпочивайте :)");
        dialogF.setColor(Color.valueOf("#066c35"));


        Texture myTexture0 = new Texture(Gdx.files.internal("mainMenu.png"));
        TextureRegion myTextureRegion0 = new TextureRegion(myTexture0);
        TextureRegionDrawable myTexRegionDrawable0 = new TextureRegionDrawable(myTextureRegion0);

        mainMenuBtn = new ImageButton(myTexRegionDrawable0);
        mainMenuBtn.setSize(280,170);
        mainMenuBtn.setPosition(GraphicConstants.colWidth*7-mainMenuBtn.getWidth()/2, GraphicConstants.rowHeight*7.3F);
        mainMenuBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new MainMenuScreen(app,userID));
                dispose();
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


        levelsBtns = new LinkedList<>();






        for(int i=0;i<15;i++){
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
        levelsBtns.get(4).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2,GraphicConstants.rowHeight*4 +225);
        levelsBtns.get(5).setPosition(levelsBtns.get(0).getWidth()*2 +140,GraphicConstants.rowHeight*4 + 5);
        levelsBtns.get(6).setPosition(levelsBtns.get(1).getX()+280,levelsBtns.get(5).getY());
        levelsBtns.get(7).setPosition(levelsBtns.get(0).getX()+300,levelsBtns.get(5).getY());
        levelsBtns.get(8).setPosition(70,levelsBtns.get(7).getY()-10);
        //levelsBtns.get(8).setSize(280,210);
        levelsBtns.get(9).setPosition(-25,GraphicConstants.rowHeight*3 + 60);
        //levelsBtns.get(9).setSize(290,220);
        levelsBtns.get(10).setPosition(-30,GraphicConstants.rowHeight*2+110);
        ///levelsBtns.get(10).setSize(290,220);
        levelsBtns.get(11).setPosition(185,GraphicConstants.rowHeight*2+30);
        //levelsBtns.get(11).setSize(290,220);
        levelsBtns.get(12).setPosition(GraphicConstants.colWidth*3,GraphicConstants.rowHeight*2+30);
        //levelsBtns.get(12).setSize(290,220);
        levelsBtns.get(13).setPosition(GraphicConstants.colWidth*5-40,GraphicConstants.rowHeight*2+30);
        //levelsBtns.get(13).setSize(290,220);
        levelsBtns.get(14).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2+10,GraphicConstants.rowHeight*2-130);
       // levelsBtns.get(14).setSize(290,220);

        Texture finTexture = new Texture(Gdx.files.internal("finalTestButton.png"));
        TextureRegion finRegion = new TextureRegion(finTexture);
        final TextureRegionDrawable finRegionDrawable = new TextureRegionDrawable(finRegion);

        finalTestButton = new ImageButton(finRegionDrawable);

        finalTestButton.setPosition(GraphicConstants.centerX-(finalTestButton.getWidth()/2F),GraphicConstants.rowHeight/2);
        //finalTestButton.setScale(0.5f,0.5f);
        finalTestButton.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                if (DatabaseUtils.getUserStateForLevel(app.getDatabase(), userID, 16) != 2)
                    app.setScreen(new FinalTestBeginningScreen(app, userID));
                else {
                    dialogF.show(stage);
                }


                dispose();
            }
        });
        stage.addActor(finalTestButton);


        sprite = new Sprite(new Texture(Gdx.files.internal("levels1.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "DRIVETOPIA");


        final MyDialog dialog = new MyDialog("", skin);

        TextButton theoryBtn = new TextButton("Теорія", skin);
        TextButton practiceBtn = new TextButton("Практика", skin);

        BasicTheory.setApp(app);
        theoryBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
                app.setScreen(new TheoryScreen(app, BasicTheory.getSlides(dialog.getLevel()),userID));
                dispose();
            }
        });



       /**Запуск практики**/
        practiceBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(dialog.getLevel()==1) {
                    app.setScreen(new IntermediateScreen(app,new level1(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==2) {
                    app.setScreen(new IntermediateScreen(app,new level2(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==3) {
                    app.setScreen(new IntermediateScreen(app,new level3(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==4) {
                    app.setScreen(new IntermediateScreen(app,new level4(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==5) {
                    app.setScreen(new IntermediateScreen(app,new level5(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==6) {
                    app.setScreen(new IntermediateScreen(app,new level6(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==7) {
                    app.setScreen(new IntermediateScreen(app,new level7(app,userID),userID,0,false));
                    dispose();
                }

                if(dialog.getLevel()==8) {
                    app.setScreen(new IntermediateScreen(app,new level8(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==9) {
                    app.setScreen(new IntermediateScreen(app,new level9(app,userID),userID,0,false));
                    dispose();
                }

                if(dialog.getLevel()==10) {
                    app.setScreen(new IntermediateScreen(app,new level10(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==11) {
                    app.setScreen(new IntermediateScreen(app,new level11(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==12) {
                    app.setScreen(new IntermediateScreen(app,new level12(app,userID),userID,0,false));
               dispose();
                }
                if(dialog.getLevel()==13) {
                    app.setScreen(new IntermediateScreen(app,new level13(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==14) {
                    app.setScreen(new IntermediateScreen(app,new level14(app,userID),userID,0,false));
                    dispose();
                }
                if(dialog.getLevel()==15) {
                    app.setScreen(new IntermediateScreen(app,new level15(app,userID),userID,0,false));
                    dispose();
                }
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
                "а.перехресть, зупинок\n" +
                "б.пішохідних переходів");
        topics.add("Тема 13. Номерні,\nрозпізнавальні знаки,\nнаписи і позначення");
        topics.add("Тема 14. Дорожні знаки\n і розмітка");
        topics.add("Тема 15. Рух\n" +
                "а.житлова/пішохідна зона\n" +
                "б.по автомагістралях");

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

}
