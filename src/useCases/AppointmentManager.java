package useCases;

import dataBundles.AppointmentDataBundle;
import database.DataMapperGateway;
import entities.*;


import java.time.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean bookAppointment(Integer patientId, Integer doctorId, TimeBlock proposedTime) {

        if (isNoTimeBlockConflictAppointment(getAppointmentWithPatientAndDoctor(doctorId, patientId), proposedTime)
                & isNoTimeBlockConflictAppointment(getSingleDayAvailability(doctorId,
                proposedTime.getStartTime().toLocalDate()), proposedTime)) {
            appointmentDatabase.add(new Appointment(proposedTime, doctorId, patientId));
            return true;
        }
        return false;
    }

    public boolean isNoTimeBlockConflictAppointment(ArrayList<TimeBlock> timeBlockList,
                                                    TimeBlock proposedTime){
        return timeBlockList.stream()
                .filter(x -> x.getStartTime().getDayOfYear()
                        == proposedTime.getStartTime().getDayOfYear())
                .filter(x -> x.getStartTime().isBefore(proposedTime.getStartTime()) &
                        x.getEndTime().isAfter(proposedTime.getStartTime()))
                .filter(x -> x.getStartTime().isAfter(proposedTime.getStartTime()) &
                        x.getStartTime().isBefore(proposedTime.getEndTime()))
                .collect(Collectors.toCollection(ArrayList::new)).isEmpty();
    }


    public void removeAppointment(Integer Id){
        appointmentDatabase.remove(Id);
    }
    public boolean rescheduleAppointment(Integer appointmentId, ZonedDateTime newStart, ZonedDateTime newEnd){
        Appointment appointment = appointmentDatabase.get(appointmentId);
        removeAppointment(appointmentId);
        TimeBlock proposedTime = new TimeBlock(newStart, newEnd);
        ArrayList<TimeBlock> consideration = getAppointmentWithPatientAndDoctor(appointment.getDoctorID(),
                appointment.getPatientID());
        if (isNoTimeBlockConflictAppointment(consideration, proposedTime)) {
            bookAppointment(appointment.getPatientID(), appointment.getDoctorID(), proposedTime);
            return true;
        }
        return false;
    }

    public ArrayList<TimeBlock> getAppointmentWithPatientAndDoctor(Integer doctorId, Integer patientId){
        return appointmentDatabase.getAllIds().stream()
                .map(x -> appointmentDatabase.get(x))
                .filter(x -> x.getDoctorID() == doctorId)
                .filter(x -> x.getPatientID() == patientId)
                .map(Appointment::getTimeBlock)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<AppointmentDataBundle> getPatientAppointments(Integer patientId){
        return getAllPatientAppointments(patientId).stream()
                .map(AppointmentDataBundle::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Appointment> getAllPatientAppointments(Integer patientId){
        return getAppointments().stream()
                .filter(x -> x.getPatientID() == patientId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<AppointmentDataBundle> getDoctorAppointments(Integer doctorId){
        return getAppointments().stream()
                .filter(x -> new AppointmentQueries(x).isDoctorsAppointment(doctorId))
                .map(AppointmentDataBundle::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<AppointmentDataBundle> getAllAppointments(){

        return getAppointments().stream()
                .map(AppointmentDataBundle::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Appointment> getAppointments(){
        return appointmentDatabase.getAllIds().stream()
                .map(x -> appointmentDatabase.get(x))
                .collect(Collectors.toCollection(ArrayList::new));
    }
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
        return doctorDatabase.get(doctorId).getAvailability()
                .stream()
                .filter(x -> dayOfWeek.getValue() == x.getDayOfWeek().getValue())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<TimeBlock> getSingleDayAvailability(Integer doctorId, LocalDate selectedDay){
        return getAvailabilityDataFromDayOfWeek(doctorId, selectedDay.getDayOfWeek()).stream()
                .map(x -> new TimeBlock(ZonedDateTime.of(selectedDay,
                        x.getDoctorStartTime(), ZoneId.of("US/Eastern")),
                        ZonedDateTime.of(selectedDay, x.getDoctorEndTime(),
                                ZoneId.of("US/Eastern"))))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<TimeBlock> parseAvailabilityWithAppointmentData(ArrayList<TimeBlock> availability,
                                                                     Integer doctorId){
        ArrayList<TimeBlock> parsedAvailability = new ArrayList<>();
        ArrayList<ZonedDateTime> startTime = new ArrayList<>();
        for (TimeBlock availabilityTimeBlock: availability) {
            startTime.add(availabilityTimeBlock.getStartTime());
            appointmentDatabase.getAllIds().stream()
                    .map(x -> appointmentDatabase.get(x))
                    .filter(x -> x.getTimeBlock().getStartTime().getDayOfYear() ==
                            availabilityTimeBlock.getStartTime().getDayOfYear())
                    .filter(x -> x.getDoctorID() == doctorId)
                    .filter(x -> calculateNoConflictTime(x, startTime, parsedAvailability))
                    .close();
            if (startTime.get(0) != availabilityTimeBlock.getEndTime()){
                parsedAvailability.add(new TimeBlock(startTime.remove(0), availabilityTimeBlock.getEndTime()));
            }
        }
        return parsedAvailability;
    }

    private boolean calculateNoConflictTime(Appointment selectedAppointment, ArrayList<ZonedDateTime> startTime,
                                            ArrayList<TimeBlock> collectedTimeBlocks){
        if (startTime.get(0) == selectedAppointment.getTimeBlock().getEndTime()){
            startTime.remove(0);
            startTime.add(selectedAppointment.getTimeBlock().getEndTime());
        }
        else {
            collectedTimeBlocks.add(new TimeBlock(startTime.remove(0),
                    selectedAppointment.getTimeBlock().getStartTime()));
            startTime.add(selectedAppointment.getTimeBlock().getEndTime());
        }
        return true;
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