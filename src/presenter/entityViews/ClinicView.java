package presenter.entityViews;

import dataBundles.ClinicData;

/**
 * The Clinic entity's view.
 */
public class ClinicView extends EntityView<ClinicData> {

    /**
     * @param item ClinicData bundle to view.
     * @return String representing item's full clinic view.
     */
    @Override
    public String viewFull(ClinicData item) {
        return viewClinicName(item) + "\n"
                + viewAddress(item) + "\n"
                + viewTimeZone(item) + "\n"
                + viewClinicHours(item) + "\n"
                + viewPhoneNumber(item);
    }

    /**
     * @param item ClinicData bundle to view.
     * @return String representing the clinic's name as a view.
     */
    public String viewClinicName(ClinicData item) {
        String clinicName = getDefaultStringNA(item.getClinicName());
        return "This clinic's name is " + clinicName + ".";
    }

    /**
     * @param item ClinicData bundle to view.
     * @return String representing the clinic's address as a view.
     */
    public String viewAddress(ClinicData item) {
        String address = getDefaultStringNA(item.getAddress());
        return "This clinic's address is " + address + ".";
    }

    /**
     * @param item ClinicData bundle to view.
     * @return String representing the clinic's time zone as a view.
     */
    public String viewTimeZone(ClinicData item) {
        String timeZone = getDefaultStringNA(item.getTimeZone().toString());
        return "This clinic's time zone is " + timeZone + ".";
    }

    /**
     * @param item ClinicData bundle to view.
     * @return String representing the clinic's hours of operation as a view.
     */
    public String viewClinicHours(ClinicData item) {
        String startTime = getDefaultStringNA(item.getClinicHours().getStartTime().toString());
        String endTime = getDefaultStringNA(item.getClinicHours().getEndTime().toString());
        return "This clinic is open from " + startTime + "to" + endTime + ".";
    }

    /**
     * @param item ClinicData bundle to view.
     * @return String representing the clinic's phone number as a view.
     */
    public String viewPhoneNumber(ClinicData item) {
        String phoneNumber = getDefaultStringNA(item.getPhoneNumber());
        return "This clinic's phone number is " + phoneNumber + ".";
    }
}
