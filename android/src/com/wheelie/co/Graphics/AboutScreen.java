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

import java.util.Locale;

/**Марина: поки що це просто копія екрану профіля,якщо воно так і залишено це значить що я ще не доробила**/
public class AboutScreen extends ScreenAdapter implements InputProcessor {
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
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private GlyphLayout layout;
    private TextButton backButton;

    private TextButton editButton;

    private Label PIB;

    private Label age;

    private Label failures;

    private Integer ageBD = 19;

    private String PIBBD = "Зубенко Михайло Петрович";

    private Integer failuresBD = 1;





    public AboutScreen(final Drivetopia app, int level, int score) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level =level;
        this.app = app;
        this.score = score;
        backgroundOffset=0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Zyana.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 135;
        parameter.borderWidth = 1.5F;

        parameter.color=Color.BLACK;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        stage = new Stage(new ScreenViewport());


        batch = new SpriteBatch();


        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font1=fontFactory.getFont(ukrLocale,1);
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,2);
        BitmapFont verySmall =fontFactory.getFont(ukrLocale,6);

        Skin skinForText = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinForText.add("font", verySmall);

        skinForText.load(Gdx.files.internal("skin-composer-ui.json"));


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));

        ScrollPane scrollPane = new ScrollPane(null);
        scrollPane.setBounds(10, 10, GraphicConstants.screenWidth - 20, GraphicConstants.rowHeight*6);

        String text = FileService.readFile("about.txt");

        Label label = new Label(text, skinForText);
        label.setWrap(true);



        Table table = new Table();
        table.defaults().pad(10,10,200,10);


        table.add(label).width(GraphicConstants.screenWidth-20).row();

        scrollPane.setActor(table);
        scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling
        stage.addActor(scrollPane);



        backButton = new TextButton("Назад",skin2);
        backButton.setSize(GraphicConstants.colWidth*5,GraphicConstants.rowHeight*0.7F);
        backButton.setPosition(GraphicConstants.centerX- backButton.getWidth()/2,300- backButton.getHeight()*1.2F);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new MainMenuScreen(app,1,1));
            }
        });

        stage.addActor(backButton);



        //stage.addActor(PIB);




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
        font2.draw(batch, layout, GraphicConstants.centerX-layout.width/2,GraphicConstants.rowHeight*6.8F);

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