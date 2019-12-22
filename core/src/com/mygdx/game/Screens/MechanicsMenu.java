package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Entities.Adv.Advisor;
import com.mygdx.game.Entities.Estate.Estate;
import com.mygdx.game.Strategy;

import java.util.ArrayList;

public class MechanicsMenu implements Screen {

    Strategy strategy;
    private Stage stage;
    private Table container;
    private PlayScreen playScreen;
    private AdvisorScreen advisorScreen;
    private EstateScreen estateScreen;
    private ArmiesScreen armiesScreen;
    private RegionScreen regionScreen;
    private LawScreen lawScreen;
    private EconomicsScreen economicsScreen;

    public MechanicsMenu(Strategy strategy, PlayScreen playScreen) {
        this.strategy = strategy;
        this.playScreen = playScreen;
        advisorScreen = new AdvisorScreen(strategy, MechanicsMenu.this);
        estateScreen = new EstateScreen(strategy, MechanicsMenu.this);
        armiesScreen = new ArmiesScreen(strategy, MechanicsMenu.this);
        regionScreen = new RegionScreen(strategy, MechanicsMenu.this);
        lawScreen = new LawScreen(strategy, MechanicsMenu.this);
        economicsScreen = new EconomicsScreen(strategy, MechanicsMenu.this);
    }

    @Override
    public void show() {
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        // Gdx.graphics.setVSync(false);

        container = new Table();
        stage.addActor(container);
        container.setFillParent(true);

        Table table = new Table();
        // table.debug();
        final ScrollPane scroll = new ScrollPane(table, skin);

        table.pad(10).defaults().expandX().space(4);
        String[] strings = new String[] {"Advisor", "Estates", "Armies", "Regions",
                "Laws", "Economics"};
        final Screen[] screens = new Screen[] {advisorScreen, estateScreen, armiesScreen, regionScreen,
                                        lawScreen, economicsScreen};
        for (int i = 0; i < strings.length; i++) {
            table.row();
            table.add(new Label(strings[i], skin)).expandX().fillX();
            TextButton button = new TextButton("Further", skin);
            table.add(button);
            final int finalI = i;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    strategy.setScreen(screens[finalI]);
                }
            });
        }
        container.add(scroll).expand().fill().colspan(4);
        container.row().space(10).padBottom(10);
        TextButton backButton = new TextButton("Back", skin);
        container.add(backButton).left().expandX();
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                strategy.setScreen(playScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
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
