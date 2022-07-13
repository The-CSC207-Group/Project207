package useCases;

import entities.Appointment;
import entities.TimeBlock;
import database.DataMapperGateway;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AppointmentQueries {

    private Appointment appointment;

    public AppointmentQueries(Appointment appointment){
        this.appointment = appointment;
    }
    public boolean isDoctorsAppointment(Integer doctorId){
        return appointment.getDoctorID() == doctorId;
    }

}
