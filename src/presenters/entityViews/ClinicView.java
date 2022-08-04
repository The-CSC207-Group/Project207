package presenters.entityViews;

import dataBundles.AppointmentData;
import dataBundles.AvailabilityData;
import dataBundles.ClinicData;

import java.time.DayOfWeek;
import java.util.*;

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

    // ALL CODE BELOW IS FOR PHASE 2

    /**
     * @param item ClinicData to view.
     * @return String representing the clinic's hours of operation as a view.
     */

    public String viewClinicHours(ClinicData item){
        ArrayList<AvailabilityData> availabilites = item.getClinicHours();
        StringBuilder out = new StringBuilder("Clinic Hours: \n");
        LinkedHashMap<String, DayOfWeek> dayMap = new LinkedHashMap<>() {{
            put("Monday", DayOfWeek.MONDAY);
            put("Tuesday", DayOfWeek.TUESDAY);
            put("Wednesday", DayOfWeek.WEDNESDAY);
            put("Thursday", DayOfWeek.THURSDAY);
            put("Friday", DayOfWeek.FRIDAY);
            put("Saturday", DayOfWeek.SATURDAY);
            put("Sunday", DayOfWeek.SUNDAY);
        }};

        for (String dayString: dayMap.keySet()){
            Optional<AvailabilityData> availabilityData = availabilites.stream().
                    filter(availability -> availability.dayOfWeek().equals(dayMap.get(dayString))).
                    findFirst();
            if (availabilityData.isPresent()) {
                out.append(dayString + ": " + availabilityData.get().startTime() + " to " + availabilityData.get().endTime() + " \n");
            }else{
                out.append(dayString + ": No Availability \n");
            }
        }
        return out.toString();
    }
}
