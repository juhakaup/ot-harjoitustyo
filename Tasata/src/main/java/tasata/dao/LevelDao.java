package tasata.dao;

import tasata.domain.Level;

public interface LevelDao {
    
    /**
     * Creates a new instance of given level
     * 
     * @param id level to be created
     * @return new instance of a level by given id, null if id not found
     */
    
    Level findLevelById(String id);
}
