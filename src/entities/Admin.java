package entities;

public class Admin extends User {

    public Admin(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }
    public Admin(String username, String password){
        super(username, password, new Contact().getId());
    }

}
