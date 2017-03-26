package com.klinker.engine2d.utils;



import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;


public class CollisionBox {

    public Shape shape;
    public Size<Float> size;
    public Vector2f origin;
    public Vector3f position;

    public enum Shape {
        RECTANGLE
    }

    public CollisionBox(Shape shape, Size<Float> size, Vector2f origin, Vector3f position) {
        this.shape = shape;
        this.size = size;
        this.origin = origin;
        this.position = position;
    }

    /**
     * Checks whether or not an object is going to collide with another's projected location.
     * @param collision The collision that is going to be projected into the future to see if it will hit.
     * @param xDif The amount the collision is going to change by in the next update in the x dir.
     * @param yDif The amount the collision is going to change by in the next update in the y dir.
     * @return Whether or not the items will collide.
     */
    public boolean intersects(CollisionBox collision, float xDif, float yDif) {
        float c1L = this.origin.x + this.position.x;
        float c1R = this.origin.x + this.size.width + this.position.x;
        float c2L = collision.origin.x + collision.position.x + xDif;
        float c2R = collision.origin.x + this.size.width + collision.position.x + xDif;
        if (c1R >= c2L && c1R <= c2R || c1L >= c2L && c1L <= c2R) {  // the rect overlap horizontally
            float c1B = this.origin.y + this.position.y;
            float c1T = this.origin.y + this.size.height + this.position.y;
            float c2B = collision.origin.y + collision.position.y + yDif;
            float c2T = collision.origin.y + this.size.height + collision.position.y + yDif;
            return c1T >= c2B && c1T <= c2T || c1B >= c2B && c1B <= c2T; // the rect overlap vertically
        } else {
            return false;
        }
    }

}
