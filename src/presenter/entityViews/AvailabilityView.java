package presenter.entityViews;

import dataBundles.AvailabilityData;

/**
 * The Availability entity's view.
 */
public class AvailabilityView extends EntityView<AvailabilityData> {

    /**
     * @param item AvailabilityData bundle to view.
     * @return String representing item's full availability view.
     */
    @Override
    public String viewFull(AvailabilityData item) {
        return viewDayOfWeek(item)
                + viewDoctorStartTime(item)
                + viewDoctorEndTime(item);
    }

    /**
     * @param item AvailabilityData bundle to view.
     * @return String representing item's day of week as a view.
     */
    public String viewDayOfWeek(AvailabilityData item) {
        return "Availability day of week is " + item.getDayOfWeek() + ".";
    }

    /**
     * @param item AvailabilityData bundle to view.
     * @return String representing item's doctor start time as a view.
     */
    public String viewDoctorStartTime(AvailabilityData item) {
        return "Doctor start time is " + item.getDoctorStartTime() + ".";
    }

    /**
     * @param item AvailabilityData bundle to view.
     * @return String representing item's doctor end time as a view.
     */
    public String viewDoctorEndTime(AvailabilityData item) {
        return "Doctor end time is " + item.getDoctorEndTime() + ".";
    }

}
