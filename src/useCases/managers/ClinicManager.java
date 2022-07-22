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
     * Returns a data representing the clinic in the database.
     * @return ClinicData - data representing the clinic in the database.
     */
    public ClinicData clinicData(){
        return new ClinicData(clinic);
    }

    /**
     * Changes the clinic's name to the name passed in.
     * @param newName String - the clinic's new name.
     * @return boolean - returns whether the clinic name was successfully changed or not.
     */
    public boolean changeClinicName(String newName){
        clinic.setName(newName);
        return true;
    }

    /**
     * Changes the clinic's phone number to the phone number passed in.
     * @param newPhoneNumber String - the clinic's new phone number.
     * @return boolean - returns whether the phone number was successfully changed or not.
     */
    public boolean changeClinicPhoneNumber(String newPhoneNumber){
        if (!Pattern.matches("^([0-9])+$", newPhoneNumber)){return false;}
        clinic.setPhoneNumber(newPhoneNumber);
        return true;
    }

    /**
     * Changes the clinic's email to the phone number passed in.
     * @param newEmail String - the clinic's new email.
     * @return boolean - returns whether the email was successfully changed or not.
     */
    public boolean changeClinicEmail(String newEmail){
        //got regex from https://regexlib.com/Search.aspx?k=email
        if (!Pattern.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", newEmail)){
            return false;}
        clinic.setEmail(newEmail);
        return true;
    }

    /**
     * Changes the clinic's address to the address passed in.
     * @param newAddress String - the clinic's new address.
     * @return boolean - returns whether the address was successfully changed or not.
     */
    public boolean changeClinicAddress(String newAddress){
        clinic.setAddress(newAddress);
        return true;
    }

}
