// PHASE 2 FILE

package useCases;

import dataBundles.*;
import database.DataMapperGateway;
import database.Database;
import entities.*;
import utilities.TimeUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Use case class for handling operations and data pertaining to appointments.
 */
public class AppointmentManager {

    private final DataMapperGateway<Appointment> appointmentDatabase;
    private final DataMapperGateway<Doctor> doctorDatabase;
    private final Database database;
    private final Clinic clinicData;

    /**
     *Initializes Appointment Manager with the appointment database, and doctor database.
     */
    public AppointmentManager(Database database){
        this.appointmentDatabase = database.getAppointmentDatabase();
        this.doctorDatabase  = database.getDoctorDatabase();
        this.database = database;
        this.clinicData = database.getClinic();
    }

    /**
     * Book a doctor and patient specific appointment and store it in the appointment database.
     * @param patientData PatientData - data representing a patient entity.
     * @param doctorData DoctorData - data representing a doctor entity.
     * @param year Integer - an integer value that represents a year.
     * @param month Integer - an integer value that represents a month of a year.
     * @param day Integer - an integer value that represents a day of a month.
     * @param hour Integer - an integer value that represents an hour of a day.
     * @param minute Integer - an integer value that represents a minute of an hour.
     * @param lengthOfAppointments an integer value in minutes that represents the length of an appointment.
     * @return AppointmentData - returns the newly booked appointment in an AppointmentData form. otherwise returns null
     * if invalid time was submitted.
     */
    public AppointmentData bookAppointment(PatientData patientData, DoctorData doctorData,
                                           Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                           Integer lengthOfAppointments) {
        TimeBlock proposedTime = new TimeUtils().createTimeBlock(year, month, day, hour, minute,
                lengthOfAppointments);
        if (isValidAppointment(doctorData, proposedTime))  {
            Appointment newApp = new Appointment(proposedTime, doctorData.getId(), patientData.getId());
                    appointmentDatabase.add(newApp);
            return new AppointmentData(newApp);
        }
        return null;
    }
    private boolean isValidAppointment(DoctorData doctorData, TimeBlock timeBlock){
        return doesNotOverlapWithDoctorsAppointments(timeBlock, doctorData) && strictlyOverlapsWithClinicHours(timeBlock);
    }
    public boolean doesNotOverlapWithDoctorsAppointments(UniversalTimeBlockWithDay timeBlock, DoctorData doctorData){
        return getDoctorAppointments(doctorData).stream()
                .anyMatch(x -> overlapsDateAndHours(x, timeBlock));
    }
    private boolean strictlyOverlapsWithClinicHours(UniversalTimeBlock timeBlock){
        for (Availability i: clinicData.getClinicHours()){
            if (i.dayOfWeek().equals(timeBlock.dayOfWeek()) && strictlyWithinHours(i, timeBlock)){
                return true;
            }
        }
        return false;
    }

    public boolean doesNotOverlapWithDoctorsAbsence(UniversalTimeBlockWithDay timeBlock, DoctorData doctorData){
        return getDoctorAbsence(doctorData).stream()
                .anyMatch(x -> overlapsDateAndHours(x, timeBlock));
    }
    /**
     * Removes an Appointment from the database.
     * @param appointmentData AppointmentData - data representing an appointment entity.
     */
    public void removeAppointment(AppointmentData appointmentData){
        appointmentDatabase.remove(appointmentData.getAppointmentId());
    }

    /**
     * Reschedule an appointment, adjusting an appointments time block and validating it.
     * @param appointmentData AppointmentData - data that represents an appointment entity.
     * @param year Integer - an integer value that represents a year.
     * @param month Integer - an integer value that represents a month of a year.
     * @param day Integer - an integer value that represents a day of a month.
     * @param hour Integer - an integer value that represents an hour of a day.
     * @param minute Integer - an integer value that represents a minute of an hour.
     * @param lengthOfAppointments Integer - an integer value in minutes that represents the length of an appointment.
     * @return boolean - a boolean representing if the appointment successfully rescheduled.
     */
    public boolean rescheduleAppointment(AppointmentData appointmentData, Integer year, Integer month, Integer day,
                                         Integer hour, Integer minute, Integer lengthOfAppointments){

        TimeBlock proposedTime = new TimeUtils().createTimeBlock(year, month, day, hour, minute, lengthOfAppointments);

        DoctorData doctorData = new DoctorData(doctorDatabase.get(appointmentData.getDoctorId()));
        if (isValidAppointment(doctorData, proposedTime)){
            appointmentDatabase.add(new Appointment(proposedTime, appointmentData.getDoctorId(), appointmentData.getPatientId()));
            return true;
        }
        return false;
    }

