package entities;

import Utilities.JsonSerializable;

import java.time.ZoneId;
import java.util.regex.Pattern;

public class Clinic extends JsonSerializable {

    private String name;
    private String phoneNumber;
    private String address;
    private ZoneId timeZone;
    //used to store the operating hours of the clinic for availability calculations
    private TimeBlock clinicHours;

    public Clinic(String name, String phoneNumber, String address, ZoneId timeZone, TimeBlock clinicHours) {
        this.name = name;
        setPhoneNumber(phoneNumber);
        this.address = address;
        this.timeZone = timeZone;
        this.clinicHours = clinicHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharSequence getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (Pattern.matches("^\\d{10}$", phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new RuntimeException("Phone number should contain only digits from 0 to 9.");
        }

//        int n = phoneNumber.length();
//        ArrayList<Boolean> allDigits = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            allDigits.add(Character.isDigit(phoneNumber.charAt(i)));
//        }
//        if (!allDigits.contains(false)) {
//            this.phoneNumber = phoneNumber;
//        }
//        else {
//            throw new RuntimeException("Phone number should contain only digits from 0 to 9.");
//        }
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

    public TimeBlock getClinicHours() {
        return clinicHours;
    }

    public void setClinicHours(TimeBlock clinicHours) {
        this.clinicHours = clinicHours;
    }
}
