package entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class AvailabilityData {
    private DayOfWeek dayOfWeek;
    private LocalTime doctorStartTime;
    private LocalTime doctorEndTime;

    public AvailabilityData(DayOfWeek dayOfWeek, LocalTime doctorStartTime, LocalTime doctorEndTime){
        this.dayOfWeek = dayOfWeek;
        this.doctorStartTime = doctorStartTime;
        this.doctorEndTime = doctorEndTime;
    }
    public DayOfWeek getDataDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getDoctorStartTime() {
        return doctorStartTime;
    }

    public void setDoctorStartTime(LocalTime startTime) {
        this.doctorStartTime = startTime;
    }

    public LocalTime getDoctorEndTime() {
        return doctorEndTime;
    }

    public void setDoctorEndTime(LocalTime endTime) {
        this.doctorEndTime = endTime;
    }

}
