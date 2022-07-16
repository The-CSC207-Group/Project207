package entities;

import utilities.JsonSerializable;

import java.time.LocalDateTime;

public class Log extends JsonSerializable {
    private final Integer userId;
    private final LocalDateTime time;
    private final String message;

    public Log(Integer userId, String message) {
        this.userId = userId;
        this.time = LocalDateTime.now();
        this.message = message;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public String getMessage() {
        return this.message;
    }

}
