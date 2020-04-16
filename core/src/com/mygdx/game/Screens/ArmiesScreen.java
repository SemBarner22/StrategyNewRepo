package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Strategy;

public class ArmiesScreen extends AbstractMechanicsScreen {

    public boolean isMobilisation = false;
    private TextButton mobilisation;
    public ArmiesScreen(Strategy strategy, int curPlayer, MechanicsMenu mechanicsMenu) {
        super(strategy, curPlayer, mechanicsMenu);
    }

    @Override
    public void show() {
        super.show();
        Label mobAmount = new Label("" + 1, skin);
        Label regAmount = new Label("" + 1, skin);
        mobilisation = new TextButton("Mobilisation", skin);
        mobilisation.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!isMobilisation) {
                    mobilisation.setText("Demobilisation");
                    isMobilisation =! isMobilisation;
                }
                else {
                    mobilisation.setText("Mobilisation");
                    isMobilisation =! isMobilisation;
                }
            }
        });
        Label getModTactic = new Label("" + PlayScreen.world.getPlayerGov(curPlayer).govArmy.armyMod()[1], skin);
        Label getModShock = new Label("" + 1, skin);
        Label getModFire = new Label("" + 1, skin);
        Label getModMorale = new Label("" + PlayScreen.world.getPlayerGov(curPlayer).govArmy.armyMod()[0], skin);
        Label getModOrganisation = new Label("" + PlayScreen.world.getPlayerGov(curPlayer).govArmy.armyMod()[2], skin);
        Label getEquipment = new Label("" + 1, skin);
        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.add(mobAmount).top().center();
        table.row();
        table.add(regAmount);
        table.row();
        table.add(mobilisation);
        table.row();
        table.add(getModTactic);
        table.row();
        table.add(getModShock);
        table.row();
        table.add(getModFire);
        table.row();
        table.add(getModMorale);
        table.row();
        table.add(getModOrganisation);
        table.row();
        table.add(getEquipment);
        table.row();
        container.add(backButton).bottom().left().expandX();
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
        super.dispose();
    }
}
