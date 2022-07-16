package controllers;

import dataBundles.AppointmentDataBundle;
import dataBundles.LogDataBundle;
import dataBundles.PatientDataBundle;
import dataBundles.PrescriptionDataBundle;
import presenter.screenViews.PatientView;
import useCases.accessClasses.PatientAccess;

import java.util.ArrayList;

public class PatientController extends TerminalController {
    private PatientAccess patientAccess;
    private PatientView patientView;
    private PatientDataBundle patientData;

    public PatientController(Context context, PatientDataBundle patientData) {
        super(context);
        this.patientAccess = new PatientAccess(getDatabase());
        this.patientData = patientData;
    }

    class ChangePassword implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            String newPassword1 = presenter.promptPopup("Enter a new password");
            String newPassword2 = presenter.promptPopup("Re-enter the new password");
            if (newPassword1.equals(newPassword2)){
                patientAccess.changeCurrentUserPassword(patientData.getId(), newPassword1);
            } else {
                presenter.errorMessage("These do not match");
            }
            return false;
        }
    }

    class ViewAppointments implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<AppointmentDataBundle> appointments = patientAccess.getAppointments(patientData.getId());
            return false;
        }
    }

    class ViewAllPrescriptions implements Command {

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionDataBundle> prescriptions = patientAccess.getAllPrescriptions(patientData.getId());
            return false;
        }
    }

    class ViewActivePrescriptions implements Command {
        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<PrescriptionDataBundle> prescriptions = patientAccess.getActivePrescriptions(patientData.getId());
            return false;
        }
    }

    class GetLogs implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            ArrayList<LogDataBundle> logs = patientAccess.getLogs(patientData.getUsername());
            return false;
        }
    }

    @Override
    void WelcomeMessage() {
    }
}
