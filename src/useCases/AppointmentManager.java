package useCases;

import database.DataMapperGateway;
import entities.Doctor;
import entities.Patient;
import entities.Appointment;
import entities.TimeBlock;

import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.util.HashSet;

public class AppointmentManager {
    private DataMapperGateway<Appointment> appointmentDatabase;
    private DataMapperGateway<Patient> patientDatabase;

    public AppointmentManager(DataMapperGateway<Appointment> AppointmentDatabase,
                              DataMapperGateway<Patient> PatientDatabase){
        this.appointmentDatabase = AppointmentDatabase;
        this.patientDatabase = PatientDatabase;
    }
    public boolean bookAppointment(Patient patient, Doctor doctor, ZonedDateTime startTime, ZonedDateTime endTime){
        TimeBlock newTime = new TimeBlock(startTime, endTime);
        Appointment newApp = new Appointment(idforappointment????, newTime, doctor.getId(), patient.getId());
        this.appointmentDatabase.add(newApp);

        return true;
    }
    public void removeAppointment(Appointment oldApp){
        this.appointmentDatabase.remove(oldApp.getId());
    }
    public boolean rescheduleAppointment(Appointment oldApp, ZonedDateTime newStart, ZonedDateTime newEnd){
        TimeBlock newTime = new TimeBlock(newStart, newEnd);
        appointmentDatabase.get(oldApp.getId()).setTimeBlock(newTime);
        return true;
    }
    public ArrayList<Appointment> getAppointments(Patient patient){
        HashSet<Integer> allAppointmentIds = appointmentDatabase.getAllIds();
        ArrayList<Appointment> patientAppointments = new ArrayList<>();
        for (Integer id : allAppointmentIds){
            if (patient.getId() == appointmentDatabase.get(id).getPatientID())
                patientAppointments.add(appointmentDatabase.get(id));
        }
        return patientAppointments;
    }
    public ArrayList<Appointment> getAppointments(Doctor doctor){
        HashSet<Integer> allAppointmentIds = appointmentDatabase.getAllIds();
        ArrayList<Appointment> doctorAppointments = new ArrayList<>();
        for (Integer id : allAppointmentIds){
            if (doctor.getId() == appointmentDatabase.get(id).getPatientID())
                doctorAppointments.add(appointmentDatabase.get(id));
        }
        return doctorAppointments;
    }
    }
    public ArrayList<Appointment> getAllAppointments(){
        HashSet<Integer> allAppointmentIds = appointmentDatabase.getAllIds();
        ArrayList<Appointment> allAppointments = new ArrayList<>();
        for (Integer id : allAppointmentIds){
            allAppointments.add(appointmentDatabase.get(id));
        }
        return allAppointments;
    }
    public ArrayList<TimeBlock> getAvailability(Doctor doctor){
        return doctor.getAvailability();
    }
    public void changeAvailability(Doctor doctor){
        doctor.
    }
}
