package tasata.domain;

import tasata.dao.LevelDao;

/**
 * This class handles the game logic and services
 */

public class Game {
    private Level currentLevel;
    private LevelDao levelDao;
    
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
        if (levelDao.findLevelById(levelId) != null) {
            this.currentLevel = levelDao.findLevelById(levelId);
            return true;
        }
        return false;
    }
    
    public boolean incrementTile(int index) {
        if(currentLevel.getTile(index) != null) {
            currentLevel.getTile(index).disperseTile();
            return true;
        }
        return false;
    }
}
