package com.wheelie.co.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Locale;

/**
 * Даний клас відповідає за створення різних шрифтів
 * **/
public class FontFactory {
    public static final String UKRAINIAN_FONT_NAME = "f3.ttf";
    public static final String UKRAINIAN_FONT_NAME2 = "f3.ttf";
    public static final String ENGLISH_FONT_NAME = "Imperial_Web.ttf";
    public static final String ENGLISH_FONT_NAME2 = "f3.ttf";

    public static final String PRETTY_UKR_FONT_NAME = "Alice-Regular.ttf";
    public static final String UKRAINIAN_CHARACTERS = "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщьЮюЯя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}@";
    public static final String ALL_CHARACTERS = "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщьЮюЯя"
            + "QqWwEeRrTtYyUuIiOoPpAaSsDdFfGgHhJjKkLlZzXxCcVvBbNnMm1234567890.,:;_¡!¿?\"'+-*/()[]={}@";
    private BitmapFont enFont;
    private BitmapFont enSmallFont;
    private BitmapFont ukrFont;

    private BitmapFont smallUkr;
    private BitmapFont verySmallUkr;
    private BitmapFont smallUkrWhite;

    private BitmapFont interUkr;

    private BitmapFont prettyUkr;

    private BitmapFont verySmallEn;
    private BitmapFont prettyProfileUkr;



    private BitmapFont generateFont(String fontName, String characters, int size, Color color) {

        // Configure font parameters
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = size;
        parameter.borderWidth = 1.5F;
        parameter.color= color;
        parameter.borderColor = color;
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal(fontName) );

        BitmapFont font = generator.generateFont(parameter);

        // Dispose resources
        generator.dispose();

        return font;
    }
    public BitmapFont getFont(Locale locale,int numb) {
        if      ("en".equals(locale.getLanguage())&& numb==5) return enSmallFont;
        if      ("en".equals(locale.getLanguage())&& numb==6) return verySmallEn;

        if      ("en".equals(locale.getLanguage())) return enFont;
        else if ("uk".equals(locale.getLanguage()) && numb==6) return verySmallUkr;
        else if ("uk".equals(locale.getLanguage()) && numb==1) return ukrFont;
        else if ("uk".equals(locale.getLanguage()) && numb==4) return smallUkrWhite;
        else if ("uk".equals(locale.getLanguage()) && numb==3) return interUkr;
        else if ("uk".equals(locale.getLanguage()) && numb==2) return smallUkr;
        else if ("uk".equals(locale.getLanguage()) && numb==10) return prettyUkr;
        else if ("uk".equals(locale.getLanguage()) && numb==11) return prettyProfileUkr;

        //НЕ ВИКОРИСТОВУЙТЕ НОМЕР 2 НІ В ЯКОМУ РАЗІ ДЛЯ ШРИФТІВ ЯКЩО БУДЕТЕ СТВОРЮВАТИ НОВІ//

        else throw new IllegalArgumentException("Not supported language");
    }
    public void initialize() {
        // If fonts are already generated, dispose it!
        if (enFont != null) enFont.dispose();
        if (enSmallFont != null) enSmallFont.dispose();
        if (ukrFont != null) ukrFont.dispose();
        if (smallUkr != null) smallUkr.dispose();
        if (smallUkrWhite != null) smallUkrWhite.dispose();
        if (verySmallUkr != null) verySmallUkr.dispose();
        if (smallUkrWhite != null) smallUkrWhite.dispose();
        if (interUkr != null) interUkr.dispose();
        if (verySmallEn != null) verySmallEn.dispose();
        if (prettyUkr != null) prettyUkr.dispose();
        if (prettyProfileUkr != null) prettyProfileUkr.dispose();






        smallUkr = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,70, Color.BLACK);
        verySmallUkr = generateFont(UKRAINIAN_FONT_NAME2, ALL_CHARACTERS,55, Color.BLACK);

        smallUkrWhite = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,70, Color.WHITE);
        interUkr = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,90, Color.BLACK);

        enSmallFont = generateFont(ENGLISH_FONT_NAME2, FreeTypeFontGenerator.DEFAULT_CHARS,70, Color.BLACK);
        interUkr = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,90, Color.BLACK);

        enFont = generateFont(ENGLISH_FONT_NAME, FreeTypeFontGenerator.DEFAULT_CHARS,100, Color.BLACK);
        ukrFont = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,95, Color.WHITE);

        verySmallEn = generateFont(ENGLISH_FONT_NAME2, ALL_CHARACTERS,55, Color.BLACK);

        prettyUkr = generateFont(PRETTY_UKR_FONT_NAME, ALL_CHARACTERS,55, Color.BLACK);
        prettyProfileUkr = generateFont(PRETTY_UKR_FONT_NAME, ALL_CHARACTERS,70, Color.BLACK);


    }
}
