package tasata.domain;

import java.util.ArrayList;
import java.util.Map;
import tasata.dao.LevelDao;

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
    private transient final LevelDao levelDao;

    public Pack(LevelDao levelDao) {
        this.levelDao = levelDao;
    }

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
    
    public void setLevelState(String id, State state) {
        if (levels.containsKey(id)) {
            levels.put(id, state);
        }
    }
    
    public State getLevelState(String id) {
        if (levels.containsKey(id)) {
            return levels.get(id);
        }
        return null;
    }

    public ArrayList<String[]> getLevels() {
        ArrayList<String[]> list = new ArrayList<>();
        for (String key : this.levels.keySet()) {
            list.add(new String[]{key, key + ": " + levelDao.findLevelById(key).getDescription()});
        }
        return list;
    }

    public Map<String, State> getPackState() {
        return this.levels;
    }

    /**
     * Sets the status of the levels associated to given level to available
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
