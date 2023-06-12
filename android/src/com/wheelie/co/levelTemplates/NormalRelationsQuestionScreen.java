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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Graphics.LevelsScreen;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;
import com.wheelie.co.levelTemplates.questionTemplates.NormalFlashCardQuestion;
import com.wheelie.co.levelTemplates.questionTemplates.NormalRelationsQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

public class NormalRelationsQuestionScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

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
    private final GlyphLayout layoutUkr;
    private TextButton exitButton;
    private int chosenLevel;
    private NormalRelationsQuestion question;

    public NormalRelationsQuestionScreen(NormalRelationsQuestion question1,final Drivetopia app, String title, int level, int score, int chosenLevel) {

        question=question1;
        this.chosenLevel=chosenLevel;
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

        ArrayList<String> text = question.getTexts();

        Label label = new Label("1 - "+text.get(0), skinForText);
        label.setWrap(true);
        Label label1 = new Label("2 - " + text.get(1), skinForText);
        label1.setWrap(true);
        Label label2 = new Label("3 - " + text.get(2), skinForText);
        label2.setWrap(true);




        Table textTable = new Table();
        textTable.defaults().pad(10,10,40,10);
        textTable.setPosition(10, GraphicConstants.rowHeight*2.6f); // Replace x and y with the desired coordinates
        textTable.setSize(GraphicConstants.screenWidth, GraphicConstants.screenHeight - GraphicConstants.rowHeight*3.3f);

        textTable.add(label).width(GraphicConstants.screenWidth-20).row();
        textTable.add(label1).width(GraphicConstants.screenWidth-20).row();
        textTable.add(label2).width(GraphicConstants.screenWidth-20).row();


        stage.addActor(textTable);

        ArrayList<ImageButton> images = question.getImages();
        Collections.shuffle(images);


        Table imageTable = new Table();
        imageTable.defaults().pad(10,15,50,15);
        imageTable.setPosition(10, GraphicConstants.rowHeight*0.5f); // Replace x and y with the desired coordinates
        imageTable.setSize(GraphicConstants.screenWidth -10, GraphicConstants.rowHeight*4.5f);

        imageTable.add(images.get(0)).width(300).height(300);;
        imageTable.add(images.get(1)).width(300).height(300);;
        imageTable.add(images.get(2)).width(300).height(300);;
        imageTable.row();

        stage.addActor(imageTable);

        SelectBox<String> selectBox1 = new SelectBox<>(skinForText);
        Array<String> options1 = new Array<>();
        options1.add("1");
        options1.add("2");
        options1.add("3");
        selectBox1.setItems(options1);
        selectBox1.setWidth(200);

        SelectBox<String> selectBox2 = new SelectBox<>(skinForText);
        Array<String> options2 = new Array<>();
        options2.add("1");
        options2.add("2");
        options2.add("3");
        selectBox2.setItems(options2);
        selectBox2.setWidth(200);

        SelectBox<String> selectBox3 = new SelectBox<>(skinForText);
        Array<String> options3 = new Array<>();
        options3.add("1");
        options3.add("2");
        options3.add("3");
        selectBox3.setWidth(200);
        selectBox3.setItems(options3);

        Table selectsTable = new Table();
        selectsTable.defaults().pad(10,15,50,15);
        selectsTable.setPosition(10, GraphicConstants.rowHeight*0.5f); // Replace x and y with the desired coordinates
        selectsTable.setSize(GraphicConstants.screenWidth -10, GraphicConstants.rowHeight*2.4f);

        selectsTable.add(selectBox1).width(200).padRight(90);
        selectsTable.add(selectBox2).width(200).padRight(90);
        selectsTable.add(selectBox3).width(200);
        selectsTable.row();

        stage.addActor(selectsTable);


        //stage.addActor(label);


        exitButton = new TextButton("Готово",skin2);
        exitButton.setSize(GraphicConstants.colWidth*4,GraphicConstants.rowHeight*0.7F);
        exitButton.setPosition(GraphicConstants.centerX- exitButton.getWidth()/2,GraphicConstants.rowHeight/5);

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(!selectBox1.getSelected().equals(question.findAnswer(images.get(0)))
                || !selectBox2.getSelected().equals(question.findAnswer(images.get(1)))
                || !selectBox3.getSelected().equals(question.findAnswer(images.get(2)))){
                    showDialog("Подумай краще");
                }else
                    showDialog("Молодець");

            }
        });

        stage.addActor(exitButton);



        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "Level "+ level);
        layoutUkr = new GlyphLayout(font3, question.getQuestion());


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
        font3.draw(batch, layoutUkr, GraphicConstants.centerX-layoutUkr.width/2,GraphicConstants.rowHeight*6.7F);

        //fontFactory.getFont(ukrLocale).draw(batch, "Приав", Gdx.graphics.getWidth()/4-20,Gdx.graphics.getHeight()/4*3+200);



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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
