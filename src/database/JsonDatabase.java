package database;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.*;
import entities.JsonSerializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;


public class JsonDatabase<T extends JsonSerializable> implements DataMapperGateway<T> {

    private final HashMap<Integer, T> database;
    private final Class<T> type;
    private final Gson gson;
    private final KeyDelegator keyDelegator;
    private final File folder;

    public JsonDatabase(Class<T> type, File folder) {
        this(type, new KeyDelegator(), folder);
    }

    public JsonDatabase(Class<T> type, KeyDelegator keyDelegator, File folder) {
        if (keyDelegator.getUniqueFieldMethodName() != null) {
            Method method = null;
            try {
                method = type.getMethod("getUsername");
            } catch (Exception ignored) {}
            keyDelegator.setUniqueFieldMethod(method);
        }

        this.type = type;
        GsonBuilder builder = Converters.registerAll(new GsonBuilder());
        this.gson = builder.setPrettyPrinting().create();
        this.database = new HashMap<>();
        this.keyDelegator = keyDelegator;
        this.folder = folder;
        load();
    }

    private File getSaveFile() {
        String fileName = type.getSimpleName() + ".json";
        return Paths.get(folder.toString(), fileName).toFile();
    }

    private void load() {
        // noinspection ResultOfMethodCallIgnored
        folder.mkdirs();

        if (getSaveFile().exists()) {
            String jsonCode;

            try {
                jsonCode = Files.readString(getSaveFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException("malfunctioning system");
            }

            JsonObject jsonObject = JsonParser.parseString(jsonCode).getAsJsonObject();
            for (String key : jsonObject.keySet()) {
                if (key.equals("last_id")) {
                    int last_id = Math.max(jsonObject.get(key).getAsInt(), keyDelegator.getLastId());
                    keyDelegator.setLastId(last_id);
                } else {
                    Integer actualKey = Integer.parseInt(key);
                    T object = gson.fromJson(jsonObject.get(key), type);
                    if (keyDelegator.canAddItem(object)) {
                        database.put(actualKey, object);
                    } else {
                        throw new RuntimeException("broken database: duplicate unique fields");
                    }
                }
            }
        }
    }

    @Override
    public void save() {
        JsonElement jsonElement = gson.toJsonTree(database);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty("last_id", keyDelegator.getLastId());

        try {
            // noinspection ResultOfMethodCallIgnored
            getSaveFile().createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("cannot create file " + getSaveFile());
        }

        try (PrintWriter out = new PrintWriter(getSaveFile())) {
            out.println(jsonObject);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("malfunctioning system");
        }
    }

    @Override
    public HashSet<Integer> getAllIds() {
        return new HashSet<>(database.keySet());
    }

    @Override
    public T get(Integer id) {
        return database.get(id);
    }

    @Override
    public Integer add(T item) {
        if (keyDelegator.canAddItem(item)) {
            int unique_id = keyDelegator.getNewUniqueId();
            database.put(unique_id, item);
            item.setId(unique_id);
            return item.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean remove(Integer id) {
        if (database.containsKey(id)) {
            database.remove(id);
            return true;
        } else {
            return false;
        }
    }

}
