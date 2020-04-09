package tasata.domain;

import java.util.ArrayList;
import tasata.dao.LevelDao;
import tasata.domain.GameEvent;

/**
 * This class handles the game logic and services
 */
public class Game implements EventListener {
    private Level currentLevel;
    private LevelDao levelDao;
    private int moves;
    private ArrayList<EventListener> listeners;

    public Game(LevelDao levelDao) {
        this.levelDao = levelDao;
        this.listeners = new ArrayList<>();
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
//            notiyListeners("LevelLoaded", levelId);
            return true;
        }
        return false;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
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
            if (tiles.get(i).getValue() != value) {
                return false;
            }
        }
        return true;
    }

    private boolean disperseTile(String id) {
        Tile tile = currentLevel.getTile(id);
        if (tile != null) {
            tile.disperseTile();
            return true;
        }
        return false;
    }

    public void addListener(EventListener listener) {
        this.listeners.add(listener);
    }

    private void notiyListeners(GameEvent event, String attribute) {
        for (EventListener listener : this.listeners) {
            listener.onEvent(event, attribute);
        }
    }

    @Override
    public void onEvent(GameEvent event, String args) {
        switch (event) {
            case TILE_PRESS:
                if (disperseTile(args)) {
                    moves++;
                    notiyListeners(GameEvent.STATE_CHANGE, "");
                }
                break;
            case RESET_LEVEL:
                loadLevel(currentLevel.getId());
                notiyListeners(GameEvent.STATE_CHANGE, "");
                break;
            default:
                break;
        }
    }
    
}
