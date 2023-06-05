package com.wheelie.co.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;

import java.util.Locale;

public class TheoryScreen extends ScreenAdapter implements InputProcessor {
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
    private int score;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;
    private TextButton backButton;



    public TheoryScreen(final Drivetopia app, int level, int score) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level =level;
        this.app = app;
        this.score = score;

        stage = new Stage(new ScreenViewport());


        batch = new SpriteBatch();


        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font1=fontFactory.getFont(ukrLocale,1);
        font2=fontFactory.getFont(enLocale,5);
        font3=fontFactory.getFont(ukrLocale,2);
        BitmapFont verySmall =fontFactory.getFont(ukrLocale,6);

        Skin skinForText = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinForText.add("font", verySmall);

        skinForText.load(Gdx.files.internal("skin-composer-ui.json"));

        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        Skin skin1 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin1.add("font", font2);

        skin1.load(Gdx.files.internal("skin-composer-ui.json"));


        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));

        final ScrollPane scrollPane = new ScrollPane(null); // Set null as the content for now
        scrollPane.setBounds(10, 10, GraphicConstants.screenWidth - 20, GraphicConstants.screenHeight - 20);

        Image image = new Image(new Texture("theory1.jpg"));
        image.setSize(2000f,1000);

        String text = "Учасники дорожнього руху (водії) мають:\n" +
                "Знати й виконувати ПДР\n" +
                "Очікувати від інших виконання ПДР\n" +
                "Забезпечувати, що їх дії / бездіяльність не створять загрозу життю та здоров’ю громадян та інших водіїв\n" +
                "Водій, який створив такі умови, має негайно забезпечити безпеку дорожнього руху на цій ділянці дороги та вжити всіх можливих заходів до усунення перешкод\n" +
                "Якщо це неможливо, водій має попередити інших учасників дорожнього руху, повідомити уповноважений підрозділ Національної поліції, власника дороги або уповноважений ним орган\n" +
                "Водії зобов’язані бути особливо уважними велосипедистів, осіб, які рухаються в кріслах колісних, та пішоходів. Усі водії повинні бути особливо обережними до дітей, людей похилого віку та осіб з явними ознаками інвалідності\n" +
                "Використовувати дороги не за їх призначенням дозволяється з урахуванням вимог статей 36-38 Закону України «Про автомобільні дороги»:\n" +
                "Використання доріг поза їх основним призначенням контролюється місцевими владними органами (для місцевих доріг) або центральним органом влади (для державних доріг)\n" +
                "Для проведення спортивних змагань на дорогах потрібно отримати дозвіл від відповідних органів влади та поліції. Для державних доріг потрібен дозвіл від центрального органу влади з дорожнього господарства\n" +
                "Будівництво спортивних споруд, автозаправних станцій та інших об'єктів на дорогах можливе лише з дозволу державних органів та погодженням з поліцією\n" +
                "Дозволи на будівництво та експлуатацію об'єктів біля доріг видаватимуться органами дорожнього управління за плату і згодою поліції\n" +
                "При реконструкції доріг, будівництві нових ділянок доріг та споруд, слід дотримуватись вимог безпеки та проектів, затверджених відповідними органами\n";
        String title = "Тема 1. Загальні положення";

        Label titleLabel = new Label(title,skin);
        titleLabel.setWrap(true);

        Label label = new Label(text, skinForText);
        //label.setWidth(1000f); // Adjust the width according to your needs
        label.setWrap(true);

        /*Image image1 = new Image(new Texture("b13.jpg"));
        image1.setSize(300,300);
        Label label1= new Label("Your large amount of text gext 1 dkihawK CKLdaclj LD CALD clext 1 dkihawK CKLdaclj LD CALD clext 1 dkihawK CKLdaclj LD CALD cl\\next 1 dkihawK CKLdaclj LD CALD clext 1 dkihawK CKLdaclj LD CALD cloes here... fffffffffffffff ffffffffffffffffffff ffffffffffffffffffffffff ffffffff", skin1);
        //label1.setWidth(GraphicConstants.screenWidth-10); // Adjust the width according to your needs
        label1.setWrap(true);*/

        Table table = new Table();
        table.defaults().pad(10,10,200,10);

        table.add(image).width(GraphicConstants.screenWidth-20).row();
        table.add(titleLabel).width(GraphicConstants.screenWidth-20).row();
        table.add(label).width(GraphicConstants.screenWidth-20).row();
        //table.add(image1).row();
        //table.add(label1).width(GraphicConstants.screenWidth-20).row();
        //table.setFillParent(true);
        //table.pack();
        scrollPane.setActor(table);
        scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling
        stage.addActor(scrollPane);


        backButton = new TextButton("Готово",skin2);
        backButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        backButton.setPosition(GraphicConstants.centerX- backButton.getWidth()/2,300- backButton.getHeight()*1.2F);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new LevelsScreen(app,1,1));
            }
        });

        stage.addActor(backButton);



        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "Drivetopia");



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
        //font2.draw(batch, layout, GraphicConstants.centerX-layout.width/2,GraphicConstants.rowHeight*6.8F);

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

    /**
     * Відбувається дія при натисканні на екран лівою кнопкою миші
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 coord = stage.screenToStageCoordinates(new Vector2((float)screenX,(float) screenY));
        Actor hitActor = stage.hit(coord.x,coord.y,true);
        if(hitActor== backButton){
            System.out.println("Hit " + hitActor.getClass());
            app.setScreen(new MainMenuScreen(app,1,1));
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
