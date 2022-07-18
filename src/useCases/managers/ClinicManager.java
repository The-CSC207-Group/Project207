package useCases.managers;

import dataBundles.ClinicData;
import database.Database;
import entities.Clinic;

public class ClinicManager {
    private Clinic clinic;

    public ClinicManager(Database database){
        this.clinic = database.getClinic();
    }
    public ClinicData clinicData(){
        return new ClinicData(clinic);
    }
    public boolean changePhone(String phoneNumber){
        clinic.setPhoneNumber(phoneNumber);
        return true;
    }
    public boolean changeAddress(String address){
        clinic.setAddress(address);
        return true;
    }
}
