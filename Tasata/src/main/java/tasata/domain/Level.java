package tasata.domain;

import java.util.ArrayList;

/**
 * This class represents a single level of the game
 */
public class Level {
    private ArrayList<Tile> tileSet;
    private String levelId;
    //private int[][] connections;
    
    private String description;
    private String[][] connections;
    private int moves;
    
    
    public Level(String id) {
        levelId = id;
        tileSet = new ArrayList<>();  
    }
    
    public void addTile(Tile tile) {
        tileSet.add(tile);
    }
    
    public String getId() {
        return this.levelId;
    }
    
    /**
     * Creates the connections between tiles in a level
     * @param connections array of vectors representing the connections between the tiles
     * @return true if connections were build, false otherwise
     */
    
    public boolean createTileConnections(String[][] connections) {
        for (String[] connection : connections) {
            Tile tileA = getTile(connection[0]);
            Tile tileB = getTile(connection[1]);
            if(tileA == null || tileB == null) {
                return false;
            }
            tileA.addAdjecentTile(tileB);
            tileB.addAdjecentTile(tileA);
        }
        this.connections = connections;
        return true;
    }
    
    public boolean createTileConnections() {
        return createTileConnections(this.connections);
    }
    
    public void setTileConnections(String[][] connections) {
        this.connections = connections;
    }
    
    public ArrayList<Tile> getTileSet() {
        return this.tileSet;
    }
    
    public Tile getTile(String id) {
        for (Tile tile : tileSet) {
            if (tile.equals(id)) {
                return tile;
            }
        }
        return null;
    }
    
    public String[][] getConnections() {
        return this.connections;
    }
    
    @Override
    public boolean equals(Object o) {
        return this.levelId.equals(o);
    }
    
}
