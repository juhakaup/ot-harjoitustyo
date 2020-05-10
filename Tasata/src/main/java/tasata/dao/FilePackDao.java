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
 * Handles loading packs from a file
 *
 */
public class FilePackDao implements PackDao {

    private final File progressFile;
    private Pack[] packs = null;
    private static FileWriter fileWriter;
    private final String packFileLocation;
    private final String progressFileLocation;
    private String protocol;

    public FilePackDao(String packFileLocation, String progressFileLocation) {
        this.packFileLocation = packFileLocation;
        this.progressFileLocation = progressFileLocation;
        protocol = this.getClass().getResource("").getProtocol();

        if (protocol.equals("jar")) {
            packs = readFile(packFileLocation);
        } else if (protocol.equals("file")) {
            File packFile = new File(packFileLocation);
            packs = readFile(packFile);
        }

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

            Gson gson = new Gson();
            Pack[] packs = gson.fromJson(jsonReader, Pack[].class);
            return packs;

        } catch (Exception e) {
            System.out.println("error in file pack dao");
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(progressPacks, fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error in saving game progress");
        }
    }

}
