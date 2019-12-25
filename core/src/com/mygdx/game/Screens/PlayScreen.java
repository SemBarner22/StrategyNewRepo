package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.Entities.MainComponents.World;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.Strategy;

import java.io.IOException;
import java.util.ArrayList;

public class PlayScreen implements Screen {

    //public boolean isMoveEnded;
    private ArrayList<Player> players;
    public static World world;
    public ArrayList<Label> labels;
    //private Player player;
    //private Player player2;
    //private Player player3;
    private InputMultiplexer im;
    private int curPlayer;
    private TiledMap map;
    private OrthogonalTiledMapRendererWithSprites renderer;
    private Batch batch;
    private ShapeRenderer sr;
    private Strategy strategy;
    private Stage stage;
    private Table table;
    public Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    public PlayScreen(final Strategy strategy) {
        this.strategy = strategy;
        labels = new ArrayList<>();
        players = new ArrayList<>();
        curPlayer = 0;
        players.add(new Player(200, -10));
        players.add(new Player(300, -10));
        players.add(new Player(400, -10));
        texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();
        gamePort = new FillViewport(Strategy.V_WIDTH, Strategy.V_HEIGHT, gameCam);
        stage = new Stage();
        try {
            world = new World(players.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("res\\map\\grass_tileset_map1.tmx");
        renderer = new OrthogonalTiledMapRendererWithSprites(map);
        renderer.setView(gameCam);
        sr = new ShapeRenderer();
        sr.setColor(Color.CYAN);
        Gdx.gl.glLineWidth(3);
        gameCam.translate(300, 300);
        im = new InputMultiplexer(stage, players.get(curPlayer));
        Gdx.input.setInputProcessor(im);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.top().pad(10).defaults().expandX().space(10);
        //table.pad(10).defaults().expandX().space(10);
        for (int i = 0; i < 2; i++) {
            table.row();
            for (int j = 0; j < 5; j++) {
                labels.add(new Label(j + i + "dos", skin));
                Label a = labels.get(labels.size() - 1);
                //table.add(labels.get(labels.size() - 1));
                table.add(a);
                //table.add(new Label(j + i + "dos", skin));
            }
        }
        Table bottomTable = new Table();
        stage.addActor(bottomTable);
        bottomTable.setFillParent(true);
        bottomTable.bottom().pad(10).defaults().expandX().space(100);
        TextButton mechanicsButton = new TextButton("Mechanics menu", skin);
        bottomTable.add(mechanicsButton);
        mechanicsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                strategy.setScreen(new MechanicsMenu(strategy, curPlayer, PlayScreen.this));
                players.get(curPlayer).setX(0);
                players.get(curPlayer).setY(0);
            }
        });
        TextButton moveEndButton = new TextButton("End of the move", skin);
        bottomTable.add(moveEndButton);
        world.preTurn(0);
        moveEndButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                world.afterTurn(curPlayer);
                curPlayer = (curPlayer + 1) % players.size();
                if (curPlayer == 0) {
                    world.AfterGlobalTurn();
                }
                world.preTurn(curPlayer);

            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        labels.get(0).setText("Player: " + (curPlayer + 1));
        labels.get(1).setText("Money: " + world.getPlayerGov(curPlayer).mainScreen10Getters()[0]);
        labels.get(6).setText("Profit: " + world.getPlayerGov(curPlayer).mainScreen10Getters()[1]);
        im = new InputMultiplexer(stage, players.get(curPlayer));
        Gdx.input.setInputProcessor(im);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gameCam);
        batch = strategy.batch;
        strategy.batch.setProjectionMatrix(gameCam.combined);
        sr.setProjectionMatrix(gameCam.combined);
        sr.setColor(Color.CYAN);
        strategy.batch.begin();
        renderer.render(new int[]{0, 1, 2, 3, 4});
        strategy.batch.end();

        MapObject playObject = map.getLayers().get("RegionsNew").getObjects().get("Player" + curPlayer);
        Polygon regPlayer = ((PolygonMapObject) playObject).getPolygon();
        Vector3 v3 = new Vector3(players.get(curPlayer).getX(), players.get(curPlayer).getY(), 0);
        gameCam.unproject(v3);
        regPlayer.setPosition(v3.x, v3.y);
        //regPlayer.setPosition(player.getX(), player.getY());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.polygon(regPlayer.getTransformedVertices());
        sr.end();

        MapObject provObject = map.getLayers().get("RegionsNew").getObjects().get("Player" + curPlayer);
        Polygon provPlayer = ((PolygonMapObject) provObject).getPolygon();
        Vector3 v33 = new Vector3(players.get(curPlayer).getX(), players.get(curPlayer).getY(), 0);
        gameCam.unproject(v33);
        provPlayer.setPosition(v33.x, v33.y);
        //regPlayer.setPosition(player.getX(), player.getY());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.polygon(provPlayer.getTransformedVertices());
        sr.end();

        for (MapObject object : map.getLayers().get("RegionsNew").getObjects()) {
            if (object instanceof PolygonMapObject && object.getProperties().containsKey("RegIndex")) {
                Polygon polygonMapObject = ((PolygonMapObject) object).getPolygon();
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.polygon(polygonMapObject.getTransformedVertices());
                sr.end();

                if (Intersector.overlapConvexPolygons(polygonMapObject, regPlayer)) {
                    strategy.batch.begin();
                    renderer.render(new int[]{map.getLayers().getIndex("Region" +
                            object.getProperties().get("RegIndex", Integer.class))});
                    strategy.batch.end();
                }
            }
        }

        for (MapObject object : map.getLayers().get("Provincions").getObjects()) {
            if (object instanceof PolygonMapObject && object.getProperties().containsKey("RegIndex")) {
                Polygon polygonMapObject = ((PolygonMapObject) object).getPolygon();
                //sr.begin(ShapeRenderer.ShapeType.Line);
                //sr.polygon(polygonMapObject.getTransformedVertices());
                //sr.end();

                if (Intersector.overlapConvexPolygons(polygonMapObject, provPlayer)) {
                    strategy.batch.begin();
                    renderer.render(new int[]{map.getLayers().getIndex("ProvReg" +
                            object.getProperties().get("RegIndex", Integer.class))});
                    strategy.batch.end();
                    strategy.setScreen(new CityScreen(strategy, curPlayer, PlayScreen.this));
                    players.get(curPlayer).setX(0);
                    players.get(curPlayer).setY(0);
                }
            }
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = height;
        gameCam.update();
    }

    @Override
    public void pause() {
        //System.out.println("heh paused");
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        texture.dispose();
        map.dispose();
        batch.dispose();
    }
}
