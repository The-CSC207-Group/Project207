package entities;

import java.time.LocalDateTime;

public class TimeBlock {

    // variables

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // constructor

    public TimeBlock(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // methods

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
