package tasata.domain;

import java.util.ArrayList;

/**
 * This class represents a single tile element of a level
 */

public class Tile {
    private int value;
    private String id;
    private final ArrayList<Tile> adjacentTiles;
    
    public Tile(String id, int value) {
        this.id = id;
        this.value = value;
        adjacentTiles = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public void addAdjecentTile(Tile tile) {
        adjacentTiles.add(tile);
    }

    /**
     * Disperses the tiles value to it's adjacent tiles by incrementing the
     * neighboring tiles values by one and decrementing the tiles value by
     * the amount of adjacent tiles 
     */
    public void disperseTile() {
        for (Tile tile : adjacentTiles) {
            tile.setValue(tile.getValue() + 1);
        }
        this.value = this.value -= adjacentTiles.size();
    }
    
}
