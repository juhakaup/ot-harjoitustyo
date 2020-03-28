package tasata.domain;

import java.util.ArrayList;

/**
 * This class represents a single level of the game
 */
public class Level {
    private ArrayList<Tile> tileSet;
    private Integer[][] tileConnections;
    private final String levelId;
    
    public Level(String id) {
        levelId = id;
        tileSet = new ArrayList<>();
    }
    
    public void addTile(Tile tile) {
        tileSet.add(tile);
    }
    
    /**
     * Creates the connections between tiles in a level
     * @param matrix 2D matrix representing connections between tiles
     */
    public void setConnections(Integer[][] matrix) {
        this.tileConnections = matrix;
        
        for(int tile = 0; tile < tileConnections.length; tile++) {
            for(int index = 0; index < tileConnections[tile].length; index++) {
                if(tileConnections[tile][index] != 0) {
                    tileSet.get(tile).addAdjecentTile(tileSet.get(tileConnections[tile][index] - 1));
                }
            }
        }
    }
    
    public Tile getTile(int index) {
        return tileSet.get(index);
    }
}
