package entities;

import java.time.LocalDateTime;

public class Log {

    private final int id;
    private final LocalDateTime time;
    private final String message;

    public Log(int id, String message) {
        this.id = id;
        this.time = LocalDateTime.now();
        this.message = message;
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

}
