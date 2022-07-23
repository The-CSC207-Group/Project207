package presenter.entityViews;

import dataBundles.ClinicData;

import java.util.ArrayList;

/**
 * The Clinic entity's view.
 */
public class ClinicView extends EntityView<ClinicData> {

    /**
     * @param item ClinicData to view.
     * @return String representing item's full clinic view.
     */
    @Override
    public String viewFull(ClinicData item) {
        return viewClinicName(item) + "\n"
                + viewAddress(item) + "\n"
                + viewEmail(item) + "\n"
                + viewTimeZone(item) + "\n"
                + viewPhoneNumber(item) + "\n"
                + viewClinicHours(item);
    }

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's name as a view.
     */
    public String viewClinicName(ClinicData item) {
        String clinicName = getDefaultStringNA(item.getClinicName());
        return "This clinic's name is " + clinicName + ".";
    }

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's address as a view.
     */
    public String viewAddress(ClinicData item) {
        String address = getDefaultStringNA(item.getAddress());
        return "This clinic's address is " + address + ".";
    }

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's email as a view.
     */
    public String viewEmail(ClinicData item) {
        String email = getDefaultStringNA(item.getEmail());
        return "This clinic's email is " + email + ".";
    }

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's time zone as a view.
     */
    public String viewTimeZone(ClinicData item) {
        String timeZone = getDefaultStringNA(item.getTimeZone().toString());
        return "This clinic's time zone is " + timeZone + ".";
    }

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's phone number as a view.
     */
    public String viewPhoneNumber(ClinicData item) {
        String phoneNumber = getDefaultStringNA(item.getPhoneNumber());
        return "This clinic's phone number is " + phoneNumber + ".";
    }

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's hours of operation as a view.
     */
    public String viewClinicHours(ClinicData item) {
        ArrayList<String> al = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            al.add(item.getClinicHours().get(i).getDoctorStartTime().toString());
            al.add(item.getClinicHours().get(i).getDoctorEndTime().toString());
        }
        return "Clinic hours:" +
                "\nMonday: " + al.get(0) + " to " + al.get(1) +
                "\nTuesday: " + al.get(2) + " to " + al.get(3) +
                "\nWednesday: " + al.get(4) + " to " + al.get(5) +
                "\nThursday: " + al.get(6) + " to " + al.get(7) +
                "\nFriday: " + al.get(8) + " to " + al.get(9) +
                "\nSaturday: " + al.get(10) + " to " + al.get(11) +
                "\nSunday: " + al.get(12) + " to " + al.get(13);
    }

}
