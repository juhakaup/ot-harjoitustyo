package tasata.domain;

import java.util.ArrayList;
import java.util.Map;
import tasata.dao.LevelDao;
import tasata.dao.PackDao;

/**
 * This class handles the game logic and services
 */

public class Game implements EventListener {
    private Level currentLevel;
    private Pack currentPack;
    private final LevelDao levelDao;
    private final PackDao packDao;
    private int moves;
    private ArrayList<String> steps;
    private boolean solved;
    private final ArrayList<EventListener> listeners;

    public Game(LevelDao levelDao, PackDao packDao) throws Exception {
        this.levelDao = levelDao;
        this.packDao = packDao;
        this.listeners = new ArrayList<>();
    }
    
    public boolean loadLevelPack(String id) {
        Pack pack = packDao.findPackById(id);
        if (pack == null) return false;
        
        currentPack = pack;
        return true;
    }

    /**
     * Find and load level by level id
     *
     * @param levelId level to be loaded
     * @return true if level loaded, false otherwise
     */
    public boolean loadLevel(String levelId) {
        Level level = levelDao.findLevelById(levelId);
        if (level == null) return false;
        
        currentLevel = level;
        steps = new ArrayList<>();
        solved = false;
        notifyListeners(GameEvent.LEVEL_LOADED, levelId);
        return true;
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
            steps.add(id);
            
            if(isSolved()) {
                solved = true;
                currentPack.unlock(currentLevel.getId());
                notifyListeners(GameEvent.LEVEL_STATE_CHANGE, currentPack.getPackState());
                notifyListeners(GameEvent.LEVEL_SOLVED, currentLevel.getId());
                storeLevelState();
            }
            return true;
        }
        return false;
    }
    
    private void storeLevelState() {
        System.out.println("store level state unimplemented");
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
                if (disperseTile((String)args)) {
                    moves++;
                    notifyListeners(GameEvent.TILE_CHANGE, currentLevel.getTileSet());
                }
                break;
            case RESET_LEVEL:
                loadLevel(currentLevel.getId());
                notifyListeners(GameEvent.TILE_CHANGE, "");
                break;
            case MENU_SCENE:
                storeLevelState();
                break;
            case LOAD_LEVEL:
                loadLevel((String) args);
                break;
            default:
                break;
        }
    }
    
}
