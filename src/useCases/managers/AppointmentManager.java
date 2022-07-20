package useCases.managers;

import dataBundles.AppointmentData;
import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.TimeBlockData;
import database.DataMapperGateway;
import database.Database;
import entities.Appointment;
import entities.Availability;
import entities.Doctor;
import entities.TimeBlock;

import java.time.*;
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
     *
     * @param patientData       data representing a patient entity.
     * @param doctorData        data representing a doctor entity.
     * @param year              an integer value that represents a year.
     * @param month             an integer value that represents a month of a year.
     * @param day               an integer value that represents a day of a month.
     * @param hour              an integer value that represents an hour of a day.
     * @param minute            an integer value that represents a minute of an hour.
     * @param lenOfAppointment  an integer value in minutes that represents the length of an appointment.
     * @return  returns the newly booked appointment in an AppointmentData form. itherwise returns null if invalid time
     * was submitted.
     */
    public AppointmentData bookAppointment(PatientData patientData, DoctorData doctorData,
                                           Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                           Integer lenOfAppointment) {
        TimeBlock proposedTime = new TimeManager().createTimeBlock(year, month, day, hour, minute,
                lenOfAppointment);
        if (isNoTimeBlockConflictAppointment(getTimeBlocksWithPatientAndDoctor(doctorData.getId(), patientData.getId()),
                proposedTime) & isNoTimeBlockConflictAppointment(getSingleDayAvailability(doctorData.getId(),
                proposedTime.getStartTime().toLocalDate()), proposedTime) &
                isNoTimeBlockConflictAbsence(doctorData.getId(), proposedTime)) {
            Appointment newApp = new Appointment(proposedTime, doctorData.getId(), patientData.getId());
                    appointmentDatabase.add(newApp);
            return new AppointmentData(newApp);
        }
        return null;
    }

    private boolean isNoTimeBlockConflictAppointment(ArrayList<TimeBlock> timeBlockList,
                                                    TimeBlock proposedTime){
        return timeConflictList(timeBlockList, proposedTime).isEmpty();
    }
    private ArrayList<TimeBlock> timeConflictList(ArrayList<TimeBlock> timeBlockList, TimeBlock proposedTime){
        return timeBlockList.stream()
                .filter(x -> x.getStartTime().getDayOfYear()
                        == proposedTime.getStartTime().getDayOfYear())
                .filter(x -> x.getStartTime().isBefore(proposedTime.getStartTime()) &
                        x.getEndTime().isAfter(proposedTime.getStartTime()))
                .filter(x -> x.getStartTime().isAfter(proposedTime.getStartTime()) &
                        x.getStartTime().isBefore(proposedTime.getEndTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isNoTimeBlockConflictAbsence(Integer doctorId,
                                                     TimeBlock proposedTime){
        return timeConflictList(doctorDatabase.get(doctorId).getAbsence(), proposedTime).isEmpty();
    }

    /**
     *  removes an Appointment from the database.
     * @param appointmentData   data representing an appointment entity.
     */
    public void removeAppointment(AppointmentData appointmentData){
        appointmentDatabase.remove(appointmentData.getAppointmentId());
    }

    /**
     *
     * @param appointmentData data that represents an appointment entity.
     * @param year              an integer value that represents a year.
     * @param month             an integer value that represents a month of a year.
     * @param day               an integer value that represents a day of a month.
     * @param hour              an integer value that represents an hour of a day.
     * @param minute            an integer value that represents a minute of an hour.
     * @param lenOfAppointment  an integer value in minutes that represents the length of an appointment.
     * @return a boolean representing if the appointment successfully rescheduled.
     */
    public boolean rescheduleAppointment(AppointmentData appointmentData, Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                         Integer lenOfAppointment){
        Appointment appointment = appointmentDatabase.get(appointmentData.getAppointmentId());
        appointmentDatabase.get(appointmentData.getAppointmentId()).setTimeBlock(null);
        TimeBlock proposedTime = new TimeManager().createTimeBlock(year, month, day, hour, minute, lenOfAppointment);
        ArrayList<TimeBlock> consideration = getTimeBlocksWithPatientAndDoctor(appointment.getDoctorId(),
                appointment.getPatientId());
        if (isNoTimeBlockConflictAppointment(consideration, proposedTime) &
                isNoTimeBlockConflictAppointment(getSingleDayAvailability(appointmentData.getDoctorId(),
                proposedTime.getStartTime().toLocalDate()), proposedTime) &
                isNoTimeBlockConflictAbsence(appointmentData.getDoctorId(), proposedTime)){
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
     * @param patientData Data that represents the patient entity
     * @return ArrayList of AppointmentData which includes information of specific patient Appointments.
     */
    public ArrayList<AppointmentData> getPatientAppointments(PatientData patientData){
        return getAllPatientAppointments(patientData.getId()).stream()
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
     * @param doctorData Data that represents the doctor entity.
     * @return ArrayList of AppointmentData which includes information of specific doctor Appointments.
     */
    public ArrayList<AppointmentData> getDoctorAppointments(DoctorData doctorData){
        return getAppointments().stream()
                .filter(x -> x.getDoctorId().equals(doctorData.getId()))
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
    private ArrayList<TimeBlock> getAppointmentsTimeBlock(){
        return getAppointments().stream()
                .map(Appointment::getTimeBlock)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *Get the available time slots between a defined search start and end time in terms of TimeBlockData
     * @param doctorData    data representing a doctor entity.
     * @param year          an integer value that represents a year.
     * @param month         an integer value that represents a month of a year.
     * @param startDay      day of a month that represents the search start day.
     * @param endDay        day of a month that represents the search end day.
     * @return returns an arrayList of TimeBlockData that represents available appointment times between the search
     * start and end date.
     */
    public ArrayList<TimeBlockData> getAvailableTimes(DoctorData doctorData, Integer year, Integer month,
                                                    Integer startDay, Integer endDay){
        TimeManager timeManager = new TimeManager();
        return searchAvailability(doctorData, timeManager.createZonedDataTime(year, month, startDay, 0, 0),
                timeManager.createZonedDataTime(year, month, endDay, 23, 59));
    }
    /**
     * gets all available time for a doctor considering their availability, other appointments, and search time
     * parameters.
     * @param doctorData        data representing a doctor entity.
     * @param searchStartTime   ZonedDateTime representing the beginning of the search period
     * @param searchEndTime     ZonedDateTime representing the beginning of the end period
     * @return an ArrayList of TimeBlocks representing all available timeslots to schedule an Appointment.
     */
    public ArrayList<TimeBlockData> searchAvailability(DoctorData doctorData, ZonedDateTime searchStartTime,
                                                       ZonedDateTime searchEndTime){
        ArrayList<TimeBlock> totalAvailableTimes = new ArrayList<>();
        for (int numOfDays = searchEndTime.getDayOfYear() - searchStartTime.getDayOfYear(); numOfDays > 0; numOfDays--){
            LocalDate daySearched = searchEndTime.toLocalDate();
            totalAvailableTimes.addAll(parseAvailabilityWithAppointmentData(getSingleDayAvailability(doctorData.getId(),
                    daySearched.minusDays(numOfDays)), doctorData.getId()));
        }
        return totalAvailableTimes.stream()
                .map(TimeBlockData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     *  gets the availability data from a doctor on a specific enum representing the day of the week.
     * @param doctorId  id of the doctor the Appointment was assigned to.
     * @param dayOfWeek an Enum that represents a day of the week without ties to a specific date.
     * @return an ArrayList of Availability that holds data on a doctor's available time.
     */
    public ArrayList<Availability> getAvailabilityFromDayOfWeek(Integer doctorId, DayOfWeek dayOfWeek){
        return doctorDatabase.get(doctorId).getAvailability()
                .stream()
                .filter(x -> dayOfWeek.equals(x.getDayOfWeek()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    private ArrayList<TimeBlock> getSingleDayAvailability(Integer doctorId, LocalDate selectedDay){
        return getAvailabilityFromDayOfWeek(doctorId, selectedDay.getDayOfWeek()).stream()
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
            ArrayList<TimeBlock> allConflicts = getAppointmentsTimeBlock();
            allConflicts.addAll(doctorDatabase.get(doctorId).getAbsence());
                    allConflicts.stream()
                    .filter(x -> x.getStartTime().getDayOfYear() ==
                            availabilityTimeBlock.getStartTime().getDayOfYear())
                    .filter(x -> calculateNoConflictTime(x, startTime, parsedAvailability))
                    .close();
            if (startTime.get(0) != availabilityTimeBlock.getEndTime()){
                parsedAvailability.add(new TimeBlock(startTime.remove(0), availabilityTimeBlock.getEndTime()));
            }
        }
        return parsedAvailability;
    }

    private boolean calculateNoConflictTime(TimeBlock timeBlock, ArrayList<ZonedDateTime> startTime,
                                            ArrayList<TimeBlock> collectedTimeBlocks){
        if (startTime.get(0).equals(timeBlock.getEndTime())){
            startTime.remove(0);
            startTime.add(timeBlock.getEndTime());
        }
        else {
            collectedTimeBlocks.add(new TimeBlock(startTime.remove(0),
                    timeBlock.getStartTime()));
            startTime.add(timeBlock.getEndTime());
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

    /**
     * A function that adds a new availability section to a doctor's ArrayList
     * @param doctorData        the data representing a specific doctor in the database
     * @param dayOfWeek         An enum that represents a particular day of the week
     * @param hour              An integer value representing the hour of LocalTime
     * @param minute            An integer value representing the minute of an hour of LocalTime
     * @param lenOfAvailability The length of the newly added availability in terms of minutes
     */
    public void newAvailability(DoctorData doctorData, DayOfWeek dayOfWeek, Integer hour, Integer minute,
                                      Integer lenOfAvailability){
        TimeManager timeManager = new TimeManager();
        doctorDatabase.get(doctorData.getId()).addAvailability(new Availability(dayOfWeek,
                timeManager.createLocalTime(hour, minute, 0), timeManager.createLocalTime(hour, minute,
                0).plusMinutes(lenOfAvailability)));
    }

    /**
     * removes an Availability from a doctor's ArrayList Availability
     * @param doctorData        the data representing a specific doctor in the database
     * @param availability      data that represents a reoccurring time in  which a doctor can have an appointment
     */
    public void removeAvailability(DoctorData doctorData, Availability availability) {
        doctorDatabase.get(doctorData.getId()).removeAvailability(availability);
        removeAppointmentConflictAvailability(doctorData, availability);
        //send notification to patient that their appointment was removed
    }

    /**
     *
     * @param availability
     * @param newStart
     * @param newEnd
     */
    public void adjustAvailability(Availability availability, LocalTime newStart, LocalTime newEnd){
        if (newStart.isAfter(availability.getDoctorStartTime())){
            getAllAppointments().stream()
                    .filter(x-> x.getTimeBlock().getStartTime().getDayOfWeek() == availability.getDayOfWeek())
                    .filter(x ->x.getTimeBlock().startTimeToLocal().isBefore(newStart))
                    .forEach(this::removeAppointment);
        }
        else if (newEnd.isBefore(availability.getDoctorEndTime())){
            getAllAppointments().stream()
                    .filter(x-> x.getTimeBlock().getStartTime().getDayOfWeek() == availability.getDayOfWeek())
                    .filter(x ->x.getTimeBlock().endTimeToLocal().isAfter(newEnd))
                    .forEach(this::removeAppointment);
        }
        availability.setDoctorStartTime(newStart);
        availability.setDoctorEndTime(newEnd);
;
        }

    private boolean overlapDay(AppointmentData appointmentData, Availability availability){
        return appointmentData.getTimeBlock().getStartTime().getDayOfWeek().equals(availability.getDayOfWeek());
    }
    private boolean overlapTime (AppointmentData appointmentData, Availability availability){
        return (appointmentData.getTimeBlock().startTimeToLocal().isBefore(availability.getDoctorStartTime()) &
                appointmentData.getTimeBlock().endTimeToLocal().isAfter(availability.getDoctorStartTime())) |
                (appointmentData.getTimeBlock().startTimeToLocal().isBefore(availability.getDoctorEndTime()) &
                        appointmentData.getTimeBlock().endTimeToLocal().isAfter(availability.getDoctorEndTime()));
    }
    private void removeAppointmentConflictAvailability(DoctorData doctorData, Availability availability){
        getDoctorAppointments(doctorData).stream()
                .filter(x-> overlapDay(x, availability))
                .filter(x -> overlapTime(x, availability))
                .forEach(this::removeAppointment);
    }

    /**
     *
     * @param doctorData
     * @param absence
     */
    public void deleteAbsence(DoctorData doctorData, TimeBlock absence){
        doctorDatabase.get(doctorData.getId()).removeAbsence(absence);
    }

    /**
     *
     * @param doctorData
     * @param startTime
     * @param endTime
     */
    public void addAbsence(DoctorData doctorData, ZonedDateTime startTime, ZonedDateTime endTime){
        TimeBlock proposedTime = new TimeBlock(startTime, endTime);
        doctorDatabase.get(doctorData.getId()).addAbsence(new TimeBlock(startTime, endTime));
        getAllAppointments().stream()
                .filter(x -> x.getTimeBlock().getStartTime().getDayOfYear()
                        == proposedTime.getStartTime().getDayOfYear())
                .filter(x -> x.getTimeBlock().getStartTime().isBefore(proposedTime.getStartTime()) &
                        x.getTimeBlock().getEndTime().isAfter(proposedTime.getStartTime()))
                .filter(x -> x.getTimeBlock().getStartTime().isAfter(proposedTime.getStartTime()) &
                        x.getTimeBlock().getStartTime().isBefore(proposedTime.getEndTime()))
                .forEach(this::removeAppointment);
    }
}