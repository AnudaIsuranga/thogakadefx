package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static DBConnection instance;

    private final List<Customer> customerList;

    //Title list
    @Getter
    private ObservableList<String> titles;

    private DBConnection(){
        customerList = new ArrayList<>();
        titles = FXCollections.observableArrayList();
        titles.add("Mr.");
        titles.add("Miss.");
    }

    public static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    //Get customer array-list
    public List<Customer> getConnection(){
        return customerList;
    }

}
