package presenter.entityViews;

import dataBundles.ContactData;

import java.util.List;

public class ContactView extends EntityView<ContactData> {

    @Override
    public String view(ContactData item) {
        return null;
    }

    public String viewName(ContactData item) {
        return "Your name is " + item.getName() + ".";
    }

    public String viewEmail(ContactData item) {
        return "Your email is " + item.getEmail() + ".";
    }

    public String viewPhoneNumber(ContactData item) {
        return "Your phone number is " + item.getPhoneNumber() + ".";
    }

    public String viewAddress(ContactData item) {
        return "Your address is " + item.getAddress() + ".";
    }

    public String viewBirthday(ContactData item) {
        return "Your date of birth is " + item.getBirthday().toString() + ".";
    }

    public String viewEmergencyContactName(ContactData item) {
        return "Your emergency contact name is " + item.getEmergencyContactName() + ".";
    }

    public String viewEmergencyContactEmail(ContactData item) {
        return "Your emergency contact email is " + item.getEmergencyContactEmail() + ".";
    }

    public String viewEmergencyContactPhoneNumber(ContactData item) {
        return "Your emergency contact phone number is " + item.getEmergencyContactPhoneNumber() + ".";
    }

    public String viewEmergencyRelationship(ContactData item) {
        return "Your emergency contact is your " + item.getEmergencyRelationship() + ".";
    }

    public String viewFull(ContactData item) {
        return "Your name is " + item.getName() + ".\n" +
                "Your email is " + item.getEmail() + ".\n" +
                "Your phone number is " + item.getPhoneNumber() + ".\n" +
                "Your address is " + item.getAddress() + ".\n" +
                "Your date of birth is " + item.getBirthday().toString() + ".\n" +
                "Your emergency contact name is " + item.getEmergencyContactName() + ".\n" +
                "Your emergency contact email is " + item.getEmergencyContactEmail() + ".\n" +
                "Your emergency contact phone number is " + item.getEmergencyContactPhoneNumber() + ".\n" +
                "Your emergency contact is your " + item.getEmergencyRelationship() + ".";
    }

    public String viewNameFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewName(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewEmailFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewEmail(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewPhoneNumberFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewPhoneNumber(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewAddressFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewAddress(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewBirthdayFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewBirthday(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewEmergencyContactNameFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewEmergencyContactName(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewEmergencyContactEmailFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewEmergencyContactEmail(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewEmergencyContactPhoneNumberFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewEmergencyContactPhoneNumber(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewEmergencyRelationshipFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewEmergencyRelationship(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewFullFromList(List<ContactData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewFull(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

}
