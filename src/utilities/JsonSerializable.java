package utilities;

/**
 * Parent class of any entity that can be saved to the database.
 */
public abstract class JsonSerializable {

    private Integer id = null;

    /**
     * returns the unique id of the serializable object.
     *
     * @return Integer - unique id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets this object's id to a new one.
     *
     * @param id Integer - new id to replace the old one.
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
