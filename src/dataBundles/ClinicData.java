package dataBundles;

import entities.Availability;
import entities.Clinic;
import entities.TimeBlock;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Wrapper class for Clinic entity.
 */
public class ClinicData {

    private final Clinic clinic;

    /**
     * Constructor.
     * @param clinic Clinic - Clinic entity.
     */
    public ClinicData(Clinic clinic) {
        this.clinic = clinic;
    }

    /**
     * @return String - address of the clinic stored.
     */
    public String getAddress(){
        return clinic.getAddress();
    }

    /**
     * @return String - address of the clinic stored.
     */
    public String getEmail(){
        return clinic.getEmail();
    }

    /**
     * @return ZoneId - timezone of the clinic stored.
     */
    public ZoneId getTimeZone() {
        return clinic.getTimeZone();
    }

    /**
     * @return TimeBlock - hours of operations of the clinic stored.
     */
    public ArrayList<AvailabilityData> getClinicHours(){
        return clinic.getClinicHours().stream()
                .map(AvailabilityData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return String - phone number of the clinic stored.
     */
    public String getPhoneNumber() {
        return clinic.getPhoneNumber();
    }

    /**
     * @return String - name of the clinic stored.
     */
    public String getClinicName() {
        return clinic.getName();
    }

    /**
     * @return Integer - id of the clinic stored.
     */
    public Integer getClinicId() {
        return clinic.getId();
    }

}
