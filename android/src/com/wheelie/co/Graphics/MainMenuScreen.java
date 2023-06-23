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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.Tools.MyDialog;

import java.util.Locale;

import DBWorkH.DatabaseUtils;

public class MainMenuScreen extends ScreenAdapter implements InputProcessor {
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;
    private int level;

    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;

    private BitmapFont fontDialog;

    private Skin skin;

    private Skin skinDialog;
    private int userId =2;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;
    private Button startBtn;
    private ImageButton profileBtn;
    private Image soundBtn;
    private Button settingsBtn;
    private Button aboutBtn;
    private Button finalTestBtn;
    private Button exitBtn;

    private int soundState = 1;

    public MainMenuScreen(final Drivetopia app, int userId) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.app = app;
        this.userId = userId;
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
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,1);
        fontDialog=fontFactory.getFont(ukrLocale,11);


        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);
        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skinDialog = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skinDialog.add("font", fontDialog);

        skinDialog.load(Gdx.files.internal("skin-composer-ui.json"));

        startBtn = new TextButton("Уроки",skin);
        startBtn.setSize(GraphicConstants.colWidth*6,GraphicConstants.rowHeight*0.7F);
        startBtn.setPosition(GraphicConstants.centerX-startBtn.getWidth()/2,GraphicConstants.rowHeight*5);
        startBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new LevelsScreen(app,userId));
                dispose();
            }
        });
        stage.addActor(startBtn);

        aboutBtn = new TextButton("Про додаток",skin);
        aboutBtn.setSize(GraphicConstants.colWidth*6,GraphicConstants.rowHeight*0.7F);
        aboutBtn.setPosition(GraphicConstants.centerX-startBtn.getWidth()/2,startBtn.getY()-startBtn.getHeight()*1.2F);
        aboutBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new AboutScreen(app,userId));
                dispose();
            }
        });
        stage.addActor(aboutBtn);

        final MyDialog dialog = new MyDialog("", skinDialog);
        dialog.setMessage("Ви вже пройшли\nфінальний тест\nна максимальний бал!\n" +
                "Відпочивайте :)");
        dialog.setColor(Color.valueOf("#066c35"));


        finalTestBtn = new TextButton("Фінальний тест",skin);
        finalTestBtn.setSize(GraphicConstants.colWidth*6,GraphicConstants.rowHeight*0.7F);
        finalTestBtn.setPosition(GraphicConstants.centerX-startBtn.getWidth()/2,aboutBtn.getY()-startBtn.getHeight()*1.2F);
        finalTestBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                if(DatabaseUtils.getUserStateForLevel(app.getDatabase(),userId,16)!=2)
                app.setScreen(new FinalTestBeginningScreen(app,userId));
                else {
                  dialog.show(stage);
                }

       dispose();
            }
        });
        stage.addActor(finalTestBtn);


        exitBtn = new TextButton("Вийти",skin);
        exitBtn.setSize(GraphicConstants.colWidth*6,GraphicConstants.rowHeight*0.7F);
        exitBtn.setPosition(GraphicConstants.centerX-startBtn.getWidth()/2,finalTestBtn.getY()-startBtn.getHeight()*1.2F);
        exitBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new BeginningScreen(app));
                dispose();
            }
        });
        stage.addActor(exitBtn);

        Texture myTexture = new Texture(Gdx.files.internal("u5.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        profileBtn = new ImageButton(myTexRegionDrawable);
        profileBtn.setSize(250,150);
        profileBtn.setPosition(10, GraphicConstants.rowHeight*7.3F);
        profileBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new ProfileScreen(app,userId));
                dispose();
            }
        });
        stage.addActor(profileBtn);

        /**звичайна іконка звуку**/


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


        sprite = new Sprite(new Texture(Gdx.files.internal("b13.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "DRIVETOPIA");

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