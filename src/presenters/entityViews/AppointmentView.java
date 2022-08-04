// PHASE 2 FILE

package presenters.entityViews;

import dataBundles.AppointmentData;
import entities.UniversalTimeBlockWithDay;

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
        LocalTime startTime = item.getTimeBlock().getStartTime().toLocalTime();
        LocalTime endTime = item.getTimeBlock().getEndTime().toLocalTime();
        return "Appointment starting at " + startTime
                + " and ending at " + endTime + " on " + item.date() +
                ".";
    }

}
