package entities;

import java.time.ZoneId;

public class Clinic {

    // variables

    private String name;
    private String phoneNumber; // phoneNumber expected to only include digits from 0 to 9.
    private String address;
    private ZoneId timeZone;

    // constructor

    public Clinic(String name, String phoneNumber, String address, ZoneId timeZone) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.timeZone = timeZone;
    }

    // methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}
