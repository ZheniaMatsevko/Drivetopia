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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalTextInputQuestion;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class NormalTextInputQuestionScreen extends ScreenAdapter  {
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
    private int score;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;
    private TextButton exitButton;
    private NormalTextInputQuestion question;

    public NormalTextInputQuestionScreen(final Drivetopia app, NormalTextInputQuestion question1, Level level  , int userId) {

        question=question1;

        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level =level;
        this.app = app;
        this.score = score;

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

        Label label = new Label(question.getQuestion(), skinForText);
        label.setWrap(true);

        TextField answer = new TextField("", skinForText); // Create a new TextField
        //stage.addActor(answer); // Add the text field to the stage

        Table textTable = new Table();
        textTable.defaults().pad(10,10,60,10);
        textTable.setPosition(20, GraphicConstants.rowHeight*2); // Replace x and y with the desired coordinates
        textTable.setSize(GraphicConstants.screenWidth - 10, GraphicConstants.screenHeight - GraphicConstants.rowHeight*3.3f);

        textTable.add(label).width(GraphicConstants.screenWidth-20).row();
        textTable.add(answer).width(GraphicConstants.screenWidth/1.2f).height(GraphicConstants.rowHeight*0.5f).row();
        stage.addActor(textTable);


        exitButton = new TextButton("Готово",skin2);
        exitButton.setSize(GraphicConstants.colWidth*4,GraphicConstants.rowHeight*0.7F);
        exitButton.setPosition(GraphicConstants.centerX- exitButton.getWidth()/2,GraphicConstants.rowHeight);

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String newText = answer.getText();

                if (newText.toLowerCase().equals(question.getAnswer().toLowerCase())) {

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
                /**якщо відповідь неправильна**/
                else {
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

            }
        });

        stage.addActor(exitButton);



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
