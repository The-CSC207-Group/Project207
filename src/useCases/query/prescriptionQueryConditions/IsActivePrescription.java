package useCases.query.prescriptionQueryConditions;

import entities.Prescription;
import useCases.query.QueryCondition;

import java.time.LocalDateTime;

public class IsActivePrescription<T extends Prescription> extends QueryCondition<T> {

    public IsActivePrescription(Boolean desiredStatus) {
        super(desiredStatus);
    }

    @Override
    public boolean isTrue(T item) {
        return item.getExpiryDate().toLocalDateTime().isBefore(LocalDateTime.now());
    }

}
