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
import java.util.stream.Stream;

/**
 * Use case class for handling operations and data pertaining to appointments.
 */
public class AppointmentManager {

    private final DataMapperGateway<Appointment> appointmentDatabase;
    private final DataMapperGateway<Doctor> doctorDatabase;

    private final DoctorManager doctorManager;
    private final Database database;
    private final Clinic clinicData;

    /**
     * Initializes Appointment Manager with the appointment database, and doctor database.
     */
    public AppointmentManager(Database database) {
        this.appointmentDatabase = database.getAppointmentDatabase();
        this.doctorDatabase = database.getDoctorDatabase();
        this.database = database;
        this.clinicData = database.getClinic();
        this.doctorManager = new DoctorManager(database);
    }

    /**
     * Book a doctor and patient specific appointment and store it in the appointment database. startTime and endTime
     * must be on the same day.
     * @param patientData          PatientData - data representing a patient entity.
     * @param doctorData           DoctorData - data representing a doctor entity.
     * @param startTime            startTime - LocalDateTime representing that start time of a proposed appointment.
     * @param endTime              endTime - LocalDateTime representing that end time of a proposed appointment.
     * @return AppointmentData - returns the newly booked appointment in an AppointmentData form. otherwise returns null
     * if invalid time was submitted.
     */
    public AppointmentData bookAppointment(PatientData patientData, DoctorData doctorData, LocalDateTime startTime,
                                           LocalDateTime endTime) {

        TimeBlock proposedTime = new TimeBlock(startTime, endTime);

        if (isValidAppointment(doctorData, proposedTime)) {
            Appointment newApp = new Appointment(proposedTime, doctorData.getId(), patientData.getId());
            appointmentDatabase.add(newApp);
            return new AppointmentData(newApp);
        }
        return null;
    }

    /**
     * Removes an Appointment from the database.
     *
     * @param appointmentData AppointmentData - data representing an appointment entity.
     */
    public void removeAppointment(AppointmentData appointmentData) {
        appointmentDatabase.remove(appointmentData.getAppointmentId());
    }

    /**
     * Reschedule an appointment, adjusting an appointments time block and validating it.
     *
     * @param appointmentData      AppointmentData - data that represents an appointment entity.
     * @param year                 Integer - an integer value that represents a year.
     * @param month                Integer - an integer value that represents a month of a year.
     * @param day                  Integer - an integer value that represents a day of a month.
     * @param hour                 Integer - an integer value that represents an hour of a day.
     * @param minute               Integer - an integer value that represents a minute of an hour.
     * @param lengthOfAppointments Integer - an integer value in minutes that represents the length of an appointment.
     * @return boolean - a boolean representing if the appointment successfully rescheduled.
     */
    public boolean rescheduleAppointment(AppointmentData appointmentData, Integer year, Integer month, Integer day,
                                         Integer hour, Integer minute, Integer lengthOfAppointments) {

        TimeBlock proposedTime = new TimeUtils().createTimeBlock(year, month, day, hour, minute, lengthOfAppointments);
        Appointment selectedAppointment = appointmentDatabase.get(appointmentData.getAppointmentId());

        database.getAppointmentDatabase().remove(appointmentData.getAppointmentId());
        DoctorData doctorData = new DoctorData(doctorDatabase.get(appointmentData.getDoctorId()));
        if (isValidAppointment(doctorData, proposedTime)) {
            appointmentDatabase.add(new Appointment(proposedTime, appointmentData.getDoctorId(), appointmentData.getPatientId()));
            return true;
        }
        appointmentDatabase.add(selectedAppointment);
        return false;
    }

