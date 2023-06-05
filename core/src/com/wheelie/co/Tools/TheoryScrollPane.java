package com.wheelie.co.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.wheelie.co.Graphics.GraphicConstants;

import java.util.LinkedList;
import java.util.List;

public class TheoryScrollPane {
    public final ScrollPane scrollPane  = new ScrollPane(null);
    public TheoryScrollPane(Skin skin, Skin skinForText, LinkedList<String> texts, LinkedList<Image> images, String topic){
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

    }

}
