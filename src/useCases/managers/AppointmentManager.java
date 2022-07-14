package useCases.managers;

import dataBundles.AppointmentDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.AppointmentQueries;


import java.time.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AppointmentManager {
    private DataMapperGateway<Appointment> appointmentDatabase;
    private DataMapperGateway<Doctor> doctorDatabase;

    /**
     *
     * @param appointmentDatabase database for appointments.
     * @param doctorDatabase      database for doctors.
     */
    public AppointmentManager(DataMapperGateway<Appointment> appointmentDatabase, DataMapperGateway<Doctor> doctorDatabase){
        this.appointmentDatabase = appointmentDatabase;
        this.doctorDatabase  = doctorDatabase;
    }

    /**
     *
     * @param patientId     id of the patient the Appointment was assigned to.
     * @param doctorId      id of the doctor the Appointment was assigned to.
     * @param proposedTime  the new proposed TimeBlock representing the duration of an Appointment.
     * @return an AppointmentDataBundle that represents the new Appointment that has been booked
     */
    public AppointmentDataBundle bookAppointment(Integer patientId, Integer doctorId, TimeBlock proposedTime) {

        if (isNoTimeBlockConflictAppointment(getTimeBlocksWithPatientAndDoctor(doctorId, patientId), proposedTime)
                & isNoTimeBlockConflictAppointment(getSingleDayAvailability(doctorId,
                proposedTime.getStartTime().toLocalDate()), proposedTime)) {
            Appointment newApp = new Appointment(proposedTime, doctorId, patientId);
                    appointmentDatabase.add(newApp);
            return new AppointmentDataBundle(newApp.getId(), newApp);
        }
        return null;
    }

    private boolean isNoTimeBlockConflictAppointment(ArrayList<TimeBlock> timeBlockList,
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

    /**
     *
     * @param appointmentId: Integer id of the Appointment.
     */
    public void removeAppointment(Integer appointmentId){
        appointmentDatabase.remove(appointmentId);
    }

    /**
     *
     * @param appointmentId Integer id of the Appointment.
     * @param newStart      ZonedDateTime representing a new start time for an Appointment's TimeBlock
     * @param newEnd        ZonedDateTime representing a new end time for an Appointment's TimeBlock
     * @return boolean that represents whether the rescheduling Appointment Time was valid or not.
     */
    public boolean rescheduleAppointment(Integer appointmentId, ZonedDateTime newStart, ZonedDateTime newEnd){
        Appointment appointment = appointmentDatabase.get(appointmentId);
        removeAppointment(appointmentId);
        TimeBlock proposedTime = new TimeBlock(newStart, newEnd);
        ArrayList<TimeBlock> consideration = getTimeBlocksWithPatientAndDoctor(appointment.getDoctorId(),
                appointment.getPatientId());
        if (isNoTimeBlockConflictAppointment(consideration, proposedTime)) {
            bookAppointment(appointment.getPatientId(), appointment.getDoctorId(), proposedTime);
            return true;
        }
        return false;
    }

    private ArrayList<TimeBlock> getTimeBlocksWithPatientAndDoctor(Integer doctorId, Integer patientId){
        return appointmentDatabase.getAllIds().stream()
                .map(x -> appointmentDatabase.get(x))
                .filter(x -> x.getDoctorId() == doctorId)
                .filter(x -> x.getPatientId() == patientId)
                .map(Appointment::getTimeBlock)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @param patientId id of the patient the Appointment was assigned to.
     * @return ArrayList of AppointmentDataBundle which includes information of specific patient Appointments.
     */
    public ArrayList<AppointmentDataBundle> getPatientAppointments(Integer patientId){
        return getAllPatientAppointments(patientId).stream()
                .map(x -> new AppointmentDataBundle(x.getId(), x))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Appointment> getAllPatientAppointments(Integer patientId){
        return getAppointments().stream()
                .filter(x -> x.getPatientId() == patientId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @param doctorId id of the doctor the Appointment was assigned to.
     * @return ArrayList of AppointmentDataBundle which includes information of specific doctor Appointments.
     */
    public ArrayList<AppointmentDataBundle> getDoctorAppointments(Integer doctorId){
        return getAppointments().stream()
                .filter(x -> new AppointmentQueries(x).isDoctorsAppointment(doctorId))
                .map(x -> new AppointmentDataBundle(x.getId(), x))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @return ArrayList of AppointmentDataBundle which includes information of many Appointments.
     */
    public ArrayList<AppointmentDataBundle> getAllAppointments(){

        return getAppointments().stream()
                .map(x -> new AppointmentDataBundle(x.getId(), x))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Appointment> getAppointments(){
        return appointmentDatabase.getAllIds().stream()
                .map(x -> appointmentDatabase.get(x))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @param doctorId          id of the doctor the Appointment was assigned to.
     * @param searchStartTime   ZonedDateTime representing the beginning of the search period
     * @param searchEndTime     ZonedDateTime representing the beginning of the end period
     * @return an ArrayList of TimeBlocks representing all available timeslots to schedule an Appointment.
     */
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

    /**
     *
     * @param doctorId  id of the doctor the Appointment was assigned to.
     * @param dayOfWeek an Enum that represents a day of the week without ties to a specific date.
     * @return an ArrayList of AvailabilityData that holds data on a doctor's available time.
     */
    public ArrayList<AvailabilityData> getAvailabilityDataFromDayOfWeek(Integer doctorId, DayOfWeek dayOfWeek){
        return doctorDatabase.get(doctorId).getAvailability()
                .stream()
                .filter(x -> dayOfWeek.getValue() == x.getDayOfWeek().getValue())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @param doctorId      id of the doctor the Appointment was assigned to.
     * @param selectedDay   LocalDate that represents a date without a specific time attached.
     * @return an ArrayList of TimeBlocks where each TimeBlock represents available time of a doctor on a specific date.
     */
    public ArrayList<TimeBlock> getSingleDayAvailability(Integer doctorId, LocalDate selectedDay){
        return getAvailabilityDataFromDayOfWeek(doctorId, selectedDay.getDayOfWeek()).stream()
                .map(x -> new TimeBlock(ZonedDateTime.of(selectedDay,
                        x.getDoctorStartTime(), ZoneId.of("US/Eastern")),
                        ZonedDateTime.of(selectedDay, x.getDoctorEndTime(),
                                ZoneId.of("US/Eastern"))))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<TimeBlock> parseAvailabilityWithAppointmentData(ArrayList<TimeBlock> availability,
                                                                     Integer doctorId){
        ArrayList<TimeBlock> parsedAvailability = new ArrayList<>();
        ArrayList<ZonedDateTime> startTime = new ArrayList<>();
        for (TimeBlock availabilityTimeBlock: availability) {
            startTime.add(availabilityTimeBlock.getStartTime());
            appointmentDatabase.getAllIds().stream()
                    .map(x -> appointmentDatabase.get(x))
                    .filter(x -> x.getTimeBlock().getStartTime().getDayOfYear() ==
                            availabilityTimeBlock.getStartTime().getDayOfYear())
                    .filter(x -> x.getDoctorId() == doctorId)
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

    /**
     *
     * @param doctorId    id of the doctor the Appointment was assigned to.
     * @param selectedDay LocalDate that represents a date without a specific time attached.
     * @return ArrayList of AppointmentDataBundle which includes information of many Appointments.
     */
    public ArrayList<AppointmentDataBundle> getScheduleData(Integer doctorId, LocalDate selectedDay){
        return getAllAppointments().stream()
                .filter(x -> x.getDoctorID().equals(doctorId))
                .filter(x->x.getTimeBlock().getStartTime().toLocalDate() == selectedDay)
                .collect(Collectors.toCollection(ArrayList::new));
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
}