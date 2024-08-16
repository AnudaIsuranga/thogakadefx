//Thogakade POS CRUD - JavaFx Task
//Author : @Iroshan-Rathnayake (RegNo: OR24110322 )
//PRF - iCD 110

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {


    @FXML
    private JFXButton btnAddNewCustomer;

    @FXML
    private JFXButton btnManageCustomer;

    @FXML
    public JFXButton btnViewCustomer;


    //Icon Logo Path
    String logoPath = "img/logo.png";

    //Custom Error Alert
    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error: \n IOException Error!");

    //Add New Customer (/add_customer_form)
    @FXML
    void btnAddNewCustomerOnAction(ActionEvent event) {
        try{
            Stage stage =new Stage();
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/add_customer_form.fxml"))
            ));
            stage.setTitle("Add New Customer");
            stage.getIcons().add(new Image(logoPath));
            stage.show();
        }catch (IOException e){
            errorAlert.show();
            throw new RuntimeException(e);
        }
    }

    //Manage Customer (/manage_customer_form)
    @FXML
    void btnManageCustomerOnAction(ActionEvent event) {
        try{
            Stage stage =new Stage();
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/manage_customer_form.fxml"))
            ));
            stage.setTitle("Manage Customer");
            stage.getIcons().add(new Image(logoPath));
            stage.show();
        }catch (IOException e){
            errorAlert.show();
            throw new RuntimeException(e);
        }
    }

    //View Customer (/view_customer_form)
    @FXML
    void btnViewCustomerOnAction(ActionEvent event) {
        try{
            Stage stage =new Stage();
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/view_customer_form.fxml"))
            ));
            stage.setTitle("View Customer");
            stage.getIcons().add(new Image(logoPath));
            stage.show();
        }catch (IOException e){
            errorAlert.show();
            throw new RuntimeException(e);
        }
    }


}
