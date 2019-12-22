package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.Strategy;

public class PlayScreen implements Screen {

    private Player player;
    boolean ifLayer1 = true;
    TiledMapTileLayer layer;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Strategy strategy;
    public Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    public PlayScreen(Strategy strategy) {
        this.strategy = strategy;
        texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Strategy.V_WIDTH, Strategy.V_HEIGHT, gameCam);
        player = new Player(0, 0);
    }

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("C:\\Users\\User\\Desktop\\STRATEGY\\res\\maps\\newMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(player);
        layer = (TiledMapTileLayer) map.getLayers().get("first");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gameCam);
        strategy.batch.setProjectionMatrix(gameCam.combined);
        MapObject object;
        PolygonMapObject object1 = null;
        PolygonMapObject object2 = null;
        Polygon p1 = object1.getPolygon();
        Polygon p2 = object2.getPolygon();
        Intersector.overlapConvexPolygons(p1, p2);
        for (int x = 0; x < layer.getWidth(); ++x) {
            for (int y = 0; y < layer.getHeight(); ++y) {
                if (layer.getCell(x, y).getTile().getProperties().containsKey("area")) {
                    //layer.getCell(x, y).hide();
                    if (player.getX() > 3) {
                        ifLayer1 = false;
                        System.out.println("aaa");
                    }
                }
            }
        }
        int[] a;
        //strategy.batch.begin();
        if (ifLayer1) {
            a = new int[]{0, 1};
            //System.out.println("a");
        } else
            a = new int[] {0};
        renderer.render(a);
        //strategy.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameCam.viewportWidth = width;
        gameCam.viewportHeight = height;
        gameCam.update();
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
        texture.dispose();
    }
}
