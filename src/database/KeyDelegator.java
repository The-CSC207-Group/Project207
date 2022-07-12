package database;

public class KeyDelegator {

    private int lastId;

    public KeyDelegator() {
        this.lastId = 0;
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
