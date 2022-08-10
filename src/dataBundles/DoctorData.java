package dataBundles;

import entities.Doctor;
import entities.TimeBlock;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Wrapper class for Doctor entity. Inherits from UserData<Doctor>.
 */
public class DoctorData extends UserData<Doctor> {

    /**
     * Initializes the doctor class and its super class with the doctor entity. Doctor entity is stored in the parent
     * class and this one.
     * @param doctor Doctor - doctor entity to be stored.
     */
    public DoctorData(Doctor doctor) {
        super(doctor);
    }

}
