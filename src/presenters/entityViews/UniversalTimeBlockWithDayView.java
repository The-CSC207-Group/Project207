// PHASE 2 FILE

package presenters.entityViews;

import dataBundles.UniversalTimeBlockWithDay;

/**
 * The Appointment entity's view.
 */
public class UniversalTimeBlockWithDayView extends EntityView<UniversalTimeBlockWithDay> {

    /**
     * @param item AppointmentData  to view.
     * @return String representing item's full appointment view.
     */
    @Override
    public String viewFull(UniversalTimeBlockWithDay item) {
        return "Appointment starting at " + item.startTime() + " and ending at " + item.endTime() + "on " + item.date() +
                ".";
    }

}
