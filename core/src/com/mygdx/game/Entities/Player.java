package com.mygdx.game.Entities;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.ExtensionLibrary.OrthographicCameraWithZoom;
import com.mygdx.game.Strategy;

public class Player implements InputProcessor {
    public int x;
    public int y;
    public OrthographicCameraWithZoom gameCam;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player(int x, int y, OrthographicCameraWithZoom gameCam) {
        this.x = x;
        this.y = y;
        this.gameCam = gameCam;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vector3 = gameCam.unproject(new Vector3(screenX, screenY, 0));
        setX((int) vector3.x);
        setY((int) vector3.y);
        System.out.println(getX());
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
