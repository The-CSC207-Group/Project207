//package controllers;
//
//import database.DataMapperGateway;
//import entities.User;
//import useCases.AdminManager;
//import useCases.UserManager;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//
//public class AdminController extends UserController {
//
//    AdminManager adminManager;
//
//    public AdminController(UserManager nManager) {
//        super(adminManager);
//        this.adminManager = adminManager;
//    }
//
//    @Override
//    public HashMap<String, Command> AllCommands() {
//        HashMap<String, Command> commands = super.AllCommands();
//        commands.put("create admin", new CreateAdminCommand());
//        commands.put("ban user", new BanUserCommand());
//        commands.put("unban user", new UnBanUserCommand());
//        commands.put("delete other user", new deleteUser());
//        commands.put("promote user", new promoteUser());
//        commands.put("create user", new createUser());
//        return commands;
//    }
//
//    class CreateAdminCommand implements Command {
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            List<String> fields = Arrays.asList("admin username", "admin password");
//            HashMap<String, String> responses = presenter.promptPopup(fields);
//            String username = responses.get(fields.get(0));
//            String password = responses.get(fields.get(1));
//
//            if (adminManager.createAdminUser(username, password)){
//                presenter.successMessage("Admin " + username + " has been created");
//            } else {
//                presenter.errorMessage("Failed to create admin");
//            }
//            return true;
//        }
//    }
//
//
//    class BanUserCommand implements Command {
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter username to ban: ");
//            if (adminManager.banUser(username)){
//                presenter.successMessage(username + " has been banned");
//            } else {
//                presenter.errorMessage("Failed to ban user");
//            }
//            return true;
//        }
//    }
//
//    class UnBanUserCommand implements Command {
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter username to unban: ");
//            if (adminManager.unbanUser(username)){
//                presenter.successMessage(username + " has been unbanned");
//            } else {
//                presenter.errorMessage("Failed to unban user");
//            }
//            return true;
//        }
//    }
//
//    class deleteUser implements Command {
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            presenter.warningMessage("This action is irreversible");
//            String username = presenter.promptPopup("Enter username to delete: ");
//            if (adminManager.deleteUser(username)){
//                presenter.successMessage(username + " has been deleted");
//            } else {
//                presenter.errorMessage("Failed to delete user");
//            }
//            return true;
//        }
//    }
//
//    class promoteUser implements Command {
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            String username = presenter.promptPopup("Enter username to promote: ");
//            if (adminManager.promoteUser(username)){
//                presenter.successMessage(username + " has been promoted");
//            } else {
//                presenter.errorMessage("Failed to promote user");
//            }
//            return true;
//        }
//    }
//
//    class createUser implements Command {
//
//        @Override
//        public boolean execute(ArrayList<String> args) {
//            List<String> fields = Arrays.asList("username", "password");
//            HashMap<String, String> responses = presenter.promptPopup(fields);
//            String username = responses.get(fields.get(0));
//            String password = responses.get(fields.get(1));
//
//            if (adminManager.createUser(username, password)){
//                presenter.successMessage("User " + username + " has been created");
//            } else {
//                presenter.errorMessage("Failed to create user: Username already exists");
//            }
//            return true;
//        }
//    }
//}
