package entities;

import utilities.JsonSerializable;

import java.time.LocalDateTime;

public class Log extends JsonSerializable {

    private final LocalDateTime time;
    private final String message;

    public Log(String message) {
        this.time = LocalDateTime.now();
        this.message = message;
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
