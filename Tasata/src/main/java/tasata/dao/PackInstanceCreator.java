
package tasata.dao;

import com.google.gson.InstanceCreator;
import java.lang.reflect.Type;
import tasata.domain.Pack;

public class PackInstanceCreator implements InstanceCreator <Pack> {
    
    private final FileLevelDao fileLevelDao;
    
    public PackInstanceCreator (FileLevelDao levelDao) {
        this.fileLevelDao = levelDao;
    }

    @Override
    public Pack createInstance(Type type) {
        
        Pack pack = new Pack(fileLevelDao);
        
        return pack;
    }
}
