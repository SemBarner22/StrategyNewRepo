package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Strategy;

public class MarketScreen extends AbstractMechanicsScreen {
    public MarketScreen(Strategy strategy, int curPlayer, Screen previousScreen) {
        super(strategy, curPlayer, previousScreen);
    }

    @Override
    public void show() {
        super.show();
        container.add(backButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
