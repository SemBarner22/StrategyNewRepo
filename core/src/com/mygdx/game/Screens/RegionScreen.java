package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Entities.MainComponents.GovComponents.City;
import com.mygdx.game.Entities.MainComponents.World;
import com.mygdx.game.ExtensionLibrary.ButtonWithIndex;
import com.mygdx.game.ExtensionLibrary.ClickListenerWithIndex;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class RegionScreen extends AbstractMechanicsScreen {
    public int regionIndex;
    private Button decAut;
    private ArrayList<ButtonWithIndex> buttons;

    public RegionScreen(Strategy strategy, int curPlayer, int regionIndex, Screen playScreen) {
        super(strategy, curPlayer, playScreen);
        this.regionIndex = regionIndex;
    }

    @Override
    public void show() {
        super.show();
        buttons = new ArrayList<>();
        Table table = new Table();
        decAut = new TextButton("Decrease autonomy", skin);

        if (PlayScreen.world.getAllRegions().get(regionIndex).getOwner() == curPlayer) {
            City[] cities = PlayScreen.world.getAllRegions().get(regionIndex).getCity();
            decAut.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    PlayScreen.world.getAllRegions().get(regionIndex).decreaseAutonomy();
                }
            });
            table.add(decAut).bottom().right();
            container.add(new TextButton("Im the owner of region index " + regionIndex, skin));
            container.add(new Label("First city Y coordinate (as example) " +
                    PlayScreen.world.getAllRegions().get(regionIndex).getCity()[0].getPosition().GetY(), skin));
            container.row();
            for (int i = 0; i < cities.length; ++i) {
                buttons.add(new ButtonWithIndex("City" + i, skin, i));
                container.add(buttons.get(i));
                buttons.get(i).addListener(new ClickListenerWithIndex(buttons.get(i)) {
                    public void clicked(InputEvent event, float x, float y) {
                        strategy.setScreen(new CityScreen(strategy, curPlayer, regionIndex, getButtonWithIndex().getIndex(), RegionScreen.this));
                    }
                });
                container.row();
            }
        }
        table.add(backButton).bottom().left();
        container.add(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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

    }
}
