package dataBundles;

import javax.xml.crypto.Data;

public abstract class DataBundle {
    // id is null if controller passes data bundle to use cases, otherwise id is set
    Integer id = null;
    public DataBundle(Integer id){
        this.id = id;
    }
    public DataBundle(){
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
}
