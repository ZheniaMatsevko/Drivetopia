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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Graphics.LevelsScreen;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levelTemplates.questionTemplates.HardPictureQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.Level;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Даний клас реалізовує графічний інтерфейс та логіку екрану завдання з флешкартами
 * **/
public class NormalFlashCardQuestionScreen extends ScreenAdapter  {
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
    //private final GlyphLayout layoutUkr;
    private NormalFlashCardQuestion question;

    public NormalFlashCardQuestionScreen(final Drivetopia app,NormalFlashCardQuestion question1,  Level level, int userId) {

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

        String text = question.getQuestion();


        LinkedList<ImageButton> buttons = question.getObjects();
        for(int i=0;i<3;i++){
            buttons.get(i).addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                        level.failureScoreCount+=5;
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
                        // answerButtons[buttonIndex].setText("Ну ти лошара");

                    }
            });
        }

        question.getCorrectAnswer().addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {

                    /**Якщо це ще не останнє питання рівню, збільшує номер поточного завдання в рівні на 1 і відкриває наступне завдання**/
                    if(level.getTasks().size()!=level.currentTaskNumber()) {
                        level.increaseTaskCounter();
                        level.currentscore+=5;
                        app.setScreen(level.tasks.get(level.currentTaskNumber()-1));
dispose();
                    }
                    /**Якщо це останнє питання, відкриває IntermediateScreen з результатами**/
                    else {
                        level.currentscore+=5;
                        app.setScreen(new IntermediateScreen(app,level,userId,2,false));
dispose();
                    }

            }
        });

        Collections.shuffle(buttons);

        Table table = new Table();
        table.defaults().pad(10,10,150,10);
        table.setPosition(10, 10); // Replace x and y with the desired coordinates
        table.setSize(GraphicConstants.screenWidth, GraphicConstants.screenHeight);


        if(question.getLevel()==6){
            table.add(buttons.get(0)).width(600);
            table.add(buttons.get(1)).width(600);
            table.row();
            table.add(buttons.get(2)).width(600);
            table.add(buttons.get(3)).width(600);
            table.row();
        }else{
            table.add(buttons.get(0));
            table.add(buttons.get(1));
            table.row();
            table.add(buttons.get(2));
            table.add(buttons.get(3));
            table.row();
        }

        //table.add(label).padLeft(50f).padTop(20).row();


        stage.addActor(table);

        Label taskLabel = new Label(question.getQuestion(),skinForText);
        taskLabel.setPosition(30,GraphicConstants.rowHeight*0.4f);
        taskLabel.setSize(GraphicConstants.screenWidth-50,GraphicConstants.rowHeight*1.5f);
        taskLabel.setWrap(true);




        stage.addActor(taskLabel);



        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        layout = new GlyphLayout(font2, "Level "+ level.levelNumb);
        //layoutUkr = new GlyphLayout(font3, question.getQuestion());


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
        //font3.draw(batch, layoutUkr, GraphicConstants.centerX-layoutUkr.width/2,GraphicConstants.rowHeight*1.9F);

        //fontFactory.getFont(ukrLocale).draw(batch, "Приав", Gdx.graphics.getWidth()/4-20,Gdx.graphics.getHeight()/4*3+200);



        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

}
