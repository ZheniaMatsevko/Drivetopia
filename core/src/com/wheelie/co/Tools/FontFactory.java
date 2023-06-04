package com.wheelie.co.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Locale;

public class FontFactory {
    public static final String UKRAINIAN_FONT_NAME = "f3.ttf";
    public static final String UKRAINIAN_FONT_NAME2 = "f3.ttf";
    public static final String ENGLISH_FONT_NAME = "Imperial_Web.ttf";
    public static final String ENGLISH_FONT_NAME2 = "f3.ttf";
    public static final String UKRAINIAN_CHARACTERS = "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщьЮюЯя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";
    private BitmapFont enFont;
    private BitmapFont enSmallFont;
    private BitmapFont ukrFont;

    private BitmapFont smallUkr;
    private BitmapFont verySmallUkr;
    private BitmapFont smallUkrWhite;

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
        if      ("en".equals(locale.getLanguage())) return enFont;
        else if ("uk".equals(locale.getLanguage()) && numb==6) return verySmallUkr;
        else if ("uk".equals(locale.getLanguage()) && numb==1) return ukrFont;
        else if ("uk".equals(locale.getLanguage()) && numb==4) return smallUkrWhite;
        else if ("uk".equals(locale.getLanguage())) return smallUkr;
        else throw new IllegalArgumentException("Not supported language");
    }
    public void initialize() {
        // If fonts are already generated, dispose it!
        if (enFont != null) enFont.dispose();
        if (enSmallFont != null) enSmallFont.dispose();
        if (ukrFont != null) ukrFont.dispose();
        if (smallUkr != null) ukrFont.dispose();
        if (smallUkrWhite != null) ukrFont.dispose();
        if (verySmallUkr != null) verySmallUkr.dispose();


        smallUkr = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,70, Color.BLACK);
        verySmallUkr = generateFont(UKRAINIAN_FONT_NAME2, UKRAINIAN_CHARACTERS,55, Color.BLACK);

        smallUkrWhite = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,70, Color.WHITE);
        enSmallFont = generateFont(ENGLISH_FONT_NAME2, FreeTypeFontGenerator.DEFAULT_CHARS,70, Color.BLACK);
        enFont = generateFont(ENGLISH_FONT_NAME, FreeTypeFontGenerator.DEFAULT_CHARS,100, Color.BLACK);
        ukrFont = generateFont(UKRAINIAN_FONT_NAME, UKRAINIAN_CHARACTERS,95, Color.WHITE);
    }
}
