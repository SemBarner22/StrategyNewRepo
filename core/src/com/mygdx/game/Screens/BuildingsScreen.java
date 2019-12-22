package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.ExtensionLibrary.ButtonWithIndex;
import com.mygdx.game.ExtensionLibrary.ClickListenerWithIndex;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class BuildingsScreen extends AbstractMechanicsScreen {

    private ArrayList<Label> labels;
    private ArrayList<ButtonWithIndex> buttons;
    private ArrayList<Table> tableList;
    private Integer[] buildingIndex = new Integer[1000];
    public BuildingsScreen(Strategy strategy, CityScreen previousScreen) {
        super(strategy, previousScreen);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        table.pad(100).defaults().expandX().space(4);
        final ScrollPane buildingPane = new ScrollPane(table, skin);
        table.add(new Label("Buildings", skin));
        table.row();
        tableList = new ArrayList<>();
        labels = new ArrayList<>();
        buttons = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            tableList.add(new Table());
            labels.add(new Label(" " + 2, skin));
            buttons.add(new ButtonWithIndex(" " + 3, skin, i));
            tableList.get(i).add(labels.get(i));
            tableList.get(i).add(buttons.get(i));
        }
        for (int i = 0; i < tableList.size(); i++) {
            table.add(tableList.get(i));
            table.row();
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).addListener(new ClickListenerWithIndex(buttons.get(i)) {
                public void clicked(InputEvent event, float x, float y) {
                    //getButtonWithIndex().getIndex()
                }
            });
        }
        container.add(buildingPane).expand().fill().colspan(4);
        container.row();
        container.add(backButton).bottom().left().expandX();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
