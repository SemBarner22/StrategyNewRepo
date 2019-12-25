package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Entities.MainComponents.World;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class MarketScreen extends AbstractMechanicsScreen {
    private ArrayList<Label> labels = new ArrayList<>();
    ArrayList<String> strings;
    public MarketScreen(Strategy strategy, int curPlayer, Screen previousScreen) {
        super(strategy, curPlayer, previousScreen);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        strings = World.getResources().showPrices();
        for (int i = 0; i < strings.size(); ++i) {
            labels.add(new Label(strings.get(i), skin));
            container.add(labels.get(i));
            container.row();
        }
        table.add(backButton).bottom().left();
        container.add(table);
    }

    @Override
    public void render(float delta) {
        strings = World.getResources().showPrices();
        for (int i = 0; i < strings.size(); ++i) {
            if (labels.size() < i) {
                labels.add(new Label(strings.get(i), skin));
            }
            labels.get(i).setText(strings.get(i));
        }
        for (int j = strings.size(); j < labels.size(); ++j) {
            labels.get(j).setText("Out of stock");
        }
        super.render(delta);
    }
}
