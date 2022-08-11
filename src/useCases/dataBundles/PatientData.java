package useCases.dataBundles;

import entities.Patient;

/**
 * Wrapper class for Patient entity. Inherits from UserData<Patient>.
 */
public class PatientData extends UserData<Patient> {

    /**
     * Initializes the patient class and its super class with the patient entity. Patient entity is stored in the parent
     * class and this one.
     *
     * @param patient Patient - patient entity to be stored.
     */
    public PatientData(Patient patient) {
        super(patient);
    }

}
