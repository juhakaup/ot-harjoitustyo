
package tasata.dao;

import tasata.domain.Pack;

/**
 *
 */
public interface PackDao {
    
    Pack findPackById(String id);
    
    void saveProgress(Pack pack);
    
}
