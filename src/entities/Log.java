package entities;

import utilities.JsonSerializable;

import java.time.LocalDateTime;

/**
 * Represents a user's log.
 */
public class Log extends JsonSerializable {

    private final Integer userId;
    private final LocalDateTime time;
    private final String message;

    /**
     * Creates an instance of Log.
     *
     * @param userId  Integer representing the id of the user that the log corresponds to.
     * @param message String representing the message to be displayed by the log.
     */
    public Log(Integer userId, String message) {
        this.userId = userId;
        this.time = LocalDateTime.now();
        this.message = message;
    }

    /**
     * @return LocalDateTime representing the time that the log was created.
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * @return String representing the message that the log stores.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return Integer representing the id of the user that the log corresponds to.
     */
    public Integer getUserId() {
        return userId;
    }

}
