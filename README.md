# CSC207 Project Phase 1
we've certainly come a long way since phase 0 of the program to the point where you can barely see any phase 0 code still being used! The size of the project quadrupled, but as result, there are some functionalities that we've poured our heart and soul into but don't seem to be quite ready for phase 1 submission.  

## _**PLEASE NOTE: Anything related to appointments, availability, absence, and reports have been moved to phase 2 of the project, and should not be graded.**_
## How to Install The Required Dependencies

First make you are using java 11: In order to set up the SDK, click `Setup SDK` and then select `coretto-11` (if this
option isn't available, you'll need to add the SDK to Intellij, there are some tutorials provided by intellij you can
follow).

All libraries will be automatically installed by intellij when the program first starts up.

The xml metadata for the libraries can be found in: `.idea/libraries`

## How to Run The Program

To start the program, Run Main.

Use `help` to see all possible commands at any point of the program. 

## Exiting The Program

To properly close the program, use the built-in `exit` command that lives in the user login page in order to save data
to the UserJsonDatabase. If you already logged into a user, use the `sign out` command and then use the `exit` command.

## Command List:

### Pre-Login:

*Note: Only a secretaries and admins can create Patient and Doctor accounts, and only an Admin can create secretary accounts; reference the admin section below.*

* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
* `view clinic info` - Shows a list of information relating to specific clinic.
* `sign in` - Log into a pre-existing account saved in the Json Database.


### Patient Post-Login:
* `delete account` - Deletes account and account data from Json Database, this will bring you back to the login page.
* `change password` - Change the login password and update it in the Json Database.
* `history` - Shows all previous login times.
* `sign out` - Exits the post-login screen and saves and new data to the Json Database, this will bring you back to the login page.

### Doctor Post-Login:
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
* `change password` - Change the login password for the current user and update it in the Json Database.
* `contact details` - Access the contact commands screen, which new commands relevant to contact. see Contact Commands below.
* `sign out` - Exits the post-login screen and saves and new data to the Json Database, this will bring you back to the login page.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `load patient` - Loads a patient, allowing the user to access commands related to the patient loaded; See **patient Loaded Doctor** Commands below.

### Secretary Post-Login:
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
* `delete patient` - deletes a patient user account from the Json Databased based on inputted username.
* `change password` - Change the login password for the current user and update it in the Json Database.
* `contact details` - Access the contact commands screen, which new commands relevant to contact. see Contact Commands below.
* `sign out` - Exits the post-login screen and saves and new data to the Json Database, this will bring you back to the login page.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `load patient` - Loads a patient, allowing the user to access commands related to the patient loaded; See **patient Loaded Secretary Commands** below.

### Admin:

To access admin privileges, use the provided admin account:

username: `root`

password: `root`

To create another admin account, use the `create admin` command while logged into the `root` account.

### Admin Post-Login:

* `change user password` - Change the password of another user's account by inputting the user's unique username and new password.
* `create secretary` - Create a new secretary account and store it in the JsonDatabase.
* `create doctor` - Create a new doctor account and store it in the JsonDatabase.
* `change password` - Change the login password and update it in the Json Database.
* `delete user` - Delete another user's account permanently by inputting their username.
* `delete self` - Delete the current user account permanently.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
* `contact details` - access the contact screen, which new commands relevant to contact. see Contact Screen below.
* `sign out` - Exits the admin post-login screen and saves new data to the Json Database, this will bring you back to the login page.
* `create patient`- Create a new patient account and store it in the Json Database.

### Patient Loaded Doctor Commands:
_Note: These commands can only be accessed by loading a patient through the `load patient` doctor command. to all other account types, these commands are unreachable._
* `view all prescriptions` - Shows a list of all past and present prescription headers related to the loaded patient.
* `delete prescription` - deletes a prescription related to the loaded patient.
* `help` - Shows a list of all available commands, available at any point in the program.
* `view active prescriptions` - Shows a list of all presently active prescription headers related to the loaded patient.
* `view all prescriptions detailed` - Shows a list of all past and present prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view reports` - Show all reports related to the loaded patient.
* `unload patient` - Unloads the patient, returning the doctor to the basic doctor command screen. Similar to the `back` command.
* `create report` - Creates a new report related loaded patient based on inputted data.
* `delete report` - Deletes a report related to the loaded patient.
* `create prescription` - creates a new prescription related to the loaded patient based on inputted data.
* `exit` - Terminates the program.

### Patient Loaded Secretary Commands:
_Note: These commands can only be accessed by loading a patient through the `load patient` secretary command. to all other account types, these commands are unreachable._
* `view all prescriptions` - Shows a list of all past and present prescription headers related to the loaded patient.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `change patient password` - Changes the password of a patient based on inputted data.
* `unload patient` - Unloads the patient, returning the secretary to the basic secretary command screen. Similar to the `back` command.
* `view all prescriptions detailed` - Shows a list of all past and present prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `back`
* `view appointments` - Shows the appointments related to the loaded patient.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
* `view active prescriptions` - Shows a list of all presently active prescription headers related to the loaded patient.
* `cancel appointment` - Cancels an appointment related to the loaded patient. Frees up a doctor's availability.
* `book appointment` - Books an appointment related to the loaded patient and related doctor and stores it in the Appointment Json Database.
* `reschedule appointment` - change the date and/or time of an appointment related to the loaded patient and update it in the Appointment Json Database.


### Contact Commands:
_Note: These commands can only be accessed by running the `contact details` command. These commands are available to all account types._
* `change emergency contact name` - Changes the stored emergency contact data related to the user based on inputted data.
* `view contact information` - Shows the name, email, phone number, address, date of birth, and the emergency contact name, email, phone number, and relationship.
* `back` - Returns to the previous screen in which the `contact details` command was called.
* `change email` - Changes the stored email address related to the user based on inputted data.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
* `change name` - Changes the stored name related to the user based on inputted data.
* `change emergency contact phone number` - Changes the stored emergency contact phone number related to the user based on inputted data.
* `change emergency contact relationship` - Changes the stored emergency contact relationship related to the user based on inputted data.
* `change phone number` - Changes the stored phone number related to the user based on inputted data.
* `change address` - Changes the stored address related to the user based on inputted data.
* `change birthday` - Changes the stored birthday related to the user based on inputted data.
* `change emergency contact email` - Changes the stored emergency contact email related to the user based on inputted data.
