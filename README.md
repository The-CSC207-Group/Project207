# CSC207 Project Phase 2

**NOTE TO TA:** To grade our phase 1, please switch to the phase 1 branch:
<https://github.com/The-CSC207-Group/Project207/tree/phase-1>.

_**PLEASE NOTE: Anything related to appointments, availability, absence, timeblocks and reports have been moved to phase 2 of the project, and should not be graded. This is true for any commented code and attributes as well.**_

## How to Install The Required Dependencies

First make sure you are using java 11: In order to set up the SDK, click `Setup SDK` and then select `coretto-11` (if this
option isn't available, you'll need to add the SDK to Intellij, there are some tutorials provided by intellij you can
follow).

All libraries will be automatically installed by intellij when the program first starts up.

The xml metadata for the libraries can be found in: `.idea/libraries`

## UML Diagram

The uml diagram can be found in the design.pdf file in the Project207 directory.

## How to Run The Program

To start the program, Run Main.

Use `help` to see all possible commands at any point of the program. 

## Exiting The Program

To properly close the program, use the built-in `exit` command that lives in the user login page in order to save data
to the UserJsonDatabase. If you already logged into a user, use the `sign out` command and then use the `exit` command.

## Design Patterns


### State Pattern

The state of the program is handled via the state pattern. For each possible state the program can be in there exists a
unique controller. Then, when the state of the program changes, we change the current controller. The current controller
is stored in the Context and every controller has a link to the context in order to change the current controller.
The while loop for the program exists completely in the context and the job of each controller is just to proccess a 
single command and if needed change the current controller.

For example the sign in controller processes the command sign in. If it's an unsuccessful sign in it does nothing
leave the current controller the same (in this case the sign in controller) if it succeeds it decided which controller
to swap to. In this case one of the User Controllers.

### Command Pattern

Instead of processing input via a long switch statement we instead do it by storing functions in a hashmap where the
command input string is the key that is linked to the function we want to run. This provided us with numerous benefits. It allowed easy 
inheritance of commands just by adding maps to each other. As well as providing easy help functionality that never got
decoupled from the state of the program.

### Iterator/Stream Pattern and Higher Order Functions

We added the ability to turn every database into a stream. allowing us to process them using higher order functions.
this simplified a lot of complex code into a more readable alternative, and encouraged the reuse of logic.
This pattern is very similar to the iterator pattern and in more modern languages is often implemented together with the
iterator pattern (in rust for example streams are actually called iterators). And in java any stream can be turned into
an iterator.
The sole difference is that the stream pattern encourages the use of higher order functions such as map and filter and
getByCondition instead of manual for loops.

an illustration of the difference can be seen in the following code snippets 

```
Item getItemByName(String name){
    return database.getBycondition(x -> x.getname.equals(name));
}
List<Appointments> getAppointmentsbyDoctorAndPatient(String patientName, String doctorName){
    return appointmentdatabase.stream()
                .filter(x -> x.patientname.eqauls(patientname))
                .filter(x -> x.doctorname.equals(doctorname))

Item getItemByname(String name) {
    for (x : database.Iterator){
        if (x.getname.equals(name)){
            return x
            }
    }
List<Appointments> getAppointmentsbyDoctorAndPateint(String patientName, String doctorName){
    List<Appointments> result = new ArrayList();
    for (x : apointmentdatabase.iterator){
        if (x.patientname.equals(patientname) | x.doctorname.equals(doctorname){
            result.append(x)
            }
    return result;
```

### Admin:

To access admin privileges, use the provided admin account:

username: `admin1`

password: `12345678`

To create another admin account, use the `create admin` command while logged into the `root` account.

## Command List:


### Pre-Login:

