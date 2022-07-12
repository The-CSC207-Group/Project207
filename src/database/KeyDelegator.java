package database;

import java.lang.reflect.Method;
import java.util.HashSet;


public class KeyDelegator {

    private int lastId;

    private String uniqueFieldMethodName;
    private Method uniqueFieldMethod;
    private HashSet<Object> uniqueFieldObjects;

    public KeyDelegator() {
        this.lastId = 0;
    }

    public KeyDelegator(String uniqueFieldMethodName) {
        this();
        this.uniqueFieldMethodName = uniqueFieldMethodName;
        this.uniqueFieldObjects = new HashSet<>();
    }

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

    public void setUniqueFieldMethod(Method uniqueFieldMethod) {
        this.uniqueFieldMethod = uniqueFieldMethod;
    }

    public String getUniqueFieldMethodName() {
        return uniqueFieldMethodName;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public Integer getNewUniqueId() {
        int uniqueId = lastId;
        lastId += 1;
        return uniqueId;
    }
}
