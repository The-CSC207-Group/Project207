package dataBundles;

import entities.Secretary;

import java.util.ArrayList;

public class SecretaryDataBundle {
    private final Secretary secretary;

    public SecretaryDataBundle(Secretary secretary) {
        this.secretary = secretary;
    }

    public String getUsername(){
        return secretary.getUsername();
    }

    public int getContact(){
        return secretary.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return secretary.getLogs();
    }
}
