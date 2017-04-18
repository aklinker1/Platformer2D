package com.klinker.platformer2d.sprites.tiles.ground;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprites.tiles.Tile;


public class FillShapeOutline extends Tile {

    public FillShapeOutline(Vector2f position, int world) {
        super(
                position,
                Depth.TILE,
                new Size<Float>(1f, 1f),
                R.textures.tiles.ground.FILL_1_W01,
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
