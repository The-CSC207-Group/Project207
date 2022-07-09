package useCases.query.prescriptionQueryConditions;

import entities.Prescription;
import useCases.query.QueryCondition;

import java.time.LocalDateTime;

public class IsActivePrescription extends QueryCondition {

    public IsActivePrescription(Boolean desiredStatus) {
        super(desiredStatus);
    }

    @Override
    public <T> boolean isTrue(T item) {
        return !((Prescription)item).getExpiryDate().toLocalDateTime().isBefore(LocalDateTime.now());
    }
}