    /**
     * Gets all appointments related to a single patient id.
     * @param patientData PatientData - data that represents the patient entity.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of specific patient
     * Appointments.
     */
    public ArrayList<AppointmentData> getPatientAppointments(PatientData patientData){
        return getAllPatientAppointments(patientData.getId()).stream()
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Gets all appointments related to a single doctor id.
     * @param doctorData DoctorData - data that represents the doctor entity.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of specific doctor
     * Appointments.
     */
    public ArrayList<AppointmentData> getDoctorAppointments(DoctorData doctorData){
        return getAppointments().stream()
                .filter(x -> x.getDoctorId().equals(doctorData.getId()))
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get all appointments in the appointment database.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of many Appointments.
     */
    public ArrayList<AppointmentData> getAllAppointments(){

        return getAppointments().stream()
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get the available time slots between a defined search start and end time in terms of TimeBlockData.
     * @param doctorData DoctorData - data representing a doctor entity.
     * @param year Integer - an integer value that represents a year.
     * @param month Integer - an integer value that represents a month of a year.
     * @param startDay Integer - day of a month that represents the search start day.
     * @param endDay Integer - day of a month that represents the search end day.
     * @return ArrayList<TimeBlockData> - returns an arrayList of TimeBlockData that represents available appointment
     * times between the search start and end date.
     */
    public ArrayList<TimeBlockData> getAvailableTimes(DoctorData doctorData, Integer year, Integer month,
                                                    Integer startDay, Integer endDay){
        TimeUtils timeUtils = new TimeUtils();
        return searchAvailability(doctorData, timeUtils.createLocalDateTime(year, month, startDay, 0, 0),
                timeUtils.createLocalDateTime(year, month, endDay, 23, 59));
    }

    /**
     * Gets all available time for a doctor considering their availability, other appointments, and search time
     * parameters.
     * @param doctorData DoctorData - data representing a doctor entity.
     * @param searchStartTime LocalDateTime - LocalDateTime object representing the beginning of the search period.
     * @param searchEndTime LocalDateTime - LocalDateTime object representing the beginning of the end period.
     * @return ArrayList<TimeBlockData> - an ArrayList of TimeBlocks representing all available timeslots to schedule an
     * Appointment.
     */
    public ArrayList<TimeBlockData> searchAvailability(DoctorData doctorData, LocalDateTime searchStartTime,
                                                       LocalDateTime searchEndTime){
        ArrayList<TimeBlock> totalAvailableTimes = new ArrayList<>();
        for (int numOfDays = searchEndTime.getDayOfYear() - searchStartTime.getDayOfYear(); numOfDays > 0; numOfDays--){
            LocalDate daySearched = searchEndTime.toLocalDate();
            totalAvailableTimes.addAll(parseAvailabilityWithAppointmentData(getSingleDayAvailability(
                    daySearched.minusDays(numOfDays)), doctorData.getId()));
        }
        return totalAvailableTimes.stream()
                .map(TimeBlockData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Gets the availability data from a doctor on a specific enum representing the day of the week.
     * @param dayOfWeek DayOfWeek - an Enum that represents a day of the week without ties to a specific date.
     * @return ArrayList<Availability> - an ArrayList of Availability that holds data on a doctor's available time.
     */
    public ArrayList<Availability> getAvailabilityFromDayOfWeek(DayOfWeek dayOfWeek){
        return database.getClinic().getClinicHours()
                .stream()
                .filter(x -> dayOfWeek.equals(x.getDayOfWeek()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Gets all doctor specific appointments in a single day.
     * @param doctorData  the data representing a specfic doctor in the database.
     * @param selectedDay LocalDate that represents a date without a specific time attached.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of many Appointments.
     */
    public ArrayList<AppointmentData> getScheduleData(DoctorData doctorData, LocalDate selectedDay){
        return getDoctorAppointments(doctorData).stream()
                .filter(x-> x.date().equals(selectedDay))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<AppointmentData> searchScheduleData(DoctorData doctorData, Integer startYear, Integer startMonth,
                                                         Integer startDay, Integer endYear, Integer endMonth, Integer endDay){
        ArrayList<AppointmentData> validAppointments = new ArrayList<>();
        LocalDate startTime = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endTime = LocalDate.of(endYear, endMonth, endDay);
        while (startTime.isBefore(endTime)){
            validAppointments.addAll(getScheduleData(doctorData, startTime));
            startTime = startTime.plusDays(1);
        }
        return validAppointments;
    }



    /**
     * Deletes an absence from a doctor's stored Absence ArrayList.
     * @param doctorData DoctorData - the data representing a specific doctor in the database.
     * @param absence TimeBlock - a TimeBlock representing the period in time that a doctor would be absent.
     */
    public void deleteAbsence(DoctorData doctorData, TimeBlock absence){
        doctorDatabase.get(doctorData.getId()).removeAbsence(absence);
    }

    /**
     * Add an absence TimeBlock to a doctor's stored Absence ArrayList.
     * @param doctorData DoctorData - the data representing a specific doctor in the database.
     * @param startTime LocalDateTime - a LocalDateTime representing the beginning of a new absence.
     * @param endTime LocalDateTime - a LocalDateTime representing the ending of a new absence.
     */
    public void addAbsence(DoctorData doctorData, LocalDateTime startTime, LocalDateTime endTime){
        TimeBlock proposedTime = new TimeBlock(startTime, endTime);
        doctorDatabase.get(doctorData.getId()).addAbsence(new TimeBlock(startTime, endTime));
        getAllAppointments().stream()
                .filter(x -> x.getTimeBlock().getStartTime().getDayOfYear()
                        == proposedTime.getStartTime().getDayOfYear())
                .filter(x -> x.getTimeBlock().getStartTime().isBefore(proposedTime.getStartTime()) &
                        x.getTimeBlock().getEndTime().isAfter(proposedTime.getStartTime()) | x.getTimeBlock().
                        getStartTime().isAfter(proposedTime.getStartTime()) &
                        x.getTimeBlock().getStartTime().isBefore(proposedTime.getEndTime()))
                .forEach(this::removeAppointment);
    }

    /**
     * Get the availability of a doctor in terms of an ArrayList of AvailabilityData.
     * @param doctorData DoctorData - the data representing a specific doctor in the database.
     * @return ArrayList<AvailabilityData> - the ArrayList of AvailabilityData that represents a doctor's availability.
     */
    public ArrayList<AvailabilityData> getAvailabilityData(DoctorData doctorData){
        return database.getClinic().getClinicHours().stream()
                .map(AvailabilityData::new)
                .collect(Collectors.toCollection(ArrayList::new));
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
                        x.getEndTime().isAfter(proposedTime.getStartTime()) | x.getStartTime().isAfter(proposedTime
                        .getStartTime()) & x.getStartTime().isBefore(proposedTime.getEndTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isNoTimeBlockConflictAbsence(Integer doctorId,
                                                 TimeBlock proposedTime){

        return timeConflictList(doctorDatabase.get(doctorId).getAbsence(), proposedTime).isEmpty();
    }

    private ArrayList<TimeBlock> getSingleDayAvailability(LocalDate selectedDay){
        return getAvailabilityFromDayOfWeek(selectedDay.getDayOfWeek()).stream()
                .map(x -> new TimeBlock(LocalDateTime.of(selectedDay, x.getDoctorStartTime()), LocalDateTime
                        .of(selectedDay, x.getDoctorEndTime())))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    private ArrayList<TimeBlock> getTimeBlocksWithPatientAndDoctor(Integer doctorId, Integer patientId){
        return appointmentDatabase.getAllIds().stream()
                .map(appointmentDatabase::get)
                .filter(x -> x.getDoctorId().equals(doctorId))
                .filter(x -> x.getPatientId().equals(patientId))
                .map(Appointment::getTimeBlock)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Appointment> getAllPatientAppointments(Integer patientId){
        return getAppointments().stream()
                .filter(x -> x.getPatientId().equals(patientId))
                .collect(Collectors.toCollection(ArrayList::new));
    }
 //made the assumption that appointments must be sorted
    private ArrayList<TimeBlock> parseAvailabilityWithAppointmentData(ArrayList<TimeBlock> availability,
                                                                      Integer doctorId){
        ArrayList<TimeBlock> parsedAvailability = new ArrayList<>();
        ArrayList<LocalDateTime> startTime = new ArrayList<>();
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

    private boolean calculateNoConflictTime(TimeBlock timeBlock, ArrayList<LocalDateTime> startTime,
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
    private boolean isWithinAvailability(TimeBlock proposedTime, DoctorData doctorData){
        ArrayList<AvailabilityData> s = database.getClinic().getClinicHours().stream()
                .filter(x-> proposedTime.getStartTime().getDayOfWeek().equals(x.getDayOfWeek()))
                .filter(x -> x.getDoctorStartTime().isBefore(proposedTime.startTimeToLocal()) & x.getDoctorEndTime()
                        .isAfter(proposedTime.endTimeToLocal()))
                .map(AvailabilityData::new)
                .collect(Collectors.toCollection(ArrayList::new));
        return s.isEmpty();

    }
    private ArrayList<Appointment> getAppointments(){
        return appointmentDatabase.getAllIds().stream()
                .map(appointmentDatabase::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<TimeBlock> getAppointmentsTimeBlock(){
        return getAppointments().stream()
                .map(Appointment::getTimeBlock)
                .collect(Collectors.toCollection(ArrayList::new));
    }





    private boolean overlapsDateAndHours(UniversalTimeBlockWithDay day1, UniversalTimeBlockWithDay day2){
        return overlaphours(day1, day2) && overlapdate(day1, day2);
    }
    private boolean overlapdate(UniversalTimeBlockWithDay day1, UniversalTimeBlockWithDay day2){
        return day1.date().equals(day2.date());
    }
    private boolean overlaphours(UniversalTimeBlock time1, UniversalTimeBlock time2){
        return isWithinHours(time1, time2.startTime()) && isWithinHours(time1, time2.endTime());
    }
    private boolean isWithinHours(UniversalTimeBlock timeBlock, LocalTime time){
        return time.isAfter(timeBlock.startTime()) && time.isBefore(timeBlock.endTime());
    }
    private boolean strictlyWithinHours(UniversalTimeBlock timeBlock, UniversalTimeBlock timeBlock2){
        return isWithinHours(timeBlock, timeBlock2.startTime()) && isWithinHours(timeBlock, timeBlock2.endTime());
    }
}