package tasata.dao;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.InputStreamReader;
import tasata.domain.Level;

/**
 * This class handles the level loading from a file
 */
public class FileLevelDao implements LevelDao {

    private static String fileLocation;
    private static final Gson GSON = new Gson();

    public FileLevelDao(String fileLocation) throws Exception {
        this.fileLocation = fileLocation;
    }

    @Override
    public Level findLevelById(String id) {
        InputStreamReader reader;
        Level[] levels = null;
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            reader = new InputStreamReader(classLoader.getResourceAsStream(fileLocation));
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
