package tasata.domain;

import java.util.ArrayList;
import java.util.Map;
import tasata.dao.LevelDao;
import tasata.dao.PackDao;

/**
 * Handles the game logic and scoring
 * 
 */

public class Game implements EventListener {
    private Level currentLevel;
    private Pack currentPack;
    private final LevelDao levelDao;
    private final PackDao packDao;
    private int playerMoves;
    private final ArrayList<EventListener> listeners;
    private int goldTier;
    private int silverTier;
    private int bronzeTier;

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

    public Map<String, State> getPackState() {
        return this.currentPack.getPackState();
    }
    
    /**
     * Find and load a level-pack from the current pack dao
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
     * Find and load a level by level id from the current level dao
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
        playerMoves = 0;
        generateScoreTiers();
        notifyListeners(GameEvent.LEVEL_LOADED, levelId);
        updateLevelState();
        return true;
    }


    /**
     * Check if all the tiles in the current level have the same value
     * thus implying that the level is solved
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
     * generates the tiers for the amount of moves needed to get certain score
     * this is based on optimal moves value in current level and multipliers
     * set for the current level-pack
     */
    
    public void generateScoreTiers() {
        int optimalMoves = currentLevel.getMoves();
        goldTier   = (int) (currentPack.getGoldLevel()   * optimalMoves);
        silverTier = (int) (currentPack.getSilverLevel() * optimalMoves);
        bronzeTier = (int) (currentPack.getBronzeLevel() * optimalMoves);
    }
    
    /**
     * Return the score of the level based on moves made at given time
     * 
     * @return level state as Enumerator
     */
    
    public State getScore() {
        if (playerMoves <= goldTier) {
            return State.GOLD;
        } else if (playerMoves <= silverTier) {
            return State.SILVER;
        } else if (playerMoves <= bronzeTier) {
            return State.BRONZE;
        }
        return null;
    }
    
    /**
     * Performs a tile press on given tile updating the level state 
     * 
     * @param id
     * @return 
     */

    public void tilePressed(String id) {
        Tile tile = currentLevel.getTile(id);
        if (tile == null) {
            return;
        }
        tile.disperseTile();
        playerMoves++;
        updateLevelState();
    }
    
    private void updateLevelState() {
        String moves = String.valueOf(playerMoves);
        String state = String.valueOf(currentPack.getPackState().get(currentLevel.getId()));
        notifyListeners(GameEvent.MOVE_COUNT_UPDATED, new String[]{moves, getScore().toString()});
        notifyListeners(GameEvent.TILE_CHANGE, currentLevel.getValues());
        if (isSolved()) {
            if (getScore().ordinal() > currentPack.getLevelState(currentLevel.getId()).ordinal()) {
                currentPack.setLevelState(currentLevel.getId(), getScore());
            }
            currentPack.unlock(currentLevel.getId());
            notifyListeners(GameEvent.PACK_STATE_UPDATE, currentPack.getPackState());
            notifyListeners(GameEvent.LEVEL_SOLVED, currentLevel.getId());
            packDao.saveProgress(currentPack);
        }
    }

    
    // Observer pattern methods
    
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
                if (args instanceof String) {
                    tilePressed((String) args);
                }
                break;
            case RESET_LEVEL:
                loadLevel(currentLevel.getId());
                break;
            case LOAD_LEVEL:
                if (args instanceof String) {
                    loadLevel((String) args);
                }
                break;
            default:
                break;
        }
    }
    
}
