package tasata.dao;

import tasata.domain.Level;
import tasata.domain.Tile;

/**
 * This is a class for testing out the game without loading data from files
 */
public class FakeLevelDao implements LevelDao {

    @Override
    public Level findLevelById(String id) {
        Level level = new Level(id);
        
        level.addTile(new Tile(1, 4));
        level.addTile(new Tile(2, 0));
        level.addTile(new Tile(3, 0));
        level.addTile(new Tile(4, 0));
        
        level.createTileConnections(new Integer[][]{
            {0,0,2,0,0,0}, //1
            {3,0,0,0,0,1}, //2
            {0,0,4,2,0,0}, //3
            {0,0,0,0,0,3}  //4
        });
        
        return level;
    }
    
}
