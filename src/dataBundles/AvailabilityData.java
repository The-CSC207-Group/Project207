package dataBundles;

import entities.Availability;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilityData {

    private final Availability availability;

    public AvailabilityData(Availability availability) {
        this.availability = availability;
    }

    public DayOfWeek getDayOfWeek() {
        return availability.getDayOfWeek();
    }

    public LocalTime getDoctorStartTime() {
        return availability.getDoctorStartTime();
    }

    public LocalTime getDoctorEndTime() {
        return availability.getDoctorEndTime();
    }
}
