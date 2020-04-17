
package tasata.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import tasata.domain.Pack;

/**
 *
 */
public class FilePackDao implements PackDao {
    
    private final File levelFile;
    private final File progressFile;
    private Pack[] packs;
    private static final Gson GSON = new Gson();
    private final FileLevelDao levels;
    private static FileWriter fileWriter;
    
    public FilePackDao(String levelFileLocation, String progressFileLocation, FileLevelDao levels) {
        this.levels = levels;
        levelFile = new File(levelFileLocation);
        progressFile = new File(progressFileLocation);
        
        if (!levelFile.exists()) {
            System.out.println("Level file not found");
        }
        
        packs = readFile(levelFile);
        
        if (progressFile.exists()) {
            Pack[] packProgress = readFile(progressFile);
            
            for (int i = 0; i < packs.length; i++) {
                for (Pack p : packProgress) {
                    if (packs[i].getId().equals(p.getId())) {
                        packs[i] = p;
                    }
                }
            }
        }
        
//        if (progressFile.exists()) {
//            packs = readFile(progressFile);
//        } else {
//            packs = readFile(levelFile);
//        }
        
    }
    
    private Pack[] readFile(File file) {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            JsonReader jsonReader = new JsonReader(reader);
            
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Pack.class, new PackInstanceCreator(levels));
            
            Gson customGson = builder.create();
            
            return customGson.fromJson(jsonReader, Pack[].class);

        } catch (Exception e) {
            System.out.println("error in file level dao");
        }
        return null;
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

    @Override
    public void saveProgress(Pack newPack) {
        Pack[] progressPacks;
        
        try {
            if (progressFile.exists()) {
                progressPacks = readFile(progressFile);
                for (int i = 0; i < progressPacks.length; i++) {
                    if (progressPacks[i].getId().equals(newPack.getId())) {
                        progressPacks[i] = newPack;
                    }
                } 
            } else {
                progressPacks = new Pack[]{newPack};
            }
  
            fileWriter = new FileWriter(progressFile);
            Gson gson = new Gson();
            gson.toJson(progressPacks, fileWriter);
            fileWriter.close();
            
        } catch (IOException e) {
            
        }
    }
    
}