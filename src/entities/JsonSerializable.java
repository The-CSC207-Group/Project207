package entities;

import org.json.*;


public interface JsonSerializable<T> {

    public JSONObject encodeToJson();
}
