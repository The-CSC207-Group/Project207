package database;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.*;
import utilities.JsonSerializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;


/**
 * Implements the DataMapperGateway Interface with a JSON Backend.
 *
 * @param <T> Objects of type T stored in this table/database.
 */
public class JsonDatabase<T extends JsonSerializable> implements DataMapperGateway<T> {

    private final HashMap<Integer, T> database;
    private final Class<T> type;
    private final Gson gson;
    private final KeyDelegator keyDelegator;
    private final File folder;

    /**
     * Constructs a JsonDatabase with a default KeyDelegator.
     *
     * @param type   Type of Class the database saves/loads.
     * @param folder Folder where the database is saved.
     */
    public JsonDatabase(Class<T> type, File folder) {
        this(type, new KeyDelegator(), folder);
    }

    /**
     * Constructs a JsonDatabase.
     *
     * @param type         Type of Class the database saves/loads.
     * @param keyDelegator KeyDelegator linked to this database.
     * @param folder       Folder where the database is saved.
     */
    public JsonDatabase(Class<T> type, KeyDelegator keyDelegator, File folder) {
        this.type = type;

        if (keyDelegator.getUniqueFieldMethodName() != null) {
            keyDelegator.setUniqueFieldMethod(getMethodByName(keyDelegator.getUniqueFieldMethodName()));
        }

        GsonBuilder builder = Converters.registerAll(new GsonBuilder());

        this.gson = builder.create();
        this.database = new HashMap<>();
        this.keyDelegator = keyDelegator;
        this.folder = folder;
        load();
    }

    /**
     * Gets a Method in the entity T by its name.
     * Used to initialize KeyDelegator with a unique Field as key.
     *
     * @param name Name of Method as String.
     * @return Method in T corresponding to the given name.
     */
    private Method getMethodByName(String name) {
        try {
            return type.getMethod(name);
        } catch (NoSuchMethodException ignored) {
            return null;
        }
    }

    /**
     * Creates a file name based on the name of the entity T.
     *
     * @return File where this database is stored.
     */
    private File getSaveFile() {
        String fileName = type.getSimpleName() + ".json";
        return Paths.get(folder.toString(), fileName).toFile();
    }

    /**
     * Loads the database from the user's filesystem.
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public HashSet<Integer> getAllIds() {
        return new HashSet<>(database.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(Integer id) {
        return database.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<T> stream() {
        return getAllIds().stream().map(this::get);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Integer id) {
        if (database.containsKey(id)) {
            keyDelegator.removeItem(database.get(id));
            database.remove(id);
            return true;
        } else {
            return false;
        }
    }

}
