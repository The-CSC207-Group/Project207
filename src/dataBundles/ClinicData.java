package dataBundles;

import entities.Clinic;

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

    /**
     * @return TimeBlock - hours of operations of the clinic stored.
     */
    public ArrayList<AvailabilityData> getClinicHours(){
        return clinic.getClinicHours().stream()
                .map(AvailabilityData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
