package useCases;

import entities.Doctor;
import entities.Patient;
import entities.Appointment;


public class AppointmentManager {
    public ArrayList<Appointment> Appointments;

    public boolean bookAppointment(Patient patient, Doctor doctor, DateTime startTime, DateTime endTime){

        return true;
    }
    public void removeAppointment(Patient patient){
        //only intakes patient as this function will print all appointments for a patient and then remove the one
        //returned on the console
    }
    public boolean rescheduleAppointment(String patient, DateTime newStart, DateTime newEnd){
        //similar to removeAppointment, prompts the user with all appointments and then changes the datetime values
        //of the selected appointment
        return true;
    }
    public ArrayList<Appointments> getAppointments(Patient patient){

    }
    public ArrayList<Appointments> getAppointments(Doctor doctor){

    }
    public ArrayList<Appointments> getAllAppointments(){

    }
    public ArrayList<Timeblock> getAvailability(Doctor doctor){

    }
    public void changeAvailability(Doctor doctor){

    }
}
