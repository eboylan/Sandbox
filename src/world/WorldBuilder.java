/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import entities.BaseEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.Point;

/**
 *
 * @author Emmet
 */
public class WorldBuilder {

    private int depth;
    private int width;
    private int height;
    private Tile[][][] tiles;
    private int[][][] regions;
    private int nextRegion;

    public WorldBuilder(int depth, int width, int height) {
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.tiles = new Tile[depth][width][height];
        this.regions = new int[depth][width][height];
        this.nextRegion = 1;
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder randomizeTiles() {
        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    tiles[z][x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
                }
            }
        }
        return this;
    }

    private WorldBuilder initSmooth(int times) {
        Tile[][][] tiles2 = new Tile[depth][width][height];
        for (int time = 0; time < times; time++) {

            for (int z = 0; z < depth; z++) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {

                        int floors = 0;
                        int rocks = 0;

                        for (int ox = -1; ox < 2; ox++) {
                            for (int oy = -1; oy < 2; oy++) {
                                if (x + ox < 0 || x + ox >= width || y + oy < 0
                                        || y + oy >= height) {
                                    continue;
                                }

                                if (tiles[z][x + ox][y + oy] == Tile.FLOOR) {
                                    floors++;
                                } else {
                                    rocks++;
                                }
                            }
                        }
                            tiles2[z][x][y] = floors >= rocks || rocks <= 1 ? Tile.FLOOR : Tile.WALL;
                        }
                    }
                }
                tiles = tiles2;
            }
            return this;
        }
    

        //basic smoothing method need to add initial gap fill/close smoooth
    

    

    private WorldBuilder smooth(int times) {
        Tile[][][] tiles2 = new Tile[depth][width][height];
        for (int time = 0; time < times; time++) {

            for (int z = 0; z < depth; z++) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {

                        int floors = 0;
                        int rocks = 0;

                        for (int ox = -1; ox < 2; ox++) {
                            for (int oy = -1; oy < 2; oy++) {
                                if (x + ox < 0 || x + ox >= width || y + oy < 0
                                        || y + oy >= height) {
                                    continue;
                                }

                                if (tiles[z][x + ox][y + oy] == Tile.FLOOR) {
                                    floors++;
                                } else {
                                    rocks++;
                                }
                            }
                        }
                            tiles2[z][x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                        }
                    }
                }
                tiles = tiles2;
            }
            return this;
        }

    

    public WorldBuilder makeCaves() {
        return randomizeTiles().initSmooth(2).smooth(2).createRegions().connectRegions();
    }

    private WorldBuilder createRegions() {
        regions = new int[depth][width][height];

        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (tiles[z][x][y] != Tile.WALL && regions[z][x][y] == 0) {
                        int size = fillRegion(nextRegion++, z, x, y);

                        if (size < 25) {
                            removeRegion(nextRegion - 1, z);
                        }
                    }
                }
            }
        }
        return this;
    }

    private void removeRegion(int region, int z) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (regions[z][x][y] == region) {
                    regions[z][x][y] = 0;
                    tiles[z][x][y] = Tile.WALL;
                }
            }
        }
    }

    private int fillRegion(int region, int z, int x, int y) {
        int size = 1;
        ArrayList<Point> open = new ArrayList<>();
        open.add(new Point(z, x, y));
        regions[z][x][y] = region;

        while (!open.isEmpty()) {
            Point p = open.remove(0);

            for (Point neighbor : p.neighbors8()) {
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height ) {
                    continue;
                }
                if (regions[neighbor.z][neighbor.x][neighbor.y] > 0
                        || tiles[neighbor.z][neighbor.x][neighbor.y] == Tile.WALL) {
                    continue;
                }

                size++;
                regions[neighbor.z][neighbor.x][neighbor.y] = region;
                open.add(neighbor);
            }
        }
        return size;
    }

    public WorldBuilder connectRegions() {
        for (int z = 0; z < depth - 1; z++) {
            connectRegionsDown(z);
        }
        return this;
    }

    private void connectRegionsDown(int z) {
        List<String> connected = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String region = regions[z][x][y] + "," + regions[z + 1][x][y];
                if (tiles[z][x][y] == Tile.FLOOR
                        && tiles[z + 1][x][y] == Tile.FLOOR
                        && !connected.contains(region)) {
                    connected.add(region);
                    connectRegionsDown(z, regions[z][x][y], regions[z + 1][x][y]);
                }
            }
        }
    }

    private void connectRegionsDown(int z, int r1, int r2) {
        List<Point> candidates = findRegionOverlaps(z, r1, r2);

        int stairs = 0;
        do {
            Point p = candidates.remove(0);
            tiles[z][p.x][p.y] = Tile.STAIRSDOWN;
            tiles[z + 1][p.x][p.y] = Tile.STAIRSUP;
            stairs++;
        } while (candidates.size() / stairs > 250);
    }

    public List<Point> findRegionOverlaps(int z, int r1, int r2) {
        ArrayList<Point> candidates = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[z][x][y] == Tile.FLOOR
                        && tiles[z + 1][x][y] == Tile.FLOOR
                        && regions[z][x][y] == r1
                        && regions[z + 1][x][y] == r2) {
                    candidates.add(new Point(z, x, y));
                }
            }
        }

        Collections.shuffle(candidates);
        return candidates;
    }
}
