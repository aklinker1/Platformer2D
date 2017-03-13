package com.klinker.map_builder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class TileSheetSplitter {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("File path must be provided.");
        } else  {
            Scanner input = new Scanner(System.in);
            File outputParent = new File("out/tiles");
            outputParent.mkdirs();
            for (File child : outputParent.listFiles()) child.delete();
            for (String path : args) {
                File tiles = new File(path);
                System.out.println("Enter tile size for \"" + tiles.getName() + "\":");
                int tileSize = input.nextInt();
                BufferedImage tileSheet = ImageIO.read(tiles);
                if (tileSheet.getWidth() % tileSize != 0 || tileSheet.getHeight() % tileSize != 0) {
                    throw new Exception("Improper tile size... " + tileSize
                            + " does not evenly fit in the tile sheet ("
                            + tileSheet.getWidth() + "x" + tileSheet.getHeight() + ")");
                }
                int tilesX = tileSheet.getWidth() / tileSize;
                int tilesY = tileSheet.getHeight() / tileSize;

                for (int x = 0; x < tilesX; x++) {
                    for (int y = 0; y < tilesY; y++) {
                        BufferedImage tileSheet2 = ImageIO.read(tiles);
                        BufferedImage tile = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_4BYTE_ABGR);
                        Graphics canvas = tile.getGraphics();
                        File output = new File(outputParent, String.format("x%02X-%02X", x, y) + ".png");
                        canvas.drawImage(
                                tileSheet2,
                                0, 0, tileSize, tileSize,
                                x * tileSize, y * tileSize, (x + 1) * tileSize, (y + 1) * tileSize,
                                null
                        );
                        ImageIO.write(tile, "png", output);
                    }
                }
            }
        }
    }

}
