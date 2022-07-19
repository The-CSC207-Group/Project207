package entities;

import utilities.JsonSerializable;

import java.time.ZoneId;
import java.util.regex.Pattern;

/**
 * Represents a clinic.
 */
public class Clinic extends JsonSerializable {

    private String name;
    private String phoneNumber;
    private String address;
    private ZoneId timeZone;
    //used to store the operating hours of the clinic for availability calculations
    private TimeBlock clinicHours;

    /**
     * Creates an instance of Clinic
     * @param name The name of the clinic.
     * @param phoneNumber The phone number of the clinic.
     * @param address The address of the clinic.
     * @param timeZone The time zone of the clinic.
     * @param clinicHours The clinic's hours of operation.
     */
    public Clinic(String name, String phoneNumber, String address, ZoneId timeZone, TimeBlock clinicHours) {
        this.name = name;
        setPhoneNumber(phoneNumber);
        this.address = address;
        this.timeZone = timeZone;
        this.clinicHours = clinicHours;
    }

    /**
     * @return Returns the name of the clinic.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the clinic.
     * @param name The new name of the clinic.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the clinic's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the clinic.
     * @param phoneNumber The new phone number of the clinic.
     */
    public void setPhoneNumber(String phoneNumber) {
        if (Pattern.matches("^([0-9])+$", phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new RuntimeException("Phone number should contain only digits from 0 to 9.");
        }
    }

    /**
     * @return Returns the clinic's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the clinic.
     * @param address The new address of the clinic.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return Returns the time zone of the clinic.
     */
    public ZoneId getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone of the clinic.
     * @param timeZone The new time zone of the clinic.
     */
    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return Returns the clinic's hours of operation.
     */
    public TimeBlock getClinicHours() {
        return clinicHours;
    }

    /**
     * Sets the clinic's hours of operation.
     * @param clinicHours The clinic's new hours of operation
     */
    public void setClinicHours(TimeBlock clinicHours) {
        this.clinicHours = clinicHours;
    }
}
