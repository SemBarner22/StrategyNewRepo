package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class EconomicsScreen extends AbstractMechanicsScreen {
        private Label taxRate;
        private Button marketButton;

        public EconomicsScreen(Strategy strategy, int curPlayer, MechanicsMenu mechanicsMenu) {
                super(strategy, curPlayer, mechanicsMenu);
        }
        
        @Override
        public void show() {
            super.show();
            taxRate = new Label(PlayScreen.world.getPlayerGov(curPlayer).getTaxRate() + "", skin);
            marketButton = new TextButton("Market", skin);
            final Slider slider = new Slider(0, 100, 1, false, skin);
            Table table = new Table();
            String res[] = PlayScreen.world.getPlayerGov(curPlayer).getEconomy();
            for (int i = 0; i < res.length; ++i) {
                container.add(new Label("" + res[i], skin));
                if (i % 2 == 1) {
                    container.row();
                }
            }
            table.addActor(backButton);
            stage.addActor(table);
            container.row();
            container.add(slider).bottom().left();
            container.add(taxRate).bottom().right();
            container.row();
            container.add(marketButton);
            slider.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    PlayScreen.world.getPlayerGov(curPlayer).setTaxRate((int) slider.getValue() / 10);
                    taxRate.setText( PlayScreen.world.getPlayerGov(curPlayer).getTaxRate());
                }
            });
            backButton = new TextButton("back", skin);
            marketButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    strategy.setScreen(new MarketScreen(strategy, curPlayer, EconomicsScreen.this));
                }
            });
        }

        @Override
        public void render(float delta) {
                super.render(delta);
            //table.add(new Label(strings[i], skin)).expandX().fillX();
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
                super.dispose();
        }
}
