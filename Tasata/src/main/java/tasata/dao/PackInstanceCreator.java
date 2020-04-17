
package tasata.dao;

import com.google.gson.InstanceCreator;
import java.lang.reflect.Type;
import tasata.domain.Pack;

public class PackInstanceCreator implements InstanceCreator <Pack> {
    
    private final LevelDao levelDao;
    
    public PackInstanceCreator (LevelDao levelDao) {
        this.levelDao = levelDao;
    }

    @Override
    public Pack createInstance(Type type) {
        
        Pack pack = new Pack(levelDao);
        
        return pack;
    }
}
