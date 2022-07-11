package useCases.query.AvailabilityQueryConditions;

import entities.Appointment;
import entities.TimeBlock;
import useCases.query.QueryCondition;
import database.DataMapperGateway;

import java.time.ZonedDateTime;

public class NoAppointmentConflict<T extends TimeBlock> extends QueryCondition<T> {
    private DataMapperGateway<Appointment> appointmentDatabase;
    public NoAppointmentConflict(Boolean desiredStatus, DataMapperGateway<Appointment> appointmentDatabase) {
        super(desiredStatus);
        this.appointmentDatabase = appointmentDatabase;
    }

    @Override
    public boolean isTrue(T item) {
        for(Integer appointmentId: appointmentDatabase.getAllIds()){
            if (appointmentDatabase.get(appointmentId).getTimeBlock().getStartTime().getDayOfYear() ==
                    item.getStartTime().getDayOfYear()){
                if (appointmentDatabase.get(appointmentId).getTimeBlock().getStartTime().isBefore(item.getStartTime()) &
                        appointmentDatabase.get(appointmentId).getTimeBlock().getEndTime().isAfter(item.getStartTime())){
                    return false;
                }
                if (appointmentDatabase.get(appointmentId).getTimeBlock().getStartTime().isAfter(item.getStartTime()) &
                        appointmentDatabase.get(appointmentId).getTimeBlock().getEndTime().isAfter(item.getEndTime())){
                    return false; //man double check this logic jesus
                }
            }

        }
        return true;
    }
}
