package useCases.managers;

import dataBundles.AppointmentData;
import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Appointment;
import entities.AvailabilityData;
import entities.Doctor;
import entities.TimeBlock;
import useCases.AppointmentQueries;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AppointmentManager {
    private DataMapperGateway<Appointment> appointmentDatabase;
    private DataMapperGateway<Doctor> doctorDatabase;
    private Database database;

    /**
     *Initializes Appointment Manager with the appointment database, and doctor database.
     */
    public AppointmentManager(Database database){
        this.appointmentDatabase = database.getAppointmentDatabase();
        this.doctorDatabase  = database.getDoctorDatabase();
        this.database = database;
    }

    /**
     * Books an appointment and adds it to the Appointment database after validating the proposed time scheduled for
     * the new appointment.
     * @param patientId     id of the patient the Appointment was assigned to.
     * @param doctorId      id of the doctor the Appointment was assigned to.
     * @param proposedTime  the new proposed TimeBlock representing the duration of an Appointment.
     * @return an AppointmentData that represents the new Appointment that has been booked.
     */
    public AppointmentData bookAppointment(PatientData patientData, DoctorData doctorData,
                                           Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                           Integer lenOfAppointment) {
        TimeBlock proposedTime = new TimeManager().createTimeBlock(year, month, day, hour, minute,
                lenOfAppointment);
        //add validation
        if (isNoTimeBlockConflictAppointment(getTimeBlocksWithPatientAndDoctor(doctorData.getId(), patientData.getId()),
                proposedTime) & isNoTimeBlockConflictAppointment(getSingleDayAvailability(doctorData.getId(),
                proposedTime.getStartTime().toLocalDate()), proposedTime)) {
            Appointment newApp = new Appointment(proposedTime, doctorData.getId(), patientData.getId());
                    appointmentDatabase.add(newApp);
            return new AppointmentData(newApp);
        }
        return null;
    }
//    public AppointmentData bookAppointment(Integer patientId, Integer doctorId, Integer year, Integer month, Integer day, Integer hour){
//        ZonedDateTime startTime = new ZonedDateTimeCreator().createZonedDataTime(year, month, day, )
//        bookAppointment(patientId, doctorId, database.getClinicDatabase(). )
//    }

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
     *  removes an Appointment from the database.
     * @param appointmentId: Integer id of the Appointment.
     */
    public void removeAppointment(AppointmentData appointmentData){
        appointmentDatabase.remove(appointmentData.getAppointmentId());
    }

    /**
     *  reschedules an appointment and validates the new appointment time before adding it to the database.
     * @param appointmentId Integer id of the Appointment.
     * @param newStart      ZonedDateTime representing a new start time for an Appointment's TimeBlock
     * @param newEnd        ZonedDateTime representing a new end time for an Appointment's TimeBlock
     * @return boolean that represents whether the rescheduling Appointment Time was valid or not.
     */
    public boolean rescheduleAppointment(AppointmentData appointmentData, Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                         Integer lenOfAppointment){
        Appointment appointment = appointmentDatabase.get(appointmentData.getAppointmentId());
        appointmentDatabase.get(appointmentData.getAppointmentId()).setTimeBlock(null);
        TimeBlock proposedTime = new TimeManager().createTimeBlock(year, month, day, hour, minute, lenOfAppointment);
        ArrayList<TimeBlock> consideration = getTimeBlocksWithPatientAndDoctor(appointment.getDoctorId(),
                appointment.getPatientId());
        if (isNoTimeBlockConflictAppointment(consideration, proposedTime)) {
            appointmentDatabase.add(appointment);
            return true;
        }
        return false;
    }

    private ArrayList<TimeBlock> getTimeBlocksWithPatientAndDoctor(Integer doctorId, Integer patientId){
        return appointmentDatabase.getAllIds().stream()
                .map(x -> appointmentDatabase.get(x))
                .filter(x -> x.getDoctorId().equals(doctorId))
                .filter(x -> x.getPatientId().equals(patientId))
                .map(Appointment::getTimeBlock)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * gets all appointments related to a single patient id.
     * @param patientId id of the patient the Appointment was assigned to.
     * @return ArrayList of AppointmentData which includes information of specific patient Appointments.
     */
    public ArrayList<AppointmentData> getPatientAppointments(Integer patientId){
        return getAllPatientAppointments(patientId).stream()
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Appointment> getAllPatientAppointments(Integer patientId){
        return getAppointments().stream()
                .filter(x -> x.getPatientId().equals(patientId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *  gets all appointments related to a single doctor id.
     * @param doctorId id of the doctor the Appointment was assigned to.
     * @return ArrayList of AppointmentData which includes information of specific doctor Appointments.
     */
    public ArrayList<AppointmentData> getDoctorAppointments(Integer doctorId){
        return getAppointments().stream()
                .filter(x -> new AppointmentQueries(x).isDoctorsAppointment(doctorId))
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *  get all appointments in the appointment database.
     * @return ArrayList of AppointmentData which includes information of many Appointments.
     */
    public ArrayList<AppointmentData> getAllAppointments(){

        return getAppointments().stream()
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Appointment> getAppointments(){
        return appointmentDatabase.getAllIds().stream()
                .map(x -> appointmentDatabase.get(x))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * gets all available time for a doctor considering their availability, other appointments, and search time
     * parameters.
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
     *  gets the availability data from a doctor on a specific enum representing the day of the week.
     * @param doctorId  id of the doctor the Appointment was assigned to.
     * @param dayOfWeek an Enum that represents a day of the week without ties to a specific date.
     * @return an ArrayList of AvailabilityData that holds data on a doctor's available time.
     */
    public ArrayList<AvailabilityData> getAvailabilityDataFromDayOfWeek(Integer doctorId, DayOfWeek dayOfWeek){
        return doctorDatabase.get(doctorId).getAvailability()
                .stream()
                .filter(x -> dayOfWeek.equals(x.getDayOfWeek()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *  get TimeBlocks that represents the doctor's availability in a single day.
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
                    .filter(x -> x.getDoctorId().equals(doctorId))
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
        if (startTime.get(0).equals(selectedAppointment.getTimeBlock().getEndTime())){
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
     * gets all doctor specific appointments in a single day.
     * @param doctorData  the data representing a specfic doctor in the database
     * @param selectedDay LocalDate that represents a date without a specific time attached.
     * @return ArrayList of AppointmentData which includes information of many Appointments.
     */
    public ArrayList<AppointmentData> getScheduleData(DoctorData doctorData, LocalDate selectedDay){
        return getAllAppointments().stream()
                .filter(x -> x.getDoctorId().equals(doctorData.getId()))
                .filter(x->x.getTimeBlock().getStartTime().toLocalDate().equals(selectedDay))
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