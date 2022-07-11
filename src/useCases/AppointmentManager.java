package useCases;

import dataBundles.AppointmentDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.query.AvailabilityQueryConditions.NoAbsenceConflict;
import useCases.query.AvailabilityQueryConditions.NoAppointmentConflict;
import useCases.query.AvailabilityQueryConditions.NoDoctorAvailabilityConflict;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.AppointmentQueryConditions.IsPatientsAppointment;
import useCases.query.AppointmentQueryConditions.IsDoctorsAppointment;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
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
        Query<TimeBlock> query = new Query<>();
        TimeBlock newTime = new TimeBlock(startTime, endTime);
        ArrayList<QueryCondition<TimeBlock>> queryConditions = new ArrayList<>();

        queryConditions.add(new NoAbsenceConflict<>(true, doctorDatabase, doctorId));
        queryConditions.add(new NoAppointmentConflict<>(true, appointmentDatabase));
        queryConditions.add(new NoDoctorAvailabilityConflict<>(true, doctorDatabase, doctorId));

        query.checkConditions()4 //ask daniel about this
        Appointment newApp = new Appointment(newTime, doctorId, patientId);
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
    public ArrayList<TimeBlock> searchAvailability(Integer doctorId, ZonedDateTime searchStartTime,
                                                       ZonedDateTime searchEndTime){
        searchEndTime.
        for (int numOfDays = searchEndTime.getDayOfYear() - searchStartTime.getDayOfYear(); numOfDays > 0; numOfDays--){
            searchEndTime.getDayOfYear() - numOfDays


        }
        return null;
        //use query, slice up each availability in 1 hour segments and run it through the conditions
    }
    public ArrayList<AvailabilityData> getAvailabilityDataFromDayOfWeek(Integer doctorId, DayOfWeek dayOfWeek){
        ArrayList<AvailabilityData> availabilityDataList = new ArrayList<>();
        for (AvailabilityData availabilityData: doctorDatabase.get(doctorId).getAvailability()){
            if (dayOfWeek.getValue() == availabilityData.getDayOfWeek().getValue()){
                availabilityDataList.add(availabilityData);
            }
        }
        return availabilityDataList;
    }
    public ArrayList<TimeBlock> getSingleDayAvailability(Integer doctorId, DayOfWeek dayOfWeek, LocalDate selectedDay){
        ArrayList<AvailabilityData> doctorAvailabilityData = getAvailabilityDataFromDayOfWeek(doctorId, dayOfWeek);
        ArrayList<TimeBlock> availabilityTimeBlock = new ArrayList<>();
        int hours = 0;
        for (AvailabilityData availabilityData: doctorAvailabilityData){
            hours += availabilityData.getDoctorEndTime().getHour() - availabilityData.getDoctorStartTime().getHour();
            while (hours > 0){
                TimeBlock newTimeBlock = new TimeBlock(ZonedDateTime.of(selectedDay,
                        availabilityData.getDoctorEndTime().minusHours(hours), ZoneId.of("US/Eastern")),
                        ZonedDateTime.of(selectedDay, availabilityData.getDoctorEndTime().minusHours(hours),
                                ZoneId.of("US/Eastern")));

                hours -= 1;
            }
        }

    }
    public boolean changeAvailability(Integer doctorId, TimeBlock oldTimeBlock, ZonedDateTime startTime,
                                      ZonedDateTime endTime){
        //seems like there should be a sort of Id associated with timeblocks since this function would require a
        // timeblock as a parameter

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