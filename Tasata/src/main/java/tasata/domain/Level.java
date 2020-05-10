package tasata.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a single level of the game
 */
public class Level {
    private final ArrayList<Tile> tileSet;
    private final String levelId;
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
    
    public int getMoves() {
        return this.moves;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Creates the connections between tiles of a level
     * 
     * @param connections array of vectors representing the connections between the tiles
     * @return true if connections were build, false otherwise
     */
    
    public boolean createTileConnections(String[][] connections) {
        for (String[] connection : connections) {
            Tile tileA = getTile(connection[0]);
            Tile tileB = getTile(connection[1]);
            if (tileA == null || tileB == null) {
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
    
    /**
     * Generates a map of tile id's and values
     * 
     * @return Map of string and integer pairs representing tile id and value
     */
    
    public Map<String, Integer> getValues() {
        Map<String, Integer> values = new HashMap<>();
        for (Tile tile : tileSet) {
            values.put(tile.getId(), tile.getValue());
        }
        return values;
    }
    
    /**
     * Return a tile corresponding to a given id
     * 
     * @param id tile id
     * @return Tile if id was found, null otherwise
     */
    
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
