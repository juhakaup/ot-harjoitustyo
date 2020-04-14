
package tasata.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import tasata.domain.Pack;

/**
 *
 */
public class FilePackDao implements PackDao {
    
    File file;
    Pack[] packs;
    private static final Gson GSON = new Gson();
    
    public FilePackDao(String fileLocation, FileLevelDao levels) {
        file = new File(fileLocation);
        if (!file.exists()) {
            System.out.println("No such file");
        }
        
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            JsonReader jsonReader = new JsonReader(reader);
            
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Pack.class, new PackInstanceCreator(levels));
            
            Gson customGson = builder.create();
            
            packs = customGson.fromJson(jsonReader, Pack[].class);

        } catch (Exception e) {
            System.out.println("error in file level dao");
        }
    }

    @Override
    public Pack findPackById(String id) {
        for (Pack pack : packs) {
            if (pack.getId().equals(id)) {
                return pack;
            }
        }
        return null;
    }
    
}