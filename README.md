# CSC207 Project Phase 2

Table of Contents:

<!-- toc -->

- [The Required Dependencies & Note on Including .idea](#the-required-dependencies--note-on-including-idea)
- [UML Diagram](#uml-diagram)
- [Usage of Design Patterns](#usage-of-design-patterns)
- [Features added in phase 2](#features-added-in-phase-2)
- [The 7 Principles of Universal Design](#the-7-principles-of-universal-design)
- [Usage of Git and GitHub Features](#usage-of-git-and-github-features)
- [How to Run The Program](#how-to-run-the-program)
  * [Bootstrapping an Admin](#bootstrapping-an-admin)
- [Exiting The Program](#exiting-the-program)
- [Command List](#command-list)
  * [Pre-Login](#pre-login)
  * [Admin Post-Login](#admin-post-login)
  * [Patient Post-Login](#patient-post-login)
  * [Doctor Post-Login](#doctor-post-login)
  * [Secretary Post-Login](#secretary-post-login)
  * [Doctor Loaded Patient Commands](#doctor-loaded-patient-commands)
  * [Secretary Loaded Patient Commands](#secretary-loaded-patient-commands)
  * [User Management Terminal Commands](#user-management-terminal-commands)
  * [Contact Commands](#contact-commands)
  * [Clinic Commands](#clinic-commands)

<!-- tocstop -->

## The Required Dependencies & Note on Including .idea

First make sure you are using java 11: In order to set up the SDK, click `Setup SDK` and then select `coretto-11`.
(if this option isn't available, you'll need to add the SDK to Intellij, there are some tutorials provided by intellij
you can follow).

All libraries will be automatically installed by intellij when the program first starts up.

The xml metadata for the libraries can be found in: `.idea/libraries`. This is why we chose to include the `.idea`
folder in our repo. Furthermore, in order to have only the necessary files and folders, a `.gitignore` has been added
accordingly inside the `.idea` folder.

## UML Diagram

The uml diagram can be found in the `design/design1.pdf` file in the Project207 directory.

## Usage of Design Patterns

Please read: `design/design2.pdf`.

## Features added in phase 2

This document contains a list of decisions and explanations about how our code has improved since Phase 1.

Please read: `design/design3.pdf`

## The 7 Principles of Universal Design

This document contains a description of how our program does or could follow the 7 principles of universal design.

Please read: `design/design4.pdf`

## Usage of Git and GitHub Features

We effectively and consistently used many git and GitHub features throughout phase 2.

- Created GitHub issue templates to guide us through creating GitHub issues whenever we encounter a bug in the program.
  * The GitHub issue templates can be found in `.github/ISSUE_TEMPLATE/`.
- Added a GitHub action to force the use of the conventional commit specs on our PR titles as part of our continuous
  integration workflow.
  * The GitHub action can be found `.github/workflows/`.
- Secured the `main` branch (using GitHub) from any direct commits and force pushes.
  * This was done by locking the `main` branch using GitHub settings.
- Used branches whenever we wanted to change part of the code without directly editing the main branch. Each new
  feature was implemented in its own branch.
- Used pull requests and GitHub reviews to review, edit and merge branches into the main branch, and resolve merge
  conflicts.

## How to Run The Program

To start the program, run Main.

Use `help` to see all possible commands at any point of the program. 

### Bootstrapping an Admin

To access admin privileges, use the provided bootstrapped admin account:

username: `admin1`

password: `12345678`

To create another admin account, use the `create admin` command while logged into the `admin1` account.

## Exiting The Program

To properly close the program, use the built-in `exit` command.

## Command List

### Pre-Login

*Note: Only secretaries and admins can create Patient and Doctor accounts, and only an Admin can create secretary accounts; reference the admin section below.*
* `sign in` - Log into a pre-existing account saved in the Json Database.
* `view clinic info` - Shows a list of information relating to specific clinic.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Admin Post-Login
* `manage users` - Loads a new screen which has commands that allow the admin to create/delete users and change their passwords. See **User Management Terminal Commands** below.
* `change clinic info` - Access the Clinic screen, with new commands relevant to the clinic. See **Clinic Commands** below.
* `delete self` - Delete the current user account permanently.
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the contact screen, which new commands relevant to contact. See **Contact Commands** below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Back is an alias for the `sign out` command (ease of access purposes).
* `sign out` - Exits the post-login screen. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Patient Post-Login
* `view appointments` - Shows a list of all appointments that the patient has with details such as date and time.
* `view all prescriptions detailed` - Shows a list of all expired and active prescriptions related to the patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions` - Shows a list of the headers of all active prescriptions belonging to a user.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the patient, including the prescription header, body, date noted, and expiration date.
* `view all prescriptions` - Shows a list of all expired and active prescriptions' headers related to a user.
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the contact commands screen, with new commands relevant to contact. See **Contact Commands** below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Back is an alias for the `sign out` command (ease of access purposes).
* `sign out` - Exits the post-login screen. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Doctor Post-Login
* `load patient` - Loads a patient, allowing the doctor to access commands related to the patient loaded; See **Doctor Loaded Patient** Commands below.
* `view appointments` - Shows a list of all appointments that the doctor has with details such as date, duration and time.
* `show schedule` - Prompts the doctor to enter a date and then shows all their appointments for that date.  
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the contact commands screen, with new commands relevant to contact. see **Contact Commands** below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Back is an alias for the `sign out` command (ease of access purposes).
* `sign out` - Exits the post-login screen. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Secretary Post-Login
* `create patient` - Create a patient and store it in the Json Database.
* `load patient` - Loads a patient, allowing the secretary to access commands related to the patient loaded; See **Secretary Loaded Patient Commands** below.
* `delete patient` - Deletes a patient user account from the Json Database based on inputted username.
* `change password` - Change this user's login password.
* `get logs` - Shows a list of logs that represents the current user's login dates.
* `contact details` - Access the Contact Commands screen, with new commands related to contact. See **Contact Commands** below.
* `view clinic info` - Shows a list of information relating to the clinic.
* `back` - Back is an alias for the `sign out` command (ease of access purposes).
* `sign out` - Exits the post-login screen. This will bring you back to the login page.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Doctor Loaded Patient Commands
_Note: These commands can only be accessed by loading a patient through the `load patient` doctor command. To all other account types, these commands are unreachable._
* `view appointments` - Shows a list of all appointments that the loaded patient has with details such as date, duration and time.
* `view reports` - Shows a list of all the reports of the loaded patient.
* `create report` - Creates a new report related to the loaded patient based on inputted data.
* `delete report` - Deletes a report related to the loaded patient.
* `view all prescriptions detailed` - Shows a list of all expired and active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions` - Shows a list of the headers of all active prescriptions belonging to a user.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view all prescriptions` - Shows a list of all expired and active prescriptions' headers related to a user.
* `create prescription` - Creates a new prescription related to the loaded patient based on inputted data.
* `delete prescription` - Deletes a prescription related to the loaded patient.
* `unload patient` - Unloads the patient, returning the doctor to the doctor post-login screen.
* `back` - Back is an alias for the `unload patient` command (ease of access purposes).
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Secretary Loaded Patient Commands
_Note: These commands can only be accessed by loading a patient through the `load patient` secretary command. To all other account types, these commands are unreachable._
* `change patient password` - Changes the password of a patient based on inputted data.
* `view appointments` - Shows a list of all appointments that the loaded patient has with details such as date, duration and time.
* `reschedule appointment` - Reschedules an appointment related to the loaded patient based on inputted data.
* `book appointment` - Books an appointment for the loaded patient based on inputted data.
* `cancel appointment` - Cancels an appointment for the loaded patient based on inputted data.
* `view all prescriptions detailed` - Shows a list of all expired and active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view active prescriptions` - Shows a list of the headers of all active prescriptions belonging to a user.
* `view active prescriptions detailed` - Shows a list of presently active prescriptions related to the loaded patient, including the prescription header, body, date noted, and expiration date.
* `view all prescriptions` - Shows a list of all expired and active prescriptions' headers related to a user.
* `unload patient` - Unloads the patient, returning the secretary to the secretary post-login screen.
* `back` - Back is an alias for the `unload patient` command (ease of access purposes).
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### User Management Terminal Commands
_Note: These commands can only be accessed by an admin through the `manage users` admin command. To all other account types, these commands are unreachable._ 
* `create admin` - Create a new admin account and store it in the JsonDatabase.
* `create secretary` - Create a new secretary account and store it in the JsonDatabase.
* `create doctor` - Create a new doctor account and store it in the JsonDatabase.
* `create patient`- Create a new patient account and store it in the Json Database.
* `delete user` - Delete another user's account permanently by inputting their username.
* `change user password` - Change the password of another user's account by inputting the user's unique username and new password.
* `back` - Returns the admin to the basic admin command screen.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Contact Commands
_Note: These commands can only be accessed by running the `contact details` command. These commands are available to all account types._
* `view contact information` - Shows the name, email, phone number, address, date of birth, and the emergency contact name, email, phone number, and relationship.
* `change name` - Changes the stored name related to the user based on inputted data.
* `change email` - Changes the stored email address related to the user based on inputted data.
* `change phone number` - Changes the stored phone number related to the user based on inputted data.
* `change address` - Changes the stored address related to the user based on inputted data.
* `change birthday` - Changes the stored birthday related to the user based on inputted data.
* `change emergency contact name` - Changes the stored emergency contact name related to the user based on inputted data.
* `change emergency contact email` - Changes the stored emergency contact email related to the user based on inputted data.
* `change emergency contact phone number` - Changes the stored emergency contact phone number related to the user based on inputted data.
* `change emergency contact relationship` - Changes the stored emergency contact relationship related to the user based on inputted data.
* `back` - Returns to the previous screen in which the `contact details` command was called.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.

### Clinic Commands
_Note: These commands can only be accessed by running the `change clinic info` command. These commands are only available to admin account types._
* `change clinic name` - Changes the stored name related to the clinic based on inputted data.
* `change clinic email` - Changes the stored email address related to the clinic based on inputted data.
* `change clinic phone number` - Changes the stored phone number related to the clinic based on inputted data.
* `change clinic address` - Changes the stored address related to the clinic based on inputted data.
* `change clinic hours` - Changes the stored clinic hours on a given day based on inputted data.
* `remove clinic hours` - Removes the stored clinic hours on a given day based on inputted data. 
* `back` - Returns to the previous screen in which the `contact details` command was called.
* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Terminates the program.
