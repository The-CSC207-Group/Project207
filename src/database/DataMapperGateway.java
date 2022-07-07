package database;

import java.util.HashSet;

public interface DataMapperGateway<T> {
    /**
     * Gets all unique identifiers in the stored in the database.
     * @return A hashset of all ids as a string.
     */
    HashSet<Integer> getAllIds();

    /**
     * Gets an object of type T from the database.
     * @param id The unique identifier of that object.
     * @return Object of type T.
     */
    T get(Integer id);

    /**
     * Adds a new object of type T to the database.
     * @param item The item to add to the database.
     * @return boolean of whether new object T was successfully added to the database.
     *         false is returned when item.id is already present in getAllIds().
     */
    boolean add(T item);

    /**
     * Remove an object of type T from the database.
     * @param id The unique identifier of that object.
     * @return boolean of whether the object was successfully deleted from the database.
     *         false is returned when id is not present in getAllIds().
     */
    boolean remove(Integer id);

    /**
     * Saves the database.
     * Called at the end of the program.
     */
    void save();
}
