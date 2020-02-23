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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
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
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.World;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.ExtensionLibrary.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.ExtensionLibrary.OrthographicCameraWithZoom;
import com.mygdx.game.Strategy;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static java.lang.Math.*;

public class PlayScreen implements Screen {

    //public boolean isMoveEnded;
    private int armyX;
    private int armyY;
    int deep[][];
    int dop[][];
    int clickedArmy = 0;
    State state = State.ARMIE;
    private ArrayList<Player> players;
    TextButton mechanicsButton;
    TextButton moveEndButton;
    public static World world;
    public ArrayList<Label> labels;
    //private Player player;
    //private Player player2;
    //private Player player3;
    private InputMultiplexer im;
    private int curPlayer;
    private TiledMap map;
    private com.mygdx.game.ExtensionLibrary.OrthogonalTiledMapRendererWithSprites renderer;
    private Batch batch;
    private ShapeRenderer sr;
    private Strategy strategy;
    private Stage stage;
    private Table table;
    public Texture texture;
    private OrthographicCameraWithZoom gameCam;
    private Viewport gamePort;
    private TiledMapTileLayer armies;
    private TiledMapTileLayer greenArea;

    public PlayScreen(final Strategy strategy) {
        this.strategy = strategy;
        labels = new ArrayList<>();
        players = new ArrayList<>();
        curPlayer = 0;
        gameCam = new OrthographicCameraWithZoom();
        players.add(new Player(200, -10, gameCam));
        players.add(new Player(300, -10, gameCam));
        players.add(new Player(400, -10, gameCam));
        texture = new Texture("badlogic.jpg");
        gamePort = new FillViewport(Strategy.V_WIDTH / 2f, Strategy.V_HEIGHT / 2f, gameCam);
        stage = new Stage();
        try {
            world = new World(players.size(), Strategy.F_WIDTH, Strategy.F_HEIGHT);
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
        mechanicsButton = new TextButton("Mechanics menu", skin);
        bottomTable.add(mechanicsButton);
        mechanicsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (state == State.DEFAULT) {
                    strategy.setScreen(new MechanicsMenu(strategy, curPlayer, PlayScreen.this));
                    players.get(curPlayer).setX(0);
                    players.get(curPlayer).setY(0);
                } else {
                    state = State.DEFAULT;
                    players.get(curPlayer).setX(0);
                    players.get(curPlayer).setY(0);
                }
            }
        });
        moveEndButton = new TextButton("End of the move", skin);
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
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        gameCam.handleInput();
        gameCam.update();
        labels.get(0).setText("Player: " + (curPlayer + 1));
        labels.get(1).setText("Money: " + world.getPlayerGov(curPlayer).mainScreen10Getters()[0]);
        labels.get(6).setText("Profit: " + world.getPlayerGov(curPlayer).mainScreen10Getters()[1]);
        labels.get(2).setText("Admin" + world.getPlayerGov(curPlayer).mainScreen10Getters()[2]);
        labels.get(3).setText("Legitimacy" + world.getPlayerGov(curPlayer).mainScreen10Getters()[3]);
        labels.get(4).setText("Prestige" + world.getPlayerGov(curPlayer).mainScreen10Getters()[4]);
        //System.out.println("Legitimacy" + world.getPlayerGov(curPlayer).mainScreen10Getters()[3]);
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

        armies = new TiledMapTileLayer(32, 32, 16, 16);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        //test
        TiledMapTileSet dungeon = map.getTileSets().getTileSet(0);
        cell.setTile(dungeon.getTile(2));
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; j++) {
                if (World.mof.CheckPosition(new Position(i, j)) > -1) {
                    armies.setCell(i, j, cell);
                }
            }
        }
        if (state == State.DEFAULT) {

            mechanicsButton.setText("Mechanics menu");
            moveEndButton.setText("End of the move");

            //TODO Coordinates from screen to TileMap
            //mof
            Vector3 v0 = new Vector3(players.get(curPlayer).getX(), players.get(curPlayer).getY(), 0);
            //Vector3 v00 = gameCam.unproject(v0);
            //int newX = max(0, (int) (min(49, max(0, v0.x / 10))));
            //int newY = max(0, (int) (49 - min(49, max(0, v0.y / 10))));
            int newX = (int) v0.x / 16;
            int newY = (int) v0.y / 16;
            System.out.println("PLAYER COORDINATES: " + players.get(curPlayer).getX() + " " + players.get(curPlayer).getY());
            System.out.println("COORDINATES: " + newX + " " + newY);
            if (newX >= 0 && newX <= 31 && newY >= 0 && newY <= 31
                    && World.mof.CheckPosition(new Position(newX, newY)) == curPlayer) {
                state = State.ARMIE;
                armyX = newX;
                armyY = newY;
                //players.get(curPlayer).setX((int) (gameCam.unproject(new Vector3(0, 0, 0))).x);
                //players.get(curPlayer).setY((int) (gameCam.unproject(new Vector3(0, 0, 0))).y);
                Vector3 vector3 = gameCam.unproject(new Vector3(0, 0, 0));
//                players.get(curPlayer).setX((int) vector3.x);
//                players.get(curPlayer).setY((int) vector3.y);
                players.get(curPlayer).setX(0);
                players.get(curPlayer).setY(0);
            }


            MapObject playObject = map.getLayers().get("RegionsNew").getObjects().get("Player" + curPlayer);
            Polygon regPlayer = ((PolygonMapObject) playObject).getPolygon();
            Vector3 v3 = new Vector3(gameCam.unproject(new Vector3(players.get(curPlayer).getX(), players.get(curPlayer).getY(), 0)));
            // gameCam.unproject(v3);
            //       regPlayer.setPosition(v3.x, v3.y);
            regPlayer.setPosition(players.get(curPlayer).getX(), players.get(curPlayer).getY());
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.polygon(regPlayer.getTransformedVertices());
            sr.end();

            MapObject provObject = map.getLayers().get("RegionsNew").getObjects().get("Player" + curPlayer);
            Polygon provPlayer = ((PolygonMapObject) provObject).getPolygon();
            Vector3 v33 = new Vector3(players.get(curPlayer).getX(), players.get(curPlayer).getY(), 0);
            gameCam.unproject(v33);
            // provPlayer.setPosition(v33.x, v33.y);
            provPlayer.setPosition(players.get(curPlayer).getX(), players.get(curPlayer).getY());
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
//                    strategy.setScreen(new RegionScreen(strategy, curPlayer,
//                            object.getProperties().get("RegIndex", Integer.class),PlayScreen.this));
//                    players.get(curPlayer).setX(0);
//                    players.get(curPlayer).setY(0);
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
                        strategy.setScreen(new RegionScreen(strategy, curPlayer,
                                object.getProperties().get("RegIndex", Integer.class), PlayScreen.this));
                        //strategy.setScreen(new CityScreen(strategy, curPlayer, PlayScreen.this));
//                        Vector3 vector3 = gameCam.unproject(new Vector3(0, 0, 0));
//                        players.get(curPlayer).setX((int) vector3.x);
//                        players.get(curPlayer).setY((int) vector3.y);
                        players.get(curPlayer).setX(0);
                        players.get(curPlayer).setY(0);
                    }
                }
            }
            map.getLayers().add(armies);
            renderer.render(new int[]{map.getLayers().getIndex(armies)});
        }
        if (state == State.ARMIE) {
            greenArea = new TiledMapTileLayer(32, 32, 16, 16);

            mechanicsButton.setText("Cancel");
            moveEndButton.setText("Advanced");
            TiledMapTileLayer.Cell cellGreen = new TiledMapTileLayer.Cell();

            //test
            TiledMapTileSet greenSet = map.getTileSets().getTileSet(0);
            TiledMapTileLayer.Cell cellRed = new TiledMapTileLayer.Cell();
            cellRed.setTile(greenSet.getTile(6));
            cellGreen.setTile(greenSet.getTile(7));
            int amount = 0;
            int all = 0;
            deep = new int[32][32];
            dop = new int[32][32];
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    deep[i][j] = -1;
                    dop[i][j] = -1;
                }
            }
            dfs(armyX, armyY, 0);
            for (int i = 0; i < 32; ++i) {
                for (int j = 0; j < 32; j++) {
                    all++;
                    if (World.mof.CheckPosition(new Position(i, j)) == -1
                    && (abs(i - armyX) + abs(j - armyY) <= 3 && deep[i][j] >= 0 && dop[i][j] == -1
                    && deep[i][j] <= 3)) {
                        greenArea.setCell(i, j, cellGreen);
                        amount++;
                    } else if (World.mof.CheckPosition(new Position(i, j)) == -1
                            && (abs(i - armyX) + abs(j - armyY) <= 3 && deep[i][j] >= 0 && dop[i][j] != -1
                            && deep[i][j] <= 3)) {
                        greenArea.setCell(i, j, cellRed);
                    }
                }
            }
            System.out.println(all + " " +amount);
            Vector3 v0 = new Vector3(players.get(curPlayer).getX(), players.get(curPlayer).getY(), 0);
            //gameCam.unproject(v0);
