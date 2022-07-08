package entities;

import org.json.JSONObject;
import java.time.LocalDateTime;

public class Log implements JsonSerializable<Log> {

    private final int id;
    private final LocalDateTime time;
    private final String message;

    public Log(int id, String message) {
        this.id = id;
        this.time = LocalDateTime.now();
        this.message = message;
    }

    public Log(JSONObject json) {
        this.id = (int) json.get("id");
        this.time = LocalDateTime.parse((String) json.get("time"));
        this.message = (String) json.get("message");
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "Time: " + getTime() + ", Message: " + getMessage();
    }

    public JSONObject encodeToJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("time", time.toString());
        json.put("message", message);
        return json;
    }
}
