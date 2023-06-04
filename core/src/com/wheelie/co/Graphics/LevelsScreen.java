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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Tools.FontFactory;

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
    private int score;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;

    private ImageButton mainMenuBtn;
    private List<ImageButton> levelsBtns;
    private Image soundBtn;

    private int soundState = 1;

    public LevelsScreen(final Drivetopia app, int level, int score) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        fontFactory.initialize();

        this.level =level;
        this.app = app;
        this.score = score;

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
        font3=fontFactory.getFont(ukrLocale,1);



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
                app.setScreen(new MainMenuScreen(app,1,1));
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



        for(int i=0;i<9;i++){
            String path = "level" + (i+1) + ".png";
            Texture myTexture = new Texture(Gdx.files.internal(path));
            TextureRegion myTextureRegion = new TextureRegion(myTexture);
            final TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

            ImageButton levelBtn = new ImageButton(myTexRegionDrawable);
            levelBtn.setSize(310,240);
            stage.addActor(levelBtn);
            levelsBtns.add(levelBtn);
        }
        levelsBtns.get(0).setPosition(10,GraphicConstants.rowHeight*6 - 55);
        levelsBtns.get(1).setPosition(levelsBtns.get(0).getWidth() - 20,GraphicConstants.rowHeight*6 - 55);
        levelsBtns.get(2).setPosition(levelsBtns.get(0).getWidth()*2 - 40,GraphicConstants.rowHeight*6 - 65);
        levelsBtns.get(3).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2,GraphicConstants.rowHeight*5+80);
        levelsBtns.get(4).setPosition(GraphicConstants.screenWidth-GraphicConstants.colWidth*2,GraphicConstants.rowHeight*4 + 100);
        levelsBtns.get(5).setPosition(levelsBtns.get(0).getWidth()*2 - 40,GraphicConstants.rowHeight*4);
        levelsBtns.get(6).setPosition(levelsBtns.get(1).getX()+10,levelsBtns.get(5).getY());
        levelsBtns.get(7).setPosition(levelsBtns.get(0).getX()+10,levelsBtns.get(5).getY());
        levelsBtns.get(8).setPosition(-20,GraphicConstants.rowHeight*3+20);
        levelsBtns.get(8).setSize(290,220);



        sprite = new Sprite(new Texture(Gdx.files.internal("levels1.jpg")));
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
