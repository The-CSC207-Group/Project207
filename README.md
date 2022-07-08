# CSC207 Project Phase 1

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

*Note: An admin account cannot be created from the register command, reference the admin section below.*

* `help` - Shows a list of all available commands, available at any point in the program.
* `exit` - Quits the program.
* `sign in` - Log into a pre-existing account saved in the Json Database.
* `register` - Create an account to be saved in the Json Database. The only account type that can be created  in pre-login is a normal user account; see admin for admin account creation. 

### User Post-Login:

* `delete account` - Deletes account and account data from Json Database, this will bring you back to the login page.
* `change password` - Change the login password and update it in the Json Database.
* `history` - Shows all previous login times.
* `sign out` - Exits the post-login screen and saves and new data to the Json Database, this will bring you back to the login page.

### Admin:

To access admin privileges, use the provided admin account:

username: `root`

password: `root`

To create another admin account, use the `create admin` command while logged into the `root` account.

### Admin Post-Login:

* `delete other user` - Delete another user's account permanently.
* `ban user` - Restrict an account and prevent them from logging in. This only works for other users not including current user. 
* `create user` - Make a new account with user privileges.
* `unban user` - Un-restrict an account, removing ban restrictions. This only works for other users not including current user.
* `delete account` - Remove YOUR account from the Json database permanently, this will bring you back to the login page.
* `change password` - Change the login password and update it in the Json Database
* `promote user` - Give admin privileges to to a non-admin user.
* `create admin` - Create a new admin account.
* `history` - Show all previous login times from current user.
* `sign out` - Exits the admin post-login screen and saves new data to the Json Database, this will bring you back to the login page.
