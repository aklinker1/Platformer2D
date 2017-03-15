package com.klinker.platformer2d.sprite.tiles;

import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.constants.Depth;

public class BackTile extends Tile {

    public BackTile(Vector2f position, int world, int tile) {
        super(position, world, tile);
    }

    @Override
    public float getDepth() {
        return Depth.TILE_BACK;
    }
}
