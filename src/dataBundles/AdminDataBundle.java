package dataBundles;

import entities.Admin;

import java.util.ArrayList;

public class AdminDataBundle extends DataBundle{
    private final Admin admin;

    public AdminDataBundle(Integer id, Admin admin) {
        super(id);
        this.admin = admin;
    }

    public String getUsername(){
        return admin.getUsername();
    }

    public int getContact(){
        return admin.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return admin.getLogs();
    }


}
