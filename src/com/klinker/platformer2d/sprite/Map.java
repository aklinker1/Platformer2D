package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprite.abstracts.Frenemy;
import com.klinker.platformer2d.sprite.abstracts.MovingSprite;
import com.klinker.platformer2d.sprite.frenemies.enemies.Krawler;
import com.klinker.platformer2d.sprite.frenemies.players.Player;
import com.klinker.platformer2d.sprite.tiles.Tile;
import com.klinker.platformer2d.utils.SparseArray;
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

    private Player player;


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

        for (int y = 0; y < this.size.height; y++) {
            for (int x = 0; x < this.size.width; x++) {
                if (tiles[y][x] == 0xFF) {
                    this.player = new Player(new Vector2f(x, y), 0x01);
                } else if (tiles[y][x] != 0) {
                    this.tiles.append(x, y, Tile.newInstance(new Vector2f(x, y), world, tiles[y][x]));
                }
            }
        }

        this.frenemies.addFirst(this.player);
        this.frenemies.addLast(new Krawler(new Vector3f(15f, 3f, Depth.ENEMY)));
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
        int xs = Math.round(camera.getPosition().x()) - SPAWN_DISTANCE;
        int ys = Math.round(camera.getPosition().y()) - SPAWN_DISTANCE;
        int xe = Math.round(camera.getPosition().x() + camera.getSize().width) + SPAWN_DISTANCE;
        int ye = Math.round(camera.getPosition().y() + camera.getSize().height) + SPAWN_DISTANCE;

        for (int y = 0; y < this.tiles.getHeight(); y++) {
            for (int x = xs; x < xe; x++) {
                this.tiles.get(x, y).render(camera);
            }
        }
        for (MovingSprite sprite : frenemies) sprite.render(camera);
    }

    /**
     * Updates all the tiles and other draw.
     */
    @Override
    public void update(Camera camera) {
        for (int yi = 0; yi < this.tiles.getHeight(); yi++) {
            SparseArray<Tile> row = this.tiles.getRow(yi);
            for (int xi = 0; xi < row.size(); xi++) {
                row.get(row.keyAt(xi)).update(camera);
            }
        }
        for (Frenemy sprite : frenemies) sprite.update(camera, tiles, frenemies);

        // limit player sprite to map width
        if (player.position.x() + player.vel.x() < 0.25f) {
            player.position.setX(0.25f);
            player.vel.setX(0);
        } else if (player.position.x() + player.vel.x() > size.width - 1.25f) {
            player.position.setX(size.width - 1.25f);
            player.vel.setX(0);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Size<Integer> getSize() {
        return size;
    }

}