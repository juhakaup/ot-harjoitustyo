package tasata.dao;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import tasata.domain.Level;

/**
 * This class handles the level loading from a file
 */
public class FileLevelDao implements LevelDao {

    private final String fileLocation;
    private static final Gson GSON = new Gson();
    private final String protocol;

    public FileLevelDao(String fileLocation) throws Exception {
        this.fileLocation = fileLocation;
        protocol = this.getClass().getResource("").getProtocol();
    }

    @Override
    public Level findLevelById(String id) {
        InputStreamReader reader = null;
        Level[] levels = null;
        try {
            if (protocol.equals("jar")) {
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                reader = new InputStreamReader(classLoader.getResourceAsStream(fileLocation));
            } else if (protocol.equals("file")) {
                reader = new InputStreamReader(new FileInputStream(fileLocation));
            }
            
            JsonReader jsonReader = new JsonReader(reader);
            levels = GSON.fromJson(jsonReader, Level[].class);
            for (int i = 0; i < levels.length; i++) {
                levels[i].createTileConnections();
            }
        } catch (Exception e) {
            System.out.println("error in file level dao");
        }

        for (Level level : levels) {
            if (level.equals(id)) {
                return level;
            }
        }
        return null;
    }

}
