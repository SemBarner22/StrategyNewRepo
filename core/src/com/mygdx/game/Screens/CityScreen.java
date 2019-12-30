package com.mygdx.game.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Strategy;

import java.util.ArrayList;
import java.util.List;

public class CityScreen extends AbstractMechanicsScreen {
    List<Table> citiesList;
    ArrayList<Label> labels = new ArrayList<>();
    private int regionIndex;
    private int cityIndex;
    public CityScreen(Strategy strategy, int curPlayer, int regionIndex, int cityIndex, RegionScreen regionScreen) {
        super(strategy, curPlayer, regionScreen);
        this.regionIndex = regionIndex;
        this.cityIndex = cityIndex;
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        final ScrollPane scroll = new ScrollPane(table, skin);
        int res[] = PlayScreen.world.getAllRegions().get(regionIndex).getCity()[cityIndex].getCityScreen();
        for (int i = 0; i < res.length; ++i) {
            table.add(new Label("" + res[i], skin));
            if (i % 2 == 1) {
                table.row();
            }
        }
        table.pad(100).defaults().expandX().space(4);
//        Table cityTable = new Table();
//        Table regionTable = new Table();
//        citiesList = new ArrayList<>();
//        Table otherCities = new Table();
//        table.add(regionTable);
//        table.row();
//        table.add(cityTable);
//        table.row();
//        for (int i = 0; i < 100; i++) {
//            citiesList.add(new Table());
//            citiesList.get(i).add(new Label("City" + (i + 1), skin));
//            citiesList.get(i).row();
//            citiesList.get(i).add(new TextButton("top", skin)).left().padRight(100);
//            citiesList.get(i).add(new TextButton("middlee", skin)).padRight(100);
//            citiesList.get(i).add(new TextButton("kek", skin)).right();
//        }
//        //more
//        for (int i = 0; i < citiesList.size(); i++) {
//            table.add(citiesList.get(i));
//            table.row();
//        }
//        regionTable.add(new Label("Region name", skin));
//        cityTable.add(new Label("City name", skin));
//        otherCities.add(new Label("OtherCities", skin));
        container.add(scroll).expand().fill().colspan(4);
        container.row();
        container.add(backButton).bottom().left();
        Button investment = new TextButton("Invest", skin);
        container.add(investment).bottom().padLeft(100);
        investment.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.world.getPlayerGov(curPlayer).invest(regionIndex, cityIndex);
            }
        });
        Button test = new TextButton("test", skin);
        container.add(test).bottom().padLeft(200);
        test.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                strategy.setScreen(new BuildingsScreen(strategy, curPlayer, CityScreen.this));
            }
        });
    }

    @Override
    public void render(float delta) {

        super.render(delta);
    }
}
