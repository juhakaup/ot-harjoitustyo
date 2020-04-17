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
    
    private transient final LevelDao levelDao;

    public Pack(LevelDao levelDao) {
        this.levelDao = levelDao;
    }

    public String getId() {
        return this.packId;
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
    
    void unlock(String id) {
        if (unlocks.containsKey(id)) {
            for(String level : unlocks.get(id)) {
                this.levels.put(level, State.AVAILABLE);
            }
        }
    }

}
