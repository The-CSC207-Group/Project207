package entities;

import java.util.ArrayList;

public class Patient extends User {

    private String healthNumber;

    public Patient(String username, String password, Integer contactInfoId, String healthNumber) {
        super(username, password, contactInfoId);
        this.healthNumber = healthNumber;
    }

    public Patient(String username, String password){
        super(username, password, new Contact().getId());
    }

    public String getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

}
