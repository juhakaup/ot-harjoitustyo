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

public class FilePackDao implements PackDao {

    private final File progressFile;
    private final Pack[] packs;
    private final LevelDao levels;
    private static FileWriter fileWriter;
    private final String packFileLocation;
    private final String progressFileLocation;

    public FilePackDao(String packFileLocation, String progressFileLocation, LevelDao levels) {
        this.levels = levels;
        this.packFileLocation = packFileLocation;
        this.progressFileLocation = progressFileLocation;

        packs = readFile(packFileLocation);

        progressFile = new File(progressFileLocation);
        if (progressFile.exists()) {
            Pack[] packProgress = readFile(progressFile);
            for (int i = 0; i < packs.length; i++) {
                for (Pack pack : packProgress) {
                    if (packs[i].getId().equals(pack.getId())) {
                        packs[i] = pack;
                    }
                }
            }
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

    private Pack[] readFile(Object file) {
        InputStreamReader reader;
        try {
            reader = getReader(file);
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

    private InputStreamReader getReader(Object file) throws Exception {
        if (file instanceof String) {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            return new InputStreamReader(classLoader.getResourceAsStream((String) file));
        } else if (file instanceof File) {
            return new InputStreamReader(new FileInputStream((File) file), "UTF-8");
        }
        return null;
    }

    @Override
    public void saveProgress(Pack newPack) {
        Pack[] progressPacks;
        try {
            if (progressFile.exists()) {
                progressPacks = readFile(progressFileLocation);
                for (int i = 0; i < progressPacks.length; i++) {
                    if (progressPacks[i].getId().equals(newPack.getId())) {
                        progressPacks[i] = newPack;
                    }
                }
            } else {
                progressPacks = new Pack[]{newPack};
            }
            fileWriter = new FileWriter(progressFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(progressPacks, fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error in saving game progress");
        }
    }

}
