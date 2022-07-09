package database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Log;

import java.util.HashSet;

public class LogJsonDatabase implements DataMapperGateway<Log>{
    public LogJsonDatabase() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(new Log(12, "Hello, World!")));
    }

    public static void main(String[] args) {
        new LogJsonDatabase();
    }

    @Override
    public HashSet<Integer> getAllIds() {
        return null;
    }

    @Override
    public Log get(Integer id) {
        return null;
    }

    @Override
    public boolean add(Log item) {
        return false;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    @Override
    public void save() {

    }
}
