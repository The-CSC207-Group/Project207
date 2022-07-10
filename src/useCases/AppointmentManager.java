package useCases;

import dataBundles.AppointmentDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.AppointmentQueryConditions.IsPatientsAppointment;
import useCases.query.AppointmentQueryConditions.IsDoctorsAppointment;
import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.util.HashSet;

public class AppointmentManager {
    private DataMapperGateway<Appointment> appointmentDatabase;
    private DataMapperGateway<Patient> patientDatabase;
    private DataMapperGateway<Doctor> doctorDatabase;

    public AppointmentManager(DataMapperGateway<Appointment> AppointmentDatabase,
                              DataMapperGateway<Patient> PatientDatabase, DataMapperGateway<Doctor> doctorDatabase){
        this.appointmentDatabase = AppointmentDatabase;
        this.patientDatabase = PatientDatabase;
        this.doctorDatabase  = doctorDatabase;
    }
    //bookAppointment will return true if the proposed appointment is within a doctors avaialbility, and false if not
    public boolean bookAppointment(Integer patientId, Integer doctorId, ZonedDateTime startTime, ZonedDateTime endTime){
        TimeBlock newTime = new TimeBlock(startTime, endTime);
        //Appointment newApp = new Appointment(idforappointment????, newTime, doctorId, patientId);
        //this.appointmentDatabase.add(newApp);
        //when an appointment is booked, this function should also call change availability and remove the appointment time
        return true;
    }
    public void removeAppointment(Integer Id){
        appointmentDatabase.remove(Id);
    }
    public void rescheduleAppointment(Integer Id, ZonedDateTime newStart, ZonedDateTime newEnd){
        appointmentDatabase.get(Id).setTimeBlock(new TimeBlock(newStart, newEnd));
    }
    public ArrayList<AppointmentDataBundle> getPatientAppointments(Integer patientId){
//        HashSet<Integer> allAppointmentIds = appointmentDatabase.getAllIds();
//        ArrayList<Appointment> patientAppointments = new ArrayList<>();
//        for (Integer id : allAppointmentIds){
//            if (patient.getId() == appointmentDatabase.get(id).getPatientID())
//                patientAppointments.add(appointmentDatabase.get(id));
//        }
//        return patientAppointments;
        Query<Appointment> query = new Query<>();
        ArrayList<QueryCondition<Appointment>> queryConditions = new ArrayList<>();
        queryConditions.add(new IsPatientsAppointment<>(patientId, true));
        ArrayList<Appointment> patientsAppointments =
                query.returnAllMeetingConditions(appointmentDatabase, queryConditions);
        return convertAppointmentsToDataBundle(patientsAppointments);
    }

    public ArrayList<AppointmentDataBundle> getDoctorAppointments(Integer doctorId){
        Query<Appointment> query = new Query<>();
        ArrayList<QueryCondition<Appointment>> queryConditions = new ArrayList<>();
        queryConditions.add(new IsDoctorsAppointment<>(doctorId, true));
        ArrayList<Appointment> patientsAppointments =
                query.returnAllMeetingConditions(appointmentDatabase, queryConditions);
        return convertAppointmentsToDataBundle(patientsAppointments);
    }

    public ArrayList<AppointmentDataBundle> getAllAppointments(){
        HashSet<Integer> allAppointmentIds = appointmentDatabase.getAllIds();
        ArrayList<Appointment> allAppointments = new ArrayList<>();
        for (Integer id : allAppointmentIds){
            allAppointments.add(appointmentDatabase.get(id));
        }
        return convertAppointmentsToDataBundle(allAppointments);
    }
    //availability should be an arrayList of timeblocks, with only 1 block representing no appointments. each subsequent
    //appointment cuts the block, expanding the list
    public ArrayList<TimeBlock> getAvailability(Integer doctorId){
        return doctorDatabase.get(doctorId).getAvailability();
    }
    public boolean changeAvailability(Integer doctorId, TimeBlock oldTimeBlock, ZonedDateTime startTime,
                                      ZonedDateTime endTime){
        //seems like there should be a sort of Id associated with timeblocks since this function would require a
        // timeblock as a parameter
        ArrayList<TimeBlock> availability = getAvailability(doctorId);
        for (TimeBlock block : availability) {
            if (block == oldTimeBlock) {
                block.setStartTime(startTime);
                block.setEndTime(endTime);
                return true;
            }
        }
        return false;
    }
    private ArrayList<AppointmentDataBundle> convertAppointmentsToDataBundle(ArrayList<Appointment>
                                                                                     patientsAppointments) {
        ArrayList<AppointmentDataBundle> appointmentDataBundles = new ArrayList<>();
        for (Appointment appointment : patientsAppointments){
            appointmentDataBundles.add(new AppointmentDataBundle(appointment));
        }
        return appointmentDataBundles;
    }
}