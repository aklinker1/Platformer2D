package com.klinker.platformer2d.sprite.tiles.blocks;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprite.tiles.Tile;


public class EndTopRight extends Tile {

    public EndTopRight(Vector2f position, int world) {
        super(
                position,
                Depth.TILE,
                new Size<Float>(1f, 1f),
                R.textures.tiles.blocks.END_TOP_RIGHT_W02,
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
