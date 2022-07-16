package dataBundles;

import entities.Admin;

import java.util.ArrayList;

public class AdminData {
    private final Admin admin;

    public AdminData(Admin admin) {
        this.admin = admin;
    }

    public String getUsername(){
        return admin.getUsername();
    }

    public int getContact(){
        return admin.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return admin.getLogIds();
    }

    public int getId(){
        return admin.getId();
    }


}
