
package tasata.dao;

import tasata.domain.Pack;

public interface PackDao {
    
    /**
     * Finds a pack with given identifier
     * 
     * @param id pack id
     * @return Pack if id is found, null otherwise
     */
    
    Pack findPackById(String id);
    
    /**
     * Stores the state of given pack in a separate file
     * 
     * @param pack to be saved
     */
    
    void saveProgress(Pack pack);
    
}
