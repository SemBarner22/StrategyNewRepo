package com.mygdx.game.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Entities.Adv.Advisor;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class AdvisorScreen extends AbstractMechanicsScreen {

    private ArrayList<Label> labels;
    private final int AdvisorMaxAmount = 5;
    public AdvisorScreen(Strategy strategy, MechanicsMenu mechanicsMenu) {
        super(strategy, mechanicsMenu);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        final ScrollPane scroll = new ScrollPane(table, skin);
        table.pad(10).defaults().expandX().space(4);
        //String[] strings = new String[] {"Advisor1", "Advisor2", "Advisor3", "Advisor4", "Advisor5"};
        labels = new ArrayList<>();
        for (int i = 0; i < AdvisorMaxAmount; i++) {
            final int place = i;
            table.row();
            Advisor advisor = PlayScreen.world.getPlayerGov().getAdv(i);
            String advisorAbilities = (advisor != null ? advisor.getAbilityName() : "No advisor");
            Label label = new Label(advisorAbilities, skin);
            labels.add(label);
            table.add(label).expandX().fillX();
            TextButton button = new TextButton("Further", skin);
            table.add(button);
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    strategy.setScreen(new AdvisorFirstScreen(strategy, AdvisorScreen.this, place));
                }
            });
        }
        container.add(scroll).expand().fill().colspan(4);
        container.row();
        container.add(backButton).bottom().left().expandX();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        for (int i = 0; i < labels.size(); i++) {
            labels.get(i).setText(PlayScreen.world.getPlayerGov().getAdv(i) != null ?
                    PlayScreen.world.getPlayerGov().getAdv(i).getAbilityName() : "No advisor");
        }
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
