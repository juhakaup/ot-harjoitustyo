package tasata.domain;

import java.util.ArrayList;
import java.util.Map;
import tasata.dao.LevelDao;
import tasata.dao.PackDao;

/**
 * This class handles the game logic and services
 * 
 */

public class Game implements EventListener {
    private Level currentLevel;
    private Pack currentPack;
    private final LevelDao levelDao;
    private final PackDao packDao;
    private int playerMoves;
    private final ArrayList<EventListener> listeners;

    public Game(LevelDao levelDao, PackDao packDao) throws Exception {
        this.levelDao = levelDao;
        this.packDao = packDao;
        this.listeners = new ArrayList<>();
    }
    
    public Level getCurrentLevel() {
        return this.currentLevel;
    }
    
    public ArrayList<String[]> getCurrentLevels() {
        return currentPack.getLevels();
    }

    public Map<String, State> getLevelsState() {
        return this.currentPack.getPackState();
    }
    
    /**
     * Method finds and loads a level pack from the current pack dao
     * 
     * @param id Pack id as a String
     * @return true if pack is loaded, false otherwise
     */
    
    public boolean loadLevelPack(String id) {
        Pack pack = packDao.findPackById(id);
        if (pack == null) {
            return false;
        }
        currentPack = pack;
        return true;
    }

    /**
     * Find and load level by level id from the current level dao
     *
     * @param levelId id of the level to be loaded as a String
     * @return true if level loaded, false otherwise
     */
    
    public boolean loadLevel(String levelId) {
        Level level = levelDao.findLevelById(levelId);
        if (level == null) {
            return false;
        }
        currentLevel = level;
        notifyListeners(GameEvent.LEVEL_LOADED, levelId);
        return true;
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
            if (tiles.get(i).getValue() != value) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Performs a tile press on given tile updating the level state 
     * 
     * @param id
     * @return 
     */

    private boolean tilePressed(String id) {
        Tile tile = currentLevel.getTile(id);
        if (tile == null) {
            return false;
        }
        tile.disperseTile();
        updateLevelState();
        return true;
    }
    
    public void updateLevelState() {
        playerMoves++;
        if (isSolved()) {
            currentPack.unlock(currentLevel.getId());
            notifyListeners(GameEvent.LEVEL_STATE_CHANGE, currentPack.getPackState());
            notifyListeners(GameEvent.LEVEL_SOLVED, currentLevel.getId());
            packDao.saveProgress(currentPack);
        }
    }

    public void addListener(EventListener listener) {
        this.listeners.add(listener);
    }

    private void notifyListeners(GameEvent event, Object attribute) {
        for (EventListener listener : this.listeners) {
            listener.onEvent(event, attribute);
        }
    }

    @Override
    public void onEvent(GameEvent event, Object args) {
        switch (event) {
            case TILE_PRESS:
                if (tilePressed((String) args)) {
                    notifyListeners(GameEvent.TILE_CHANGE, currentLevel.getTileSet());
                }
                break;
            case RESET_LEVEL:
                loadLevel(currentLevel.getId());
                notifyListeners(GameEvent.TILE_CHANGE, "");
                break;
            case LOAD_LEVEL:
                loadLevel((String) args);
                break;
            default:
                break;
        }
    }
    
}
