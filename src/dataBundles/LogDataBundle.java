package dataBundles;

import entities.Log;

import java.time.LocalDateTime;

public class LogDataBundle extends DataBundle {
    private final Log log;

    public LogDataBundle(Integer id, Log log){
        super(id);
        this.log = log;
    }
    public Integer getId(){
        return log.getId();
    }
    public String getMessage(){
        return log.getMessage();
    }
    public LocalDateTime getTime(){
        return log.getTime();
    }
}