*Note: Only a secretaries and admins can create Patient and Doctor accounts, and only an Admin can create secretary accounts; reference the admin section below.*
* `sign in` - Log into a pre-existing account saved in the Json Database.
* `view clinic info` - Shows a list of information relating to specific clinic.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Admin Post-Login:
* `manage users` - Loads a new screen which has commands that allow the admin to create/delete users and change their passwords
* `change clinic info` - access the Clinic screen, with new commands relevant to the clinic. see Clinic Screen below.
* `delete self` - Delete the current user account permanently.
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - access the contact screen, which new commands relevant to contact. see Contact Screen below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page. Back is an alias for sign out (ease of access purposes).
* `sign out` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Patient Post-Login:
* `view appointments` - Shows a list of all appointments that the patient has with details such as date and time.
* `view all prescriptions detailed` - Shows a list of all past and present prescriptions related to the patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions` - Shows a list of the headers of all active prescriptions belonging to a user.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the patient, including the prescription header, body, date noted, and expiration date.
* `view all prescriptions` - Shows a list of all past and present prescriptions' headers related to a user.
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the contact commands screen, with new commands relevant to contact. see Contact Commands below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page. Back is an alias for sign out (ease of access purposes).
* `sign out` - Exits the post-login screen and saves and new data to the Json Database, this will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Doctor Post-Login:
* `load patient` - Loads a patient, allowing the user to access commands related to the patient loaded; See **patient Loaded Doctor** Commands below.
* `view appointments` - Shows a list of all appointments that the doctor has with details such as date, duration and time.
* `show schedule` - Prompts the doctor to enter a date and then shows all the appointments for that date.  
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the contact commands screen, with new commands relevant to contact. see Contact Commands below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page. Back is an alias for sign out (ease of access purposes). 
* `sign out` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Secretary Post-Login:
* `create patient` - Create a patient and store it in the Json Database.
* `load patient` - Loads a patient, allowing the user to access commands related to the patient loaded; See **patient Loaded Secretary Commands** below.
* `delete patient` - Deletes a patient user account from the Json Database based on inputted username.
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the contact commands screen, with new commands relevant to contact. see Contact Commands below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page. Back is an alias for sign out (ease of access purposes).
* `sign out` - Exits the post-login screen and saves new data to the Json Database. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.


### Patient Loaded Doctor Commands:
_Note: These commands can only be accessed by loading a patient through the `load patient` doctor command. To all other account types, these commands are unreachable._
* `unload patient` - Unloads the patient, returning the doctor to the basic doctor command screen. 
* `back` - Unloads the patient, returning the doctor to the basic doctor command screen. Similar to the `unload patient` command.
* `view appointments` - Shows a list of all appointments that the loaded patient has with details such as date, duration and time.
* `view reports` -Shows a list of all the reports of the loaded patient.
* `create report` - Creates a new report related to the loaded patient based on inputted data.
* `delete report` - Deletes a report related to the loaded patient.
* `view all prescriptions detailed` - Shows a list of all past and present prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions` - Shows a list of the headers of all active prescriptions belonging to a user.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view all prescriptions` - Shows a list of all past and present prescriptions' headers related to a user.
* `create prescription` - Creates a new prescription related to the loaded patient based on inputted data.
* `delete prescription` - Deletes a prescription related to the loaded patient.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Patient Loaded Secretary Commands:
_Note: These commands can only be accessed by loading a patient through the `load patient` secretary command. To all other account types, these commands are unreachable._
* `change patient password` - Changes the password of a patient based on inputted data.
* `view appointments` - Shows a list of all appointments that the loaded patient has with details such as date, duration and time.
* `reschedule appointment` - Reschedules an appointment related to the loaded patient based on inputted data.
* `book appointment` - Books an appointment for the loaded patient based on inputted data.
* `cancel appointment` - Cancels an appointment for the loaded patient based on inputted data.
* `unload patient` - Unloads the patient, returning the secretary to the basic secretary command screen.
* `back` - Unloads the patient, returning the secretary to the basic secretary command screen. Similar to the `unload patient` command.
* `view all prescriptions detailed` - Shows a list of all past and present prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions` - Shows a list of the headers of all active prescriptions belonging to a user.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view all prescriptions` - Shows a list of all past and present prescriptions' headers related to a user.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### User Management Terminal 
_Note: These commands can only be accessed by an admin through the `manage users` secretary command. To all other account types, these commands are unreachable._
* `back` - Returns the admin to the basic admin command screen. 
* `create admin` - Create a new admin account and store it in the JsonDatabase.
* `create secretary` - Create a new secretary account and store it in the JsonDatabase.
* `create doctor` - Create a new doctor account and store it in the JsonDatabase.
* `create patient`- Create a new patient account and store it in the Json Database.
* `delete user` - Delete another user's account permanently by inputting their username.
* `change user password` - Change the password of another user's account by inputting the user's unique username and new password.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Contact Commands:
_Note: These commands can only be accessed by running the `contact details` command. These commands are available to all account types._
* `view contact information` - Shows the name, email, phone number, address, date of birth, and the emergency contact name, email, phone number, and relationship.
* `change name` - Changes the stored name related to the user based on inputted data.
* `change email` - Changes the stored email address related to the user based on inputted data.
* `change phone number` - Changes the stored phone number related to the user based on inputted data.
* `change address` - Changes the stored address related to the user based on inputted data.
* `change birthday` - Changes the stored birthday related to the user based on inputted data.
* `change emergency contact name` - Changes the stored emergency contact data related to the user based on inputted data.
* `change emergency contact email` - Changes the stored emergency contact email related to the user based on inputted data.
* `change emergency contact phone number` - Changes the stored emergency contact phone number related to the user based on inputted data.
* `change emergency contact relationship` - Changes the stored emergency contact relationship related to the user based on inputted data.
* `back` - Returns to the previous screen in which the `contact details` command was called.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Clinic Commands:
_Note: These commands can only be accessed by running the `change clinic info` command. These commands are only available to admin account types._
* `change clinic name` - Changes the stored name related to the clinic based on inputted data.
* `change clinic email` - Changes the stored email address related to the clinic based on inputted data.
* `change clinic phone number` - Changes the stored phone number related to the clinic based on inputted data.
* `change clinic address` - Changes the stored address related to the clinic based on inputted data.
* `change clinic hours` - Changes the stored clinic hours related to the clinic based on inputted data.
* `remove clinic hours` - Removes the stored clinic hours related to the clinic based on inputted data. 
* `back` - Returns to the previous screen in which the `contact details` command was called.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