    /**
     * Gets all appointments related to a single patient id.
     *
     * @param patientData PatientData - data that represents the patient entity.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of specific patient
     * Appointments.
     */
    public ArrayList<AppointmentData> getPatientAppointments(PatientData patientData) {
        return getAllPatientAppointments(patientData.getId())
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Gets all appointments related to a single doctor id.
     *
     * @param doctorData DoctorData - data that represents the doctor entity.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of specific doctor
     * Appointments.
     */
    public ArrayList<AppointmentData> getDoctorAppointments(DoctorData doctorData) {
        return getAppointments().stream()
                .filter(x -> x.getDoctorId().equals(doctorData.getId()))
                .map(AppointmentData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Gets the availability data from a doctor on a specific enum representing the day of the week.
     *
     * @param dayOfWeek DayOfWeek - an Enum that represents a day of the week without ties to a specific date.
     * @return AvailabilityData - returns AvailabilityData that represents the clinic's operating hours on the specific
     * DayOfWeek.
     */
    public AvailabilityData getAvailabilityFromDayOfWeek(DayOfWeek dayOfWeek) {
        Availability availability = database.getClinic().getClinicHours().stream()
                .filter(x -> dayOfWeek.equals(x.getDayOfWeek()))
                .findFirst().orElse(null);
        if (availability == null) {
            return null;
        }
        return new AvailabilityData(availability);
    }
    /**
     * Gets all doctor specific appointments in a single day.
     *
     * @param doctorData  the data representing a specfic doctor in the database.
     * @param selectedDay LocalDate that represents a date without a specific time attached.
     * @return ArrayList<AppointmentData> - ArrayList of AppointmentData which includes information of many Appointments.
     */
    public ArrayList<AppointmentData> getSingleDayAppointment(DoctorData doctorData, LocalDate selectedDay) {
        return getDoctorAppointments(doctorData).stream()
                .filter(x -> x.date().equals(selectedDay))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Stream<Appointment> getAllPatientAppointments(Integer patientId) {
        return getAppointments().stream()
                .filter(x -> x.getPatientId().equals(patientId));
    }

    private ArrayList<Appointment> getAppointments() {
        return appointmentDatabase.getAllIds().stream()
                .map(appointmentDatabase::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isValidAppointment(DoctorData doctorData, TimeBlock timeBlock) {
        return doesNotOverlapWithDoctorsAppointments(timeBlock, doctorData) && strictlyOverlapsWithClinicHours(timeBlock);
    }

    private boolean doesNotOverlapWithDoctorsAppointments(UniversalTimeBlockWithDay timeBlock, DoctorData doctorData) {
        return getDoctorAppointments(doctorData).stream()
                .noneMatch(x -> overlapsDateAndHours(x.getTimeBlock(), timeBlock));
    }

    private boolean strictlyOverlapsWithClinicHours(UniversalTimeBlock timeBlock) {
        for (Availability i : clinicData.getClinicHours()) {
            if (i.dayOfWeek().equals(timeBlock.dayOfWeek()) && strictlyWithinHours(i, timeBlock)) {
                return true;
            }
        }
        return false;
    }
    private boolean overlapsDateAndHours(UniversalTimeBlockWithDay day1, UniversalTimeBlockWithDay day2) {
        return (overlapHours(day1, day2) || overlapHours(day2, day1)) && overlapDate(day1, day2);
    }
    private boolean overlapDate(UniversalTimeBlockWithDay day1, UniversalTimeBlockWithDay day2) {
        return day1.date().equals(day2.date());
    }
    private boolean overlapHours(UniversalTimeBlock time1, UniversalTimeBlock time2) {
        return isWithinHours(time1, time2.startTime()) || isWithinHours(time1, time2.endTime());
    }
    private boolean isWithinHours(UniversalTimeBlock timeBlock, LocalTime time) {
        return !time.isBefore(timeBlock.startTime()) && !time.isAfter(timeBlock.endTime());
    }
    private boolean strictlyWithinHours(UniversalTimeBlock timeBlock, UniversalTimeBlock timeBlock2) {
        return isWithinHours(timeBlock, timeBlock2.startTime()) && isWithinHours(timeBlock, timeBlock2.endTime());
    }
    private boolean isWithinDate(LocalDate time1, LocalDate time2, UniversalTimeBlockWithDay timeBlock) {
        return time1.isBefore(timeBlock.date()) && time2.isAfter(timeBlock.date());
    }
}