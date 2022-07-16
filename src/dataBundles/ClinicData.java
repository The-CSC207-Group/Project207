package dataBundles;

import entities.Clinic;
import entities.TimeBlock;

import java.time.ZoneId;

public class ClinicData {
    private final Clinic clinic;

    public ClinicData(Clinic clinic) {
        this.clinic = clinic;
    }

    public String getAddress(){
        return clinic.getAddress();
    }

    public ZoneId getTimeZone() {
        return clinic.getTimeZone();
    }

    public TimeBlock getClinicHours(){
        return clinic.getClinicHours();
    }

    public CharSequence getPhoneNumber() {
        return clinic.getPhoneNumber();
    }

    public String getClinicName() {
        return clinic.getName();
    }

}
