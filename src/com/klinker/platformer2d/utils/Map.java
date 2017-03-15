package com.klinker.platformer2d.utils;

import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.sprite.MovingSprite;
import com.klinker.platformer2d.sprite.Player;
import com.klinker.platformer2d.sprite.Tile;

import java.util.LinkedList;

public class Map {

    private int world;
    private Size<Integer> size;
    private SparseArray2D<Tile> tiles;
    private LinkedList<MovingSprite> frenemies;
    private Player player;

    public Map(int world, int[][] tiles, Size<Integer> size) {
        this.size = size;
        this.world = world;
        this.tiles = new SparseArray2D<>();
        this.frenemies = new LinkedList<>();

        Vector2f playerStart = new Vector2f();
        for (int y = 0; y < this.size.height; y++) {
            for (int x = 0; x < this.size.width; x++) {
                float difY = y + Platformer2D.TILE_COUNT.y - size.height;
                if (tiles[y][x] == 0xFF) {
                    playerStart = new Vector2f(x, y);
                } else if (tiles[y][x] != 0) {
                    this.tiles.append(x, y, new Tile(new Vector2f(x, y), world, tiles[y][x]));
                }
            }
        }

        this.player = new Player(playerStart, 1);
    }

    public int getWorld() {
        return world;
    }

    public void render() {
        for (int yi = 0; yi < this.tiles.getHeight(); yi++) {
            SparseArray<Tile> row = this.tiles.getRow(yi);
            for (int xi = 0; xi < row.size(); xi++) {
                row.get(row.keyAt(xi)).render();

            }
        }
        player.render();
    }

    public void update() {
        for (int yi = 0; yi < this.tiles.getHeight(); yi++) {
            SparseArray<Tile> row = this.tiles.getRow(yi);
            for (int xi = 0; xi < row.size(); xi++) {
                row.get(row.keyAt(xi)).update();
            }
        }
        player.update(tiles, frenemies);
        if (player.getY() < -1) player.setY(Platformer2D.TILE_COUNT.y);
    }

}
