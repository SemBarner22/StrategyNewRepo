package com.mygdx.game.ExtensionLibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class OrthographicCameraWithZoom extends OrthographicCamera {
    private float rotationSpeed;

    public OrthographicCameraWithZoom() {
        super();
        rotationSpeed = 0.5f;
    }

    public OrthographicCameraWithZoom(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
        rotationSpeed = 0.5f;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            this.zoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.rotate(rotationSpeed, 0, 0, 1);
        }

        this.zoom = MathUtils.clamp(this.zoom, 0.2f, 1000/this.viewportWidth);

        float effectiveViewportWidth = this.viewportWidth * this.zoom;
        float effectiveViewportHeight = this.viewportHeight * this.zoom;

        this.position.x = MathUtils.clamp(this.position.x, effectiveViewportWidth / 2f, 1000 - effectiveViewportWidth / 2f);
        this.position.y = MathUtils.clamp(this.position.y, effectiveViewportHeight / 2f, 1000 - effectiveViewportHeight / 2f);
    }
}
