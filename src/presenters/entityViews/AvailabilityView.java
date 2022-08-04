// PHASE 2 FILE

package presenters.entityViews;

import dataBundles.AvailabilityData;
import utilities.DayOfWeekUtils;

/**
 * The Availability entity's view.
 */
public class AvailabilityView extends EntityView<AvailabilityData> {
    private final DayOfWeekUtils dayOfWeekUtils = new DayOfWeekUtils();
    /**
     * @param item AvailabilityData  to view.
     * @return String representing item's full availability view.
     */
    @Override
    public String viewFull(AvailabilityData item) {
        return dayOfWeekUtils.dayOfWeekToString(item.getDayOfWeek()) + ": " + item.startTime() + " to " + item.endTime();
    }

    /**
     * @param item AvailabilityData  to view.
     * @return String representing item's day of week as a view.
     */
    public String viewDayOfWeek(AvailabilityData item) {
        return "Day of week: " + dayOfWeekUtils.dayOfWeekToString(item.dayOfWeek());
    }

    /**
     * @param item AvailabilityData  to view.
     * @return String representing item's doctor start time as a view.
     */
    public String viewStartTime(AvailabilityData item) {
        return "Start of availability: " + item.getDoctorStartTime() + ".";
    }

    /**
     * @param item AvailabilityData  to view.
     * @return String representing item's doctor end time as a view.
     */
    public String viewEndTIme(AvailabilityData item) {
        return "End of availability: " + item.getDoctorEndTime() + ".";
    }

}
