package dataBundles;


import utilities.JsonSerializable;

public abstract class DataBundle {
    // id is null if controller passes data bundle to use cases, otherwise id is set
    JsonSerializable entity;
    public DataBundle(JsonSerializable entity){
        this.entity = entity;
    }

    public Integer getId() {
        return entity.getId();
    }
}
