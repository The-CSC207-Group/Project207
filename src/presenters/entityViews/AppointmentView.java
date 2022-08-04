// PHASE 2 FILE

package presenters.entityViews;

import dataBundles.AppointmentData;
import entities.UniversalTimeBlockWithDay;

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
        return "Appointment starting at " + item.startTime() + " and ending at " + item.endTime() + "on " + item.date() +
                ".";
    }

}
