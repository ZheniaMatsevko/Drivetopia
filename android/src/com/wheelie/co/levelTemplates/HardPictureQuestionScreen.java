package com.wheelie.co.levelTemplates;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.Level;

import java.util.LinkedList;
import java.util.Locale;

public class HardPictureQuestionScreen extends ScreenAdapter {

    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private Level level;

    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;

    private Skin skinForDialog;


    private Skin skin2;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;

    private HardPictureQuestion question;



    public HardPictureQuestionScreen(final Drivetopia app, HardPictureQuestion question1, Level level, int userId) {

        question=question1;
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level =level;
        this.app = app;

        stage = new Stage(new ScreenViewport());


        batch = new SpriteBatch();



        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font1=fontFactory.getFont(ukrLocale,1);
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,2);

        BitmapFont fontDialog=fontFactory.getFont(ukrLocale,4);

        // Setting up the skin
        skinForDialog = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinForDialog.add("font", fontDialog);
        skinForDialog.load(Gdx.files.internal("skin-composer-ui.json"));


        Skin skinForText = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinForText.add("font", font3);

        skinForText.load(Gdx.files.internal("skin-composer-ui.json"));


        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));

        //ScrollPane scrollPane  = new ScrollPane(null);
        //scrollPane.setBounds(10, 10, GraphicConstants.screenWidth - 20, GraphicConstants.screenHeight - 20);
        Image image = new Image(new Texture(question.getBackgroundImagePath()));
        //image.setSize(2000f,1000);

        String text = question.getQuestion();

        Label label = new Label(text, skinForText);
        label.setWrap(true);


        Table table = new Table();
        table.defaults().pad(10,10,70,10);
        table.setPosition(10, GraphicConstants.rowHeight); // Replace x and y with the desired coordinates
        table.setSize(GraphicConstants.screenWidth-20, GraphicConstants.screenHeight - GraphicConstants.rowHeight*2);

        table.add(image).row();
        table.add(label).width(GraphicConstants.screenWidth-20).row();
        //scrollPane.setActor(table);
        //scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling

        stage.addActor(table);

        LinkedList<ImageButton> buttons = question.getObjects();
        for(ImageButton b : buttons){
            b.addListener(new ClickListener() {
                public void clicked(InputEvent event,float x, float y) {
                    level.failureScoreCount+=7;
                    if (level.failureScoreCount>=level.failureScore)app.setScreen(new IntermediateScreen(app,level,userId,2,true));
                    else {
                        if(level.getTasks().size()==level.currentTaskNumber()){
                            app.setScreen(new IntermediateScreen(app,level,userId,2,false));
                            dispose();
                        }
                        else {
                            level.increaseTaskCounter();
                            app.setScreen(level.tasks.get(level.currentTaskNumber()-1));
                            dispose();
                        }

                    }
                }
            });
            stage.addActor(b);
        }

        question.getCorrectAnswer().addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                /**Якщо це ще не останнє питання рівню, збільшує номер поточного завдання в рівні на 1 і відкриває наступне завдання**/
                if(level.getTasks().size()!=level.currentTaskNumber()) {
                    level.increaseTaskCounter();
                    level.currentscore+=7;
                    app.setScreen(level.tasks.get(level.currentTaskNumber()-1));
                    dispose();
                }
                /**Якщо це останнє питання, відкриває IntermediateScreen з результатами**/
                else {
                    level.currentscore+=7;
                    app.setScreen(new IntermediateScreen(app,level,userId,2,false));
                    dispose();
                }
            }
        });

        stage.addActor(question.getCorrectAnswer());

        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        layout = new GlyphLayout(font2, "Level "+ level.levelNumb);



    }
    private void showDialog(String message) {
        final MyDialog dialog = new MyDialog("результат", skinForDialog);
        dialog.setMessage(message);
        dialog.getButtonTable().add("ок");
        dialog.setColor(Color.BLACK);

        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialog.hide();
                return true;
            }
        });

        dialog.setVisible(true);
        dialog.show(stage);
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
        font2.draw(batch, layout, GraphicConstants.centerX-layout.width/2,GraphicConstants.rowHeight*7.2F);

        //fontFactory.getFont(ukrLocale).draw(batch, "Приав", Gdx.graphics.getWidth()/4-20,Gdx.graphics.getHeight()/4*3+200);



        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }
}
