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
import com.mygdx.game.Entities.MainComponents.World;
import com.mygdx.game.Strategy;

public class RegionScreen extends AbstractMechanicsScreen {
    public int regionIndex;
    private Button decAut;

    public RegionScreen(Strategy strategy, int curPlayer, int regionIndex, Screen playScreen) {
        super(strategy, curPlayer, playScreen);
        this.regionIndex = regionIndex;
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        decAut = new TextButton("Decrease autonomy", skin);

        if (PlayScreen.world.getAllRegions().get(regionIndex).getOwner() == curPlayer) {
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
