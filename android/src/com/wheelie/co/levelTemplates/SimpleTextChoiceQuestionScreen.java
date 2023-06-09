package com.wheelie.co.levelTemplates;
import android.database.sqlite.SQLiteDatabase;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wheelie.co.Drivetopia;
import com.wheelie.co.Graphics.GraphicConstants;
import com.wheelie.co.Tools.FontFactory;
import com.wheelie.co.levelTemplates.questionTemplates.SimpleTextChoiceQuestion;
import com.wheelie.co.levels20.IntermediateScreen;
import com.wheelie.co.levels20.Level;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Даний клас реалізовує графічний інтерфейс та логіку екрану тестового завдання
 * **/
public class SimpleTextChoiceQuestionScreen extends ScreenAdapter  {

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
    private int score;
    private int backgroundOffset;
    private Locale enLocale;
    private Locale ukrLocale;
    private FontFactory fontFactory;
    private final GlyphLayout layout;

    private Label textQuestion;

    private Level level;

    private int userId;


/*    private TextButton answer1;

    private TextButton answer2;
    private TextButton answer3;

    private TextButton answer4;*/

    private TextButton[] answerButtons;
    SimpleTextChoiceQuestion question;

    public SimpleTextChoiceQuestionScreen(final Drivetopia app, final SimpleTextChoiceQuestion question, Level level, int userId) {
        this.userId = userId;
        this.question = question;
        //System.out.println(question.getText());

        LinkedList<String> wrA = question.generateWrongAnswers(question.getWrongAnswers());
        wrA.add(question.getCorrectAnswer());
        Collections.shuffle(wrA);

        fontFactory = new FontFactory();
        fontFactory.initialize();

       // this.level =level;
        this.app = app;
        this.score = score;
        backgroundOffset=0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("LMUkrLine.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.borderWidth = 0.5F;

        parameter.color= Color.BLACK;
        parameter.borderColor = Color.WHITE;
        font = generator.generateFont(parameter);
        stage = new Stage(new ScreenViewport());


        batch = new SpriteBatch();


        // Initialize locales
        enLocale = new Locale("en", "US");
        ukrLocale = new Locale("uk", "UA");
        font1=fontFactory.getFont(ukrLocale,1);
        font2=fontFactory.getFont(enLocale,1);
        font3=fontFactory.getFont(ukrLocale,2);



        skin = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin.add("font", font3);

        skin.load(Gdx.files.internal("skin-composer-ui.json"));

        skin2 = new Skin(new TextureAtlas(Gdx.files.internal("skin-composer-ui.atlas")));
        skin2.add("font", font1);

        skin2.load(Gdx.files.internal("skin-composer-ui.json"));



          answerButtons = new TextButton[4];


// Create the answer buttons
        for (int i = 0; i < answerButtons.length; i++) {
            final String buttonText;

                buttonText = wrA.removeFirst();

            // Create the button with the appropriate text
            answerButtons[i] = new TextButton(buttonText, skin);
            answerButtons[i].setSize(GraphicConstants.colWidth * 8, GraphicConstants.rowHeight * 0.7F);
            float gap = 10F;
            float xPos = GraphicConstants.centerX - answerButtons[i].getWidth() * 0.5F;
            float yPos = GraphicConstants.centerY - 200 - (answerButtons[i].getHeight() + gap) * i - answerButtons[i].getHeight();
            answerButtons[i].setPosition(xPos, yPos);


            // Add a click listener to each button
            final int buttonIndex = i;
            answerButtons[i].addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    /**якщо натиснуто на правильну відповідь**/
                    if (answerButtons[buttonIndex].getText().toString().equals(question.getCorrectAnswer())) {

                        /**Якщо це ще не останнє питання рівню, збільшує номер поточного завдання в рівні на 1 і відкриває наступне завдання**/
                        if(level.getTasks().size()!=level.currentTaskNumber()) {
                            level.increaseTaskCounter();
                            level.currentscore+=3;
                           app.setScreen(level.tasks.get(level.currentTaskNumber()-1));
dispose();

                        }
                        /**Якщо це останнє питання, відкриває IntermediateScreen з результатами**/
                        else {
                            level.currentscore+=3;
                            app.setScreen(new IntermediateScreen(app,level,userId,2,false));
dispose();
                        }



                    }
                    /**якщо відповідь неправильна**/
                    else {
                       level.failureScoreCount+=3;
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

            // Add the button to the stage
            stage.addActor(answerButtons[i]);
        }

        textQuestion = new Label(question.getText(),skin);
        textQuestion.setWrap(true);
        textQuestion.setWidth(GraphicConstants.screenWidth-GraphicConstants.screenWidth*0.05F);
        textQuestion.setPosition(GraphicConstants.screenWidth*0.025F,GraphicConstants.centerY + GraphicConstants.rowHeight/2);
        stage.addActor(textQuestion);

        sprite = new Sprite(new Texture(Gdx.files.internal("white.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       // Gdx.input.setInputProcessor(stage);
        layout = new GlyphLayout(font2, "Level "+ level.levelNumb);






    }


private LinkedList<String> generateRandomOrder() {
      LinkedList<String> orderedAnswers = new LinkedList<>();

        return orderedAnswers;
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
        Gdx.input.setInputProcessor(stage);
    }





    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Implement if needed
    }

    @Override
    public void resume() {
        // Implement if needed
    }

    @Override
    public void hide() {
        // Implement if needed
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

}
