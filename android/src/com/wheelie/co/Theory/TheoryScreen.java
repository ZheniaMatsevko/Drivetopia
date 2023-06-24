package com.wheelie.co.Theory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Graphics.LevelsScreen;
import com.wheelie.co.Tools.FileService;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.levels20.Level;

import java.util.LinkedList;
import java.util.Locale;

/**
 * Даний клас реалізовує графічний інтерфейс та логіку екрану з теорією
 * **/
public class TheoryScreen extends ScreenAdapter{
    Drivetopia app;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;

    private BitmapFont font;

    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font3;

    private Skin skin;

    private Skin skin2;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;

    private final GlyphLayout layout;
    private int currentSlide;
    private int userID;



    public TheoryScreen(final Drivetopia app, LinkedList<Slide> slides, int userId) {
        // Initialize FontFactory
        fontFactory = new FontFactory();
        userID=userId;
        fontFactory.initialize();

        this.app = app;
        currentSlide=0;

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


        ScrollPane scrollPane  = new ScrollPane(null);
        scrollPane.setBounds(10, GraphicConstants.rowHeight + 5, GraphicConstants.screenWidth - 20, GraphicConstants.screenHeight/1.5f);

        Image image = new Image(new Texture(slides.get(currentSlide).getImage()));
        //image.setSize(2000f,1000);


        Label label = new Label(slides.get(currentSlide).getText(), skinForText);
        label.setWrap(true);

        Table table = new Table();
        table.defaults().pad(10,10,40,10);

        //table.add(image).height(GraphicConstants.rowHeight*2).width(GraphicConstants.screenWidth-20).row();
        //table.add(label).width(GraphicConstants.screenWidth-20).row();

        table.add(image).row();
        table.add(label).width(GraphicConstants.screenWidth-20).row();


        scrollPane.setActor(table);
        scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling

        stage.addActor(scrollPane);



        Texture myTexture0 = new Texture(Gdx.files.internal("levels.png"));
        TextureRegion myTextureRegion0 = new TextureRegion(myTexture0);
        TextureRegionDrawable myTexRegionDrawable0 = new TextureRegionDrawable(myTextureRegion0);

        ImageButton backToLevelsBtn = new ImageButton(myTexRegionDrawable0);
        backToLevelsBtn.setSize(280,170);
        backToLevelsBtn.setPosition(0, GraphicConstants.rowHeight*7.3F);
        backToLevelsBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                app.setScreen(new LevelsScreen(app,userID));
                dispose();
            }
        });
        stage.addActor(backToLevelsBtn);






        /**
         * кнопка вперед
         */

        Texture forwardTexture = new Texture(Gdx.files.internal("forward.png"));
        TextureRegion forwardTextureRegion = new TextureRegion(forwardTexture);
        TextureRegionDrawable forwardTexRegionDrawable = new TextureRegionDrawable(forwardTextureRegion);
        ImageButton forward = new ImageButton(forwardTexRegionDrawable);
        forward.setSize(400,300);
        forward.setPosition(Gdx.graphics.getWidth()-forward.getWidth()-15,0);
        forward.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                if(currentSlide<slides.size()-1){
                    image.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(slides.get(++currentSlide).getImage())))));
                    label.setText(slides.get(currentSlide).getText());
                    layout.setText(font3,currentSlide +1 + "/" + slides.size());
                }
             }
        });
        stage.addActor(forward);

        /**
         * кнопка назад
         */

        Texture backTexture = new Texture(Gdx.files.internal("back.png"));
        TextureRegion backTextureRegion = new TextureRegion(backTexture);
        TextureRegionDrawable backTexRegionDrawable = new TextureRegionDrawable(backTextureRegion);
        ImageButton back = new ImageButton(backTexRegionDrawable);
        back.setSize(400,300);
        back.setPosition(0,0);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event,float x, float y) {
                if(currentSlide>0){
                    image.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(slides.get(--currentSlide).getImage())))));
                    label.setText(slides.get(currentSlide).getText());
                    layout.setText(font3,currentSlide + 1 + "/" + slides.size());

                }
            }
        });
        stage.addActor(back);

        Label pagesLabel = new Label("1/" + slides.size(),skin);
        //pagesLabel.setPosition(Gra);
        layout = new GlyphLayout(font3, "1/" + slides.size());


        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




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

        font3.draw(batch, layout, GraphicConstants.centerX-layout.width/2,GraphicConstants.rowHeight*0.6f);


        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    public void dispose() {
        stage.dispose();
        batch.dispose();
    }


}


