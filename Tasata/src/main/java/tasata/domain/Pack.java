package tasata.domain;

import java.util.ArrayList;

/**
 * 
 */
public class Pack {
    private String id;
    private ArrayList<Level> levels;
    private ArrayList<String[]> unlockQueue;
    
    public Pack(String id) {
        this.id = id;
        levels = new ArrayList<>();
        unlockQueue = new ArrayList<>();
    }
    
    public void addLevel(Level level) {
        this.levels.add(level);
    }
    
    public void addUnlockStep(String[] levels) {
        this.unlockQueue.add(levels);
    }
}
