package com.klinker.engine2d.collisions;


import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;


public class CollisionBox implements Drawable {

    public Shape shape;
    public Size<Float> size;
    public Vector3f position;
    private SimpleSprite box;

    public enum Shape {
        RECTANGLE
    }

    public CollisionBox(Shape shape, Size<Float> size, Vector2f position, Vector3f parentPosition) {
        this.shape = shape;
        this.size = size;
        this.position = new Vector3f(position.x, position.y, 0);
        this.position.setRelative(parentPosition);
        if (Sprite.showCollisions) {
            this.box = new SimpleSprite(new Vector3f(), this.size, R.textures.COLLISION);
            this.box.getPosition().setRelative(this.position);
            // force the local depth to be at HUD_LOW
            this.box.getPosition().setLocalZ(Depth.HUD_LOW - this.position.globalZ());
        } else {
            this.box = null;
        }
    }

    /**
     * Checks whether or not an object is going to collide with another's projected location.
     * @param collision The collision that is going to be projected into the future to see if it will hit.
     * @param xDif The amount the collision is going to change by in the next update in the globalX dir.
     * @param yDif The amount the collision is going to change by in the next update in the globalY dir.
     * @return Whether or not the items will collide.
     */
    public boolean intersects(CollisionBox collision, float xDif, float yDif) {
        float c1L = this.position.globalX();
        float c1R = this.size.width + this.position.globalX();
        float c2L = collision.position.globalX() + xDif;
        float c2R = this.size.width + collision.position.globalX() + xDif;
        if (c1R >= c2L && c1R <= c2R || c1L >= c2L && c1L <= c2R) {  // the rect overlap horizontally
            float c1B = this.position.globalY();
            float c1T = this.size.height + this.position.globalY();
            float c2B = collision.position.globalY() + yDif;
            float c2T = this.size.height + collision.position.globalY() + yDif;
            return c1T >= c2B && c1T <= c2T || c1B >= c2B && c1B <= c2T; // the rect overlap vertically
        } else {
            return false;
        }
    }

    @Override
    public void render(Camera camera) {
        if (box != null) box.render(camera);
    }

    @Override
    public void update(Camera camera) {
        if (box != null) box.update(camera);
    }

    @Override
    public String description() {
        return "CollisionBox@(" + this.position.localX() + ", " + this.position.localY() + ")";
    }
}
