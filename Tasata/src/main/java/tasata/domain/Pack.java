package tasata.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Pack extends Level {

    private Map<Integer, Map<String, String[]>> unlocks;
    private ArrayList<Level> levels;
    private Map<Level, State> state;

    public Pack(String id) {
        super(id);
        this.levels = new ArrayList<>();
        this.unlocks = new HashMap<>();
        this.state = new HashMap<>();

        this.unlocks.put(1, new HashMap<String, String[]>() {
            {
                put("A01", new String[]{"A02"});
            }
        });
        this.unlocks.put(2, new HashMap<String, String[]>() {
            {
                put("A02", new String[]{"A03"});
            }
        });
    }
    
    public void addLevel(Level level) {
        this.levels.add(level);
        this.state.put(level, State.LOCKED);
    }
    
    public ArrayList<Level> getLevels() {
        return this.levels;
    }
    
    public State getState(Level level) {
        if(this.state.containsKey(level)) {
            return this.state.get(level);
        }
        return null;
    }
    
}
