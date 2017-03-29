package com.klinker.platformer2d.utils;

import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.sprite.abstracts.MovingSprite;
import com.klinker.platformer2d.sprite.players.Player;
import com.klinker.platformer2d.sprite.tiles.Tile;

import java.util.LinkedList;

public class Map {

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
    private LinkedList<MovingSprite> frenemies;


    /**
     * Constructor that is called from MapReader.
     * @param world The world to render the tiles from.
     * @param tiles The array of tiles that make up the map.
     * @param size The size of the map.
     * @param level The level that this Map is a part of.
     */
    public Map(int world, int[][] tiles, Size<Integer> size, Level level) {
        this.size = size;
        this.world = world;
        this.tiles = new SparseArray2D<>();
        this.frenemies = new LinkedList<>();

        Vector2f playerStart = new Vector2f();
        for (int y = 0; y < this.size.height; y++) {
            for (int x = 0; x < this.size.width; x++) {
                float difY = y + Platformer2D.tileCounts.y - size.height;
                if (tiles[y][x] == 0xFF) {
                    playerStart = new Vector2f(x, y);
                } else if (tiles[y][x] != 0) {
                    this.tiles.append(x, y, Tile.newInstance(new Vector2f(x, y), world, tiles[y][x]));
                }
            }
        }

        this.frenemies.addFirst(new Player(playerStart, 0x01, level));
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
    public void render() {
        for (int yi = 0; yi < this.tiles.getHeight(); yi++) {
            SparseArray<Tile> row = this.tiles.getRow(yi);
            for (int xi = 0; xi < row.size(); xi++) {
                row.get(row.keyAt(xi)).render();
            }
        }
        for (MovingSprite sprite : frenemies) {
            sprite.render();
        }
    }

    /**
     * Updates all the tiles and other draw.
     */
    public void update() {
        for (int yi = 0; yi < this.tiles.getHeight(); yi++) {
            SparseArray<Tile> row = this.tiles.getRow(yi);
            for (int xi = 0; xi < row.size(); xi++) {
                row.get(row.keyAt(xi)).update();
            }
        }
        for (MovingSprite sprite : frenemies) {
            sprite.update(tiles, frenemies);
        }
    }

}
