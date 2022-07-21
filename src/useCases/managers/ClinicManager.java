package useCases.managers;

import dataBundles.ClinicData;
import database.Database;
import entities.Clinic;

import java.util.regex.Pattern;

public class ClinicManager {
    private final Clinic clinic;

    /**
     * Initializes the clinic manager.
     * @param database Database - collection of all entity databases in the program.
     */
    public ClinicManager(Database database){
        this.clinic = database.getClinic();
    }

    /**
     * Returns a data bundle representing the clinic in the database.
     * @return ClinicData - data bundle representing the clinic in the database.
     */
    public ClinicData clinicData(){
        return new ClinicData(clinic);
    }

    /**
     * Changes the clinic's phone number to the phone number passed in.
     * @param phoneNumber String - the clinic's new phone number.
     * @return boolean - returns whether the phone number was successfully changed or not
     */
    public boolean changePhone(String phoneNumber){
        if (!Pattern.matches("^(/d)+$", phoneNumber)){return false;}
        clinic.setPhoneNumber(phoneNumber);
        return true;
    }

    /**
     * Changes the clinic's address to the address passed in.
     * @param address String - the clinic's new address
     * @return boolean - returns whether the phone number was successfully changed or not
     */
    public boolean changeAddress(String address){
        clinic.setAddress(address);
        return true;
    }

}
