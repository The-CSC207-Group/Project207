package database;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
     * Gets copy of an object of type T from the database.
     * The copy is not linked to the database and therefore cannot modify it.
     * @param id The unique identifier of that object.
     * @return Object of type T.
     */
    T copy(Integer id);

    /**
     * Access items in database as a stream.
     * @return Object of type T.
     */
    Stream<T> stream();

    /**
     * Adds a new object of type T to the database.
     * @param item The item to add to the database.
     * @return an integer id is returned.
     */
    Integer add(T item);

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

    default Optional<T> getByCondition(Predicate<T> condition){
        return stream().filter(condition).findFirst();
    }
}