package tasata.domain;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class handles a group of levels, unlocking and storing state of 
 * each level in the pack
 */

public class Pack {

    private String packId;
    private Map<String, State> levels;
    private Map<String, String[]> unlocks;
    private double goldLevel;
    private double silverLevel;
    private double bronzeLevel;

    public String getId() {
        return this.packId;
    }
    
    public double getGoldLevel() {
        return this.goldLevel;
    }
    
    public double getSilverLevel() {
        return this.silverLevel;
    }
    
    public double getBronzeLevel() {
        return this.bronzeLevel;
    }
    
    /**
     * Changes the state of given level
     * 
     * @param id level to be modified
     * @param state new state for the level
     */
    
    public void setLevelState(String id, State state) {
        if (levels.containsKey(id)) {
            levels.put(id, state);
        }
    }
    
    /**
     * Return the current state of a level
     * 
     * @param id level id
     * @return current state of the state, null if level not found
     */
    
    public State getLevelState(String id) {
        if (levels.containsKey(id)) {
            return levels.get(id);
        }
        return null;
    }
    
    /**
     * Returns a list of level id's in the pack
     * 
     * @return list of level id's
     */
    
    public ArrayList<String> getLevels() {
        ArrayList<String> list = new ArrayList<>();
        for (String key : this.levels.keySet()) {
            list.add(key);
        }
        return list;
    }
    
    /**
     * Return the next available level in the pack
     * 
     * @return level if next level is available, null otherwise
     */
    
    public String getNextLevel() {
        for (String level : levels.keySet()) {
            if (levels.get(level) == State.AVAILABLE) {
                return level;
            }
        }
        return null;
    }

    public Map<String, State> getPackState() {
        return this.levels;
    }

    /**
     * Sets the status of the levels associated to given level to available
     * 
     * @param id id of the level that was completed
     */
    
    public void unlock(String id) {
        if (unlocks.containsKey(id)) {
            for (String level : unlocks.get(id)) {
                if (levels.get(level) == State.LOCKED) {
                    this.levels.put(level, State.AVAILABLE);
                }
            }
        }
    }

}
