package useCases.accessClasses;

import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import database.Database;

public interface AccessMixin {
    Database getDatabase();
    default PrescriptionData getPerscription(PatientData patient){
        return new PrescriptionData(getDatabase().getPrescriptionDatabase().getByCondition(x -> x.getPatientId().equals(patient)));
    }
}
