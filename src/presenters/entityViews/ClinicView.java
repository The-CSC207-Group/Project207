package presenters.entityViews;

import dataBundles.AvailabilityData;
import dataBundles.ClinicData;
import utilities.DayOfWeekUtils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * The Clinic entity's view.
 */
public class ClinicView extends EntityView<ClinicData> {
    final DayOfWeekUtils dayOfWeekUtils = new DayOfWeekUtils();

    /**
     * @param item ClinicData to view.
     * @return String representing item's full clinic view.
     */
    @Override
    public String viewFull(ClinicData item) {
        return viewClinicName(item) + "\n"
                + viewAddress(item) + "\n"
                + viewEmail(item) + "\n"
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
        ArrayList<AvailabilityData> availabilities = item.getClinicHours();
        StringBuilder out = new StringBuilder("Clinic Hours: \n");
        LinkedHashMap<String, DayOfWeek> dayMap = dayOfWeekUtils.getDayOfWeekStringToEnumMap();

        for (String dayString : dayMap.keySet()) {
            out.append(getSingleDayAvailabilityString(dayString, availabilities, dayMap.get(dayString))).append("\n");
        }
        return out.toString();
    }

    private String getSingleDayAvailabilityString(String dayString, ArrayList<AvailabilityData> availabilities,
                                                  DayOfWeek dayOfWeek) {
        AvailabilityView availabilityView = new AvailabilityView();
        Optional<AvailabilityData> availabilityData = availabilities.stream().
                filter(availability -> availability.getDayOfWeek().equals(dayOfWeek)).
                findFirst();
        if (availabilityData.isPresent()) {
            return availabilityView.viewFull(availabilityData.get());
        } else {
            return dayString + ": No Availability";
        }
    }

}
