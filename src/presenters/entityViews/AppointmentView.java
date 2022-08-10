package presenters.entityViews;

import dataBundles.AppointmentData;

import java.time.LocalTime;

/**
 * The Appointment entity's view.
 */
public class AppointmentView extends EntityView<AppointmentData> {

    /**
     * @param item AppointmentData  to view.
     * @return String representing item's full appointment view.
     */
    @Override
    public String viewFull(AppointmentData item) {
        LocalTime startTime = item.getTimeBlock().getStartDateTime().toLocalTime();
        LocalTime endTime = item.getTimeBlock().getEndDateTime().toLocalTime();
        return "Appointment starting at " + startTime
                + " and ending at " + endTime + " on " + item.getLocalDate() +
                ".";
    }

}
