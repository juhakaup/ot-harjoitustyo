package tasata.dao;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import tasata.domain.Level;

/**
 * This class handles the level loading from a file
 */
public class FileLevelDao implements LevelDao {

    private static File file;
    private Level[] levels;
    private static final Gson GSON = new Gson();

    public FileLevelDao(String fileLocation) throws Exception {
        file = new File(fileLocation);
        if (!file.exists()) {
            System.out.println("No such file");
        }
    }

    @Override
    public Level findLevelById(String id) {

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
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