//            int newX = max(0, (int) (min(49, v0.x / 10)));
//            int newY = max(0, (int) (49 - min(49, v0.y / 10)));
            int newX = (int) v0.x / 16;
            int newY = (int) v0.y / 16;
            //System.out.println("COORDINATES: " + newX + " " + newY);
            if (newX >= 0 && newX <= 31 && newY >= 0 && newY <= 31
                    && World.mof.CheckPosition(new Position(newX, newY)) == -1
                    && (abs(newX - armyX) + abs(newY - armyY) <= 3
                    && deep[newX][newY] >= 0 && dop[newX][newY] == -1 && deep[newX][newY] <= 3)) {
                state = State.DEFAULT;
                //Only one tile changes
                World.mof.moveArmy(new Position(armyX, armyY), new Position(newX, newY));
                //Vector3 vector3 = gameCam.unproject(new Vector3(0, 0, 0));
                players.get(curPlayer).setX(0);
                players.get(curPlayer).setY(0);
            } else if (newX >= 0 && newX <= 31 && newY >= 0 && newY <= 31
                    && World.mof.CheckPosition(new Position(newX, newY)) == -1
                    && (abs(newX - armyX) + abs(newY - armyY) <= 3
                    && deep[newX][newY] >= 0 && dop[newX][newY] != -1 && deep[newX][newY] <= 3)) {
                state = State.WAR;
            }
            map.getLayers().add(armies);
            renderer.render(new int[]{map.getLayers().getIndex(armies)});
            map.getLayers().add(greenArea);
            renderer.render(new int[]{map.getLayers().getIndex(greenArea)});
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

    void dfs(int x, int y, int d) {
        if (x >= 0 && x < 32 && y >= 0 && y < 32 && d <= 5) {
            if (deep[x][y] != -1) {
                deep[x][y] = min(d, deep[x][y]);
            } else {
                deep[x][y] = d;
            }
            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    if (Math.abs(i) + Math.abs(j) < 2) {
                        if (x + i >= 0 && x + i <= 31 && y + j >= 0 && y + j <= 31 &&
                                World.mof.CheckPosition(new Position(x + i, y + j)) > 0 &&
                                World.mof.CheckPosition(new Position(x + i, y + j)) != curPlayer) {
                            dfs_dop(x + i, y + j, 0);
                        } else if (x + i >= 0 && x + i <= 31 && y + j >= 0 && y + j <= 31 &&
                                World.mof.CheckPosition(new Position(x + i, y + j)) == -1) {
                            dfs(x + i, y + j, d + 1);
                        }
                    }
                }
            }
        }
    }
    void dfs_dop(int x, int y, int d) {
        if (x >= 0 && x < 32 && y >= 0 && y < 32 && d <= 2) {
            dop[x][y] = d;
            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    if (Math.abs(i) + Math.abs(j) <= 2) {
                        if (x + i >= 0 && x + i <= 31 && y + j >= 0 && y + j <= 31) {
                            dfs_dop(x + i, y + j, d + 1);
                        }
                    }
                }
            }
        }
    }
}

enum State {
    ARMIE,
    DEFAULT,
    WAR
}

