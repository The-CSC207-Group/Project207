package dataBundles;

import entities.Secretary;

import java.util.ArrayList;

public class SecretaryDataBundle extends DataBundle{
    private final Secretary secretary;

    public SecretaryDataBundle(Integer id, Secretary secretary) {
        super(id);
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
