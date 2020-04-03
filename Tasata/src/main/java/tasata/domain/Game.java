package tasata.domain;

import java.util.ArrayList;
import tasata.dao.LevelDao;

/**
 * This class handles the game logic and services
 */

public class Game {
    private Level currentLevel;
    private LevelDao levelDao;
    private int moves;
    
    public Game(LevelDao levelDao) {
        this.levelDao = levelDao;
    }
    
    /**
     * Find and load level by level id
     * 
     * @param levelId level to be loaded
     * @return true if level loaded, false otherwise
     */
    
    public boolean loadLevel(String levelId) {
        Level level = levelDao.findLevelById(levelId);
        if (level != null) {
            currentLevel = level;
            moves = 0;
            return true;
        }
        return false;
    }
    
    public Level getCurrentLevel() {
        return this.currentLevel;
    }
    
    public void incrementMoves() {
        moves++;
    }
    
    public int getMoves() {
        return moves;
    }
    
    /**
     * Check if all the tiles in the current level have the same value
     * 
     * @return true if level is solved, false otherwise
     */
    
    public boolean isSolved() {
        ArrayList<Tile> tiles = currentLevel.getTileSet();
        int value = tiles.get(0).getValue();
        for (int i = 1; i < tiles.size(); i++) {
            if(tiles.get(i).getValue() != value) return false;
        }
        return true;
    }
}
