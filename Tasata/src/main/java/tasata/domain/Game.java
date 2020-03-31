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
    
    public int[][] getLevelConncetions() {
        if(currentLevel != null) {
            return currentLevel.getConnections();
        }
        return null;
    }
    
    public ArrayList<Tile> getLevelTiles() {
        if(currentLevel != null) {
            return currentLevel.getTileSet();
        }
        return null;
    }
    
    public void incrementMoves() {
        moves++;
    }
    
    public int getMoves() {
        return moves;
    }
    
    public boolean isSolved() {
        ArrayList<Tile> tiles = currentLevel.getTileSet();
        int value = tiles.get(0).getValue();
        for (int i = 1; i < tiles.size(); i++) {
            if(tiles.get(i).getValue() != value) return false;
        }
        return true;
    }
}
