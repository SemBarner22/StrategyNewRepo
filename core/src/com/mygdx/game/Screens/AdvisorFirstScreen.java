package com.mygdx.game.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.ExtensionLibrary.ButtonWithIndex;
import com.mygdx.game.ExtensionLibrary.ClickListenerWithIndex;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class AdvisorFirstScreen extends AbstractMechanicsScreen {
    private int place;
    private ArrayList<Label> labels;
    private ArrayList<ButtonWithIndex> buttons;
    private Integer[] availibleAdvisors;
    private Integer[] availibleAdvisorsIndex = new Integer[1000];


    public AdvisorFirstScreen(Strategy strategy, int curPlayer, AdvisorScreen advisorScreen, int place) {
        super(strategy, curPlayer, advisorScreen);
        stage = new Stage();
        container = new Table();
        stage.addActor(container);
        this.place = place;
        for (int i = 0; i < availibleAdvisorsIndex.length; i++) {
            availibleAdvisorsIndex[i] = i;
        }
    }


    @Override
    public void show() {
        super.show();

        //Until advisors are created properly
        //PlayScreen.world.getPlayerGov().createAdvisor("Cleric");
        //Advisor a = PlayScreen.world.getPlayerGov().getAdv(0);
        availibleAdvisors = PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().getUnasignAdvisors();
        //System.out.println(a.getAbilityName());

        Table table = new Table();
        Button dismissButton = new TextButton("dismiss", skin);
        dismissButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().advisorNumber(place) != -1) {
                    PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().dismissAdvisor(PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().advisorNumber(place));
                    strategy.setScreen(previousScreen);
                    //TODO
                }
            }
        });

        final ScrollPane scroll = new ScrollPane(table, skin);
        table.pad(10).defaults().expandX().space(4);

        //PlayScreen.world.getPlayerGov().getUnasignAdvisors();
        availibleAdvisors = PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().getUnasignAdvisors();

        System.out.println(availibleAdvisors.length);

        labels = new ArrayList<>();
        buttons = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            table.row();

            if (i < availibleAdvisors.length) {

                //labels.add(new Label("" + PlayScreen.world.getPlayerGov().getAdv(availibleAdvisors[i]).getAbilityName(), skin));
                labels.add(new Label("", skin));
                table.add(labels.get(i)).expandX().fillX();
                buttons.add(new ButtonWithIndex("Change", skin, i));
                table.add(buttons.get(i));
                buttons.get(i).addListener(new ClickListenerWithIndex(buttons.get(i)) {
                    public void clicked(InputEvent event, float x, float y) {
                        //PlayScreen.world.getPlayerGov().createAdvisor("Cleric");

                        //TODO make change advisor i (testing)
                        //PlayScreen.world.getPlayerGov().AssignAdvisor(availibleAdvisors[j], place + 1);

                        PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().assignAdvisor(
                                availibleAdvisors[getButtonWithIndex().getIndex()], place);

                        System.out.println(getButtonWithIndex().getIndex());


                        strategy.setScreen(previousScreen);
                    }

                });
            } else {
                labels.add(new Label("" + i, skin));
                table.add(labels.get(i)).expandX().fillX();
                buttons.add(new ButtonWithIndex("Change", skin, i));
                table.add(buttons.get(i));
                buttons.get(i).addListener(new ClickListenerWithIndex(buttons.get(i)) {
                    public void clicked(InputEvent event, float x, float y) {
                        //PlayScreen.world.getPlayerGov().createAdvisor("Cleric");


                        //PlayScreen.world.getPlayerGov().AssignAdvisor(0, place);

                        //TODO
                        //PlayScreen.world.getPlayerGov().AssignAdvisor(availibleAdvisors[j], place + 1);
                        PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().assignAdvisor(
                                availibleAdvisors[getButtonWithIndex().getIndex()], place + 1);

                        System.out.println(getButtonWithIndex().getIndex());


                        strategy.setScreen(previousScreen);
                    }
                });
                 labels.get(i).setVisible(false);
                 buttons.get(i).setVisible(false);
            }
        }
        container.add(scroll).expand().fill().colspan(4);
        container.row();
        container.add(backButton).bottom().left().expandX();
        container.add(dismissButton).bottom().right().expandX();
    }

    @Override
    public void render(float delta) {
        availibleAdvisors = PlayScreen.world.getPlayerGov(curPlayer).getGovAdvisors().getUnasignAdvisors();


        //System.out.println(availibleAdvisors.length);
        for (int i = 0; i < labels.size(); i++) {
            if (i < availibleAdvisors.length) {
                labels.get(i).setVisible(true);
                buttons.get(i).setVisible(true);
               // labels.get(i).setText("" + PlayScreen.world.getPlayerGov().getAdv(availibleAdvisors[i]).getAbilityName());
            } else {
                labels.get(i).setVisible(false);
                buttons.get(i).setVisible(false);
            }
        }
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
        stage.dispose();
    }
}
