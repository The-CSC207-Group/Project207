package entities;

import utilities.JsonSerializable;

import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * Represents a clinic.
 */
public class Clinic extends JsonSerializable {

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private final ArrayList<Availability> clinicHours;

    /**
     * Creates an instance of Clinic
     * @param name String representing the name of the clinic.
     * @param phoneNumber String representing the phone number of the clinic.
     * @param address String representing the address of the clinic.
     * @param clinicHours TimeBlock representing the clinic's hours of operation.
     */
    public Clinic(String name, String phoneNumber, String email, String address, ArrayList<Availability> clinicHours) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.clinicHours = clinicHours;
    }

    /**
     * @return String representing the name of the clinic.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the clinic.
     * @param name The new name of the clinic as String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String representing the clinic's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the clinic.
     * @param phoneNumber The new phone number of the clinic as String.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return String representing the clinic's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the clinic.
     * @param email The new email of the clinic as String.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String representing the clinic's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the clinic.
     * @param address The new address of the clinic as String.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    // ALL CODE BELOW IS FOR PHASE 2

    /**
     * @return TimeBlock representing the clinic's hours of operation.
     */
    public ArrayList<Availability> getClinicHours() {
        return clinicHours;
    }

    /**
     * Sets the clinic's hours of operation.
     * @param clinicHours TimeBlock representing the clinic's new hours of operation.
     */
    public void addClinicHours(Availability clinicHours) {
        this.clinicHours.add(clinicHours);
    }

    /**
     * Removes the clinic hours of a certain day. Assumes there is at most 1 availability per day of week.
     */
    public void removeClinicHours(DayOfWeek dayOfWeek){
        clinicHours.removeIf(availability -> availability.getDayOfWeek().equals(dayOfWeek));
    }

}
