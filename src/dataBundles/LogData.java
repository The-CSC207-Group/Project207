package dataBundles;

import entities.Log;

import java.time.LocalDateTime;

/**
 * Wrapper class for Log entity.
 */
public class LogData {

    private final Log log;

    /**
     * Constructor.
     * @param log Log - log to be stored.
     */
    public LogData(Log log) {
        this.log = log;
    }

    /**
     * @return Integer - id of the stored log.
     */
    public Integer getId() {
        return log.getId();
    }

    /**
     * @return String - message associated with the stored log.
     */
    public String getMessage() {
        return log.getMessage();
    }

    /**
     * @return LocalDateTime - time the log was created in the stored log.
     */
    public LocalDateTime getTime() {
        return log.getTime();
    }

    /**
     * @return Integer - id of the user the log belongs to.
     */
    public Integer getUserId(){return log.getUserId();}

}
