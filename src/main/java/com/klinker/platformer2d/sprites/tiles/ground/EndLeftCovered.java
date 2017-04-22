package com.klinker.platformer2d.sprites.tiles.ground;

import com.klinker.engine2d.collisions.CollisionBox;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprites.tiles.Tile;

public class EndLeftCovered extends Tile {

    public EndLeftCovered(Vector2f position, int world) {
        super(
                position,
                Depth.TILE,
                new Size<Float>(1f, 1f),
                R.textures.tiles.ground.END_LEFT_COVERED_W01,
                world
        );
    }

    @Override
    public CollisionBox initializeCollision() {
        return new CollisionBox(
                CollisionBox.Shape.RECTANGLE,
                this.size,
                new Vector2f(),
                this.position
        );
    }
}
