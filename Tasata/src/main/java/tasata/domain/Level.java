package tasata.domain;

import java.util.ArrayList;

/**
 * This class represents a single level of the game
 */
public class Level {
    private ArrayList<Tile> tileSet;
    private final String levelId;
    private int[][] connections;
    
    public Level(String id) {
        levelId = id;
        tileSet = new ArrayList<>();
    }
    
    public void addTile(Tile tile) {
        tileSet.add(tile);
    }
    
    /**
     * Creates the connections between tiles in a level
     * @param connections array of vectors representing the connections between the tiles
     */
    public void createTileConnections(int[][] connections) {
        this.connections = connections;
        for (int[] connection : connections) {
            Tile tileA = tileSet.get(connection[0]);
            Tile tileB = tileSet.get(connection[1]);
            tileA.addAdjecentTile(tileB);
            tileB.addAdjecentTile(tileA);
        }
    }
    
    public Tile getTile(int index) {
        return tileSet.get(index);
    }
    
    public ArrayList<Tile> getTileSet() {
        return this.tileSet;
    }
    
    public int[][] getConnections() {
        return this.connections;
    }
    
}
