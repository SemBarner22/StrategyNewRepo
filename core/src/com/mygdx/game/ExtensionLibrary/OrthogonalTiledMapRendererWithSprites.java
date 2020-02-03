package com.mygdx.game.ExtensionLibrary;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
    }

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void renderObject(MapObject object) {
        super.renderObject(object);
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            batch.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY(),
                    textureObj.getOriginX(), textureObj.getOriginY(), textureObj.getTextureRegion().getRegionWidth(), textureObj.getTextureRegion().getRegionHeight(),
                    textureObj.getScaleX(), textureObj.getScaleY(), textureObj.getRotation());
        }
    }
}
