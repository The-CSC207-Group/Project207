package presenter.entityViews;

import dataBundles.AvailabilityData;
import entities.Availability;

public class AvailabilityView extends EntityView<AvailabilityData> {

    @Override
    public String viewFull(AvailabilityData item) {
        return viewDayOfWeek(item)
                + viewDoctorStartTime(item)
                + viewDoctorEndTime(item);
    }

    public String viewDayOfWeek(AvailabilityData item) {
        return "Availability day of week is " + item.getDayOfWeek() + ".";
    }

    public String viewDoctorStartTime(AvailabilityData item) {
        return "Doctor start time is " + item.getDoctorStartTime() + ".";
    }

    public String viewDoctorEndTime(AvailabilityData item) {
        return "Doctor end time is " + item.getDoctorEndTime() + ".";
    }
}
