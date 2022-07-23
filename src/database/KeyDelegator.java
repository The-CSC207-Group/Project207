package database;

import java.lang.reflect.Method;
import java.util.HashSet;


/**
 * KeyDelegator is responsible for giving unique ids and not allowing uniqueFields to be added/present twice.
 */
public class KeyDelegator {

    private int lastId;
    private String uniqueFieldMethodName;
    private Method uniqueFieldMethod;
    private HashSet<Object> uniqueFieldObjects;

    /**
     * No args Constructor used to initialize a database relation with only id as unique key.
     */
    public KeyDelegator() {
        this.lastId = 0;
    }

    /**
     * Constructor used to initialize a database relation with id and a Field as unique keys.
     * @param uniqueFieldMethodName returns
     */
    public KeyDelegator(String uniqueFieldMethodName) {
        this();
        this.uniqueFieldMethodName = uniqueFieldMethodName;
        this.uniqueFieldObjects = new HashSet<>();
    }

    /**
     * Checks if item can be added to database without violating the unique key restrictions.
     * @param item Item to be added.
     * @return boolean whether the item can be added.
     */
    public boolean canAddItem(Object item) {
        if (uniqueFieldMethod != null) {
            Object result = null;
            try {
                result = uniqueFieldMethod.invoke(item);
            } catch (Exception ignored) {}

            if (result != null) {
                if (uniqueFieldObjects.contains(result)) {
                    return false;
                } else {
                    uniqueFieldObjects.add(result);
                }
            }
        }
        return true;
    }

    /**
     * Remove the items unique field from the set of unique fields during the removal of that item.
     * @param item A database entity.
     */
    public void removeItem(Object item) {
        if (uniqueFieldMethod != null) {
            Object result = null;
            try {
                result = uniqueFieldMethod.invoke(item);
            } catch (Exception ignored) {}

            if (result != null) {
                uniqueFieldObjects.remove(result);
            }
        }
    }

    /**
     * Set the method to use to get the unique Field.
     * @param uniqueFieldMethod Method used to get the field of an Entity.
     */
    public void setUniqueFieldMethod(Method uniqueFieldMethod) {
        this.uniqueFieldMethod = uniqueFieldMethod;
    }

    /**
     * Gets the name of the method to use to get the unique Field.
     * @return String representation of the method to use to get the unique Field.
     */
    public String getUniqueFieldMethodName() {
        return uniqueFieldMethodName;
    }

    /**
     * Getter for the last unique id.
     * @return int representing the last unique id.
     */
    public int getLastId() {
        return lastId;
    }

    /**
     * Setter for the last unique id.
     * @param lastId int representing the last unique id.
     */
    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    /**
     * Gets a new unique id to use in the database.
     * @return Integer representing a unique id.
     */
    public Integer getNewUniqueId() {
        int uniqueId = lastId;
        lastId += 1;
        return uniqueId;
    }
}
