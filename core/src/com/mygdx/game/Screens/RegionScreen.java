package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private Button decAut, createArmy;
    private ArrayList<ButtonWithIndex> buttons;
    private Label failedOp;

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
        createArmy = new TextButton("Create army", skin);
        failedOp = new Label("", skin);
        final ScrollPane scroll = new ScrollPane(table, skin);
        String res[] = PlayScreen.world.getAllRegions().get(regionIndex).getRegionScreen();
        String[] namesGet = new String[res.length];
        namesGet[1] = "productionRR ";
        namesGet[2] = "profitRR ";
        namesGet[3] = "productionMin ";
        namesGet[4] = "profitMineral ";
        namesGet[5] = "culture ";
        namesGet[6] = "religion ";
        namesGet[7] = "population ";
        namesGet[8] = "totalPop ";
        namesGet[9] = "infrastructure ";
        namesGet[10] = "prosperity ";
        namesGet[11] = "autonomy ";

        if (PlayScreen.world.getAllRegions().get(regionIndex).getOwner() == curPlayer) {
            for (int i = 1; i < res.length; ++i) {
                table.add(new Label(namesGet[i] + res[i], skin));
                if (i % 2 == 0) {
                    table.row();
                }
            }
            City[] cities = PlayScreen.world.getAllRegions().get(regionIndex).getCity();
            decAut.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    PlayScreen.world.getAllRegions().get(regionIndex).decreaseAutonomy();
                }
            });
            createArmy.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    //TODO get City index through touch
                    String s = PlayScreen.world.getPlayerGov(curPlayer).createArmy(PlayScreen.world.getAllRegions().get(regionIndex).getCity()[0]);
                    if (!s.equals("Success")) {
                        failedOp.setText(s);
                    }
                }
            });
            table.add(decAut).bottom().right();
            table.row();
            table.add(createArmy).bottom().right();
            table.row();
            table.add(failedOp).bottom().right();
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
        //container.add(table);
        container.add(scroll).expand().fill().colspan(4);
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
