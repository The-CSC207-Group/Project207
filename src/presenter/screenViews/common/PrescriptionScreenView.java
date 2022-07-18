package presenter.screenViews.common;

import dataBundles.PrescriptionData;
import presenter.entityViews.PrescriptionView;
import presenter.screenViews.ScreenView;

import java.util.InputMismatchException;
import java.util.List;

public class PrescriptionScreenView extends ScreenView {

    /**
     * Delete prescription view. Show an enumeration of all prescription and ask user for integer input corresponding
     * to a selection.
     * @param prescriptionData a list of PrescriptionData to display.
     * @return an integer, representing the selected PrescriptionData from the list.
     *         null, if and only if the user input is not an integer.
     */
    public Integer deletePrescriptionPrompt(List<PrescriptionData> prescriptionData) {
        new PrescriptionView().viewFullAsEnumerationFromList(prescriptionData);
        warningMessage("This action cannot be undone!");
        try {
            return inputInt("Input prescription number to delete: ");
        } catch (InputMismatchException e) {
            return null;
        }
    }

    /**
     * Error raised when the user inputted integer is outside the size of the given data bundle list
     */
    public void showDeletePrescriptionOutOfRangeError() {
        errorMessage("Could not delete prescription: index out of range.");
    }

    /**
     * Error raised when the user input is not an integer.
     */
    public void showDeletePrescriptionNotAnIntegerError() {
        errorMessage("Could not delete prescription: please input a valid integer.");
    }
}
