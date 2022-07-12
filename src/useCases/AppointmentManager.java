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
import java.time.*;
import java.util.ArrayList;
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
    public void bookAppointment(Integer patientId, Integer doctorId, TimeBlock proposedTime){
        appointmentDatabase.add(new Appointment(proposedTime, doctorId, patientId));
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
        ArrayList<TimeBlock> totalAvailableTimes = new ArrayList<>();
        for (int numOfDays = searchEndTime.getDayOfYear() - searchStartTime.getDayOfYear(); numOfDays > 0; numOfDays--){
            LocalDate daySearched = searchEndTime.toLocalDate();
            totalAvailableTimes.addAll(parseAvailabilityWithAppointmentData(getSingleDayAvailability(doctorId,
                    daySearched.minusDays(numOfDays)), doctorId));
        }
        return totalAvailableTimes;
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
    public ArrayList<TimeBlock> getSingleDayAvailability(Integer doctorId, LocalDate selectedDay){
        ArrayList<AvailabilityData> doctorAvailabilityData = getAvailabilityDataFromDayOfWeek(doctorId,
                selectedDay.getDayOfWeek());
        ArrayList<TimeBlock> availabilityTimeBlock = new ArrayList<>();
        for (AvailabilityData availabilityData: doctorAvailabilityData){
                TimeBlock newTimeBlock = new TimeBlock(ZonedDateTime.of(selectedDay,
                        availabilityData.getDoctorStartTime(), ZoneId.of("US/Eastern")),
                        ZonedDateTime.of(selectedDay, availabilityData.getDoctorEndTime(),
                                ZoneId.of("US/Eastern")));
                availabilityTimeBlock.add(newTimeBlock);
            }
        return availabilityTimeBlock;
    }

    public ArrayList<TimeBlock> parseAvailabilityWithAppointmentData(ArrayList<TimeBlock> availability,
                                                                     Integer doctorId){
        ArrayList<TimeBlock> parsedAvailability = new ArrayList<>();
        for (TimeBlock availabilityTimeBlock: availability) {
            ZonedDateTime startTime = availabilityTimeBlock.getStartTime();
            for (Integer appointmentId : appointmentDatabase.getAllIds()) {
                if (appointmentDatabase.get(appointmentId).getDoctorID() == doctorId & startTime
                        == appointmentDatabase.get(appointmentId).getTimeBlock().getStartTime()) {
                    startTime = appointmentDatabase.get(appointmentId).getTimeBlock().getEndTime();
                }
                else if (appointmentDatabase.get(appointmentId).getDoctorID() == doctorId){
                    parsedAvailability.add(new TimeBlock(startTime,
                            appointmentDatabase.get(appointmentId).getTimeBlock().getStartTime()));
                    startTime = appointmentDatabase.get(appointmentId).getTimeBlock().getEndTime();
                }
            }
            if (startTime != availabilityTimeBlock.getEndTime()){
                parsedAvailability.add(new TimeBlock(startTime, availabilityTimeBlock.getEndTime()));
            }
        }
        return parsedAvailability;
    }
    //pending implementation
//    public boolean newAvailability(Integer doctorId, DayOfWeek dayOfWeek, LocalTime startTime,
//                                      LocalTime endTime){
//        Query<Appointment> query = new Query<>();
//        ArrayList<QueryCondition<TimeBlock>> queryConditions = new ArrayList<>();
//        queryConditions.add(new NoAppointmentConflict<>(true, appointmentDatabase));
//        query.returnFirstMeetingConditions(new TimeBlock(startTime, endTime), queryConditions)
//
//    }
//
//    public ArrayList<AvailabilityData> getAvailability(Integer doctorId, DayOfWeek dayOfWeek){
//
//    }
    private ArrayList<AppointmentDataBundle> convertAppointmentsToDataBundle(ArrayList<Appointment>
                                                                                     patientsAppointments) {
        ArrayList<AppointmentDataBundle> appointmentDataBundles = new ArrayList<>();
        for (Appointment appointment : patientsAppointments){
            appointmentDataBundles.add(new AppointmentDataBundle(appointment));
        }
        return appointmentDataBundles;
    }
}