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
        
//        level.addTile(new Tile(0, 4));
//        level.addTile(new Tile(1, 0));
//        level.addTile(new Tile(2, 0));
//        level.addTile(new Tile(3, 0));
//        
//        level.createTileConnections(new int[][]{
//            {0,1,3},
//            {1,2,1},
//            {2,3,3}
//        });
        
        level.addTile(new Tile(0, 6));
        level.addTile(new Tile(1, 0));
        level.addTile(new Tile(2, 0));
        level.addTile(new Tile(3, 0));
        level.addTile(new Tile(4, 0));
        level.addTile(new Tile(5, 0));
        
        level.createTileConnections(new int[][]{
            {0,1,3},
            {1,2,1},
            {1,3,2},
            {3,4,1},
            {3,5,3}
        });

//        level.addTile(new Tile(0, 0));
//        level.addTile(new Tile(1, 10));
//        level.addTile(new Tile(2, 0));
//        level.addTile(new Tile(3, 0));
//        level.addTile(new Tile(4, 0));
//        level.addTile(new Tile(5, 0));
//        level.addTile(new Tile(6, 0));
//        level.addTile(new Tile(7, 0));
//        level.addTile(new Tile(8, 0));
//        level.addTile(new Tile(9, 0));
//        
//        level.createTileConnections(new int[][]{
//            {0,1,3},
//            {1,2,4},
//            {1,3,1},
//            {3,4,3},
//            {1,4,2},
//            {4,5,3},
//            {5,6,1},
//            {5,7,4},
//            {7,8,5},
//            {7,9,4}
//        });
            
        return level;
    }
    
}
