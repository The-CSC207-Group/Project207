package dataBundles;

import entities.Admin;

import java.util.ArrayList;

public class AdminData extends UserDataBundle<Admin>{
    private final Admin admin;

    public AdminData(Admin admin) {
        super(admin);
        this.admin = admin;
    }

    public String getUsername() {
        return admin.getUsername();
    }

    public Integer getContact() {
        return admin.getContactInfoId();
    }

    public Integer getId() {
        return admin.getId();
    }


}
