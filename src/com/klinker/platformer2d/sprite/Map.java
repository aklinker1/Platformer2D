package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.sprite.abstracts.Frenemy;
import com.klinker.platformer2d.sprite.abstracts.MovingSprite;
import com.klinker.platformer2d.sprite.frenemies.players.Player;
import com.klinker.platformer2d.sprite.tiles.Tile;
import com.klinker.platformer2d.utils.SparseArray2D;

import java.util.LinkedList;

public class Map implements Drawable {

    private static final int SPAWN_DISTANCE = 3;

    /**
     * The world that the tiles are rendered from.
     */
    private int world;

    /**
     * The size of the map.
     */
    private Size<Integer> size;

    /**
     * The array of tiles.
     */
    private SparseArray2D<Tile> tiles;

    /**
     * The list of moving draw on the map.
     */
    private LinkedList<Frenemy> frenemies;


    /**
     * Constructor that is called from MapReader.
     * @param world The world to render the tiles from.
     * @param tiles The array of tiles that make up the map.
     * @param size The size of the map.
     */
    public Map(int world, int[][] tiles, Size<Integer> size, LinkedList<Frenemy> frenemies) {
        this.size = size;
        this.world = world;
        this.tiles = new SparseArray2D<>();
        this.frenemies = frenemies;

        for (int x = 0; x < this.size.width; x++) {
            for (int y = 0; y < this.size.height; y++) {
                if (tiles[y][x] != 0) {
                    this.tiles.append(x, y, Tile.newInstance(new Vector2f(x, y), world, tiles[y][x]));
                }
            }
        }
    }


    /**
     * @return Returns the world that the level takes place in.
     */
    public int getWorld() {
        return world;
    }

    /**
     * Renders all the tiles and other draw.
     */
    @Override
    public void render(Camera camera) {
        // get the start and finish positions to render and update from.
        int xs = Math.round(-camera.getPosition().x()) - SPAWN_DISTANCE;
        int ys = Math.round(-camera.getPosition().y()) - SPAWN_DISTANCE;
        int xe = Math.round(-camera.getPosition().x() + camera.getSize().width) + SPAWN_DISTANCE;
        int ye = Math.round(-camera.getPosition().y() + camera.getSize().height) + SPAWN_DISTANCE;

        for (int y = ys; y < ye; y++) {
            for (int x = xs; x < xe; x++) {
                Tile tile = this.tiles.get(x, y);
                if (tile != null) tile.render(camera);
            }
        }
        for (MovingSprite sprite : frenemies) sprite.render(camera);
    }

    /**
     * Updates all the tiles and other draw.
     */
    @Override
    public void update(Camera camera) {
        // get the start and finish positions to render and update from.
        int xs = Math.round(-camera.getPosition().x()) - SPAWN_DISTANCE;
        int ys = Math.round(-camera.getPosition().y()) - SPAWN_DISTANCE;
        int xe = Math.round(-camera.getPosition().x() + camera.getSize().width) + SPAWN_DISTANCE;
        int ye = Math.round(-camera.getPosition().y() + camera.getSize().height) + SPAWN_DISTANCE;

        // only update visible tiles
        for (int y = ys; y < ye; y++) {
            for (int x = xs; x < xe; x++) {
                Tile tile = this.tiles.get(x, y);
                if (tile != null) tile.update(camera);
            }
        }
        // TODO: update spawn/visible, despawn invisible
        for (Frenemy sprite : frenemies) sprite.update(camera, tiles, frenemies);

        // limit player sprite to map width
        Player player = getPlayer();
        if (player.getClass() == Player.class) {
            if (player.position.x() + player.vel.x() < 0.25f) {
                player.position.setX(0.25f);
                player.vel.setX(0);
            } else if (player.position.x() + player.vel.x() > size.width - 1.25f) {
                player.position.setX(size.width - 1.25f);
                player.vel.setX(0);
            }
        }
    }

    public Player getPlayer() {
        return (Player) frenemies.peekFirst();
    }

    public Size<Integer> getSize() {
        return size;
    }

}
