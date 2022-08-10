package dataBundles;

import utilities.JsonSerializable;

/**
 * Abstract data bundle class that provides general getters to entity data bundles.
 */
public abstract class DataBundle {

    private final JsonSerializable entity;

    /**
     * Constructor. Stores the entity as a private attribute in the class.
     *
     * @param entity JsonSerializable - some entity.
     */
    public DataBundle(JsonSerializable entity) {
        this.entity = entity;
    }

    /**
     * @return Integer - id of the entity. Id can be null when the controller creates a data bundle manually instead
     * of through use cases.
     */
    public Integer getId() {
        return entity.getId();
    }

}
