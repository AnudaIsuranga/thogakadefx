package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Customer;
import validation.DataValidation;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCustomerFormController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearch;

    List<Customer> customerList;
    private DataValidation dataValidation;

    private int index;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get Connection from DB
        customerList = DBConnection.getInstance().getConnection();

        //Initialize dataValidation object
        dataValidation = new DataValidation();

        loadTable();

        try{
            // Check if tblCustomers and it's selection model are not null
            if (tblCustomers != null && tblCustomers.getSelectionModel() != null) {
                // Listener to get user selected object
                tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, customer, newValue) -> {
                    if (newValue != null) {
                        int customerIndex = DBConnection.getInstance().getConnection().indexOf(newValue);
                        if (customerIndex != -1) {
                            index = customerIndex;
                            enableItems();
                            setTextToValues(newValue);
                        }
                    }
                });
            }
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error","Error: "+e , "img/error-48.png");
        }

    }

    //Reload Button Action
    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
        clearField();
    }

    //Delete Button Action
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        DBConnection.getInstance().getConnection().remove(index);
        showAlert(Alert.AlertType.INFORMATION,
                "Thogakade System",
                "Customer record deleted successfully!",
                "img/success-48.png");
        loadTable();
        clearField();
    }

    //Update Button Action
    @FXML
    void btnUpdateOnAction(ActionEvent event){
        //get Data for validations
        String id = txtCustomerID.getText().toUpperCase();
        String title = cmbTitle.getValue();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        LocalDate dob = txtDateOfBirth.getValue();

        Customer checkNullObj = new Customer(id,title,name,address,contact,dob);

        if(!dataValidation.isNull(checkNullObj)){
            try{
                //Validate Customer ID, Birthday & Contact
                if (!dataValidation.validateDOB(dob.toString()) && !dataValidation.validateContact(contact)) {
                    customerList.set(index,new Customer(
                            id,
                            cmbTitle.getValue(),
                            txtName.getText(),
                            txtAddress.getText(),
                            txtContact.getText(),
                            txtDateOfBirth.getValue()
                    ));
                    showAlert(Alert.AlertType.INFORMATION,
                            "Thogakade System",
                            "Customer Updated successfully!",
                            "img/success-48.png");
                    clearField();
                    loadTable();
                } else {
                    showAlert(Alert.AlertType.WARNING,
                            "Warning",
                            "Warning: \nInvalid inputs are Occurred. \n\n" +
                                    "Check following data : \n\t[1] Duplicate Customer ID\n\t[2] Invalid Contact (07xxxxxxxx) \n\t[3]Invalid Birthday format" +
                                    "\n\n\tPlease input valid data!",
                            "img/warning-48.png");
                }
            }catch (ParseException e){
                showAlert(Alert.AlertType.ERROR,
                        "Error","Invalid date format. Please enter a valid date of birth.", "img/error-48.png");
            }catch (Exception e){
                showAlert(Alert.AlertType.ERROR,"Error","An unexpected error occurred: \n"+e.getMessage(), "img/error-48.png");
            }
        }else {
            showAlert(Alert.AlertType.WARNING,"Warning","Please fill all the fields!", "img/warning-48.png");
        }
    }

    //Search Button Action
    @FXML
    void btnSearchOnAction(ActionEvent event) {
        searchCustomerAction();
    }

    //Search textField Action
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        searchCustomerAction();
    }

    //Search Customer
    private void searchCustomerAction(){
        int indexOfCustomer = dataValidation.searchCustomer(customerList,txtSearch.getText());
        if (indexOfCustomer >= 0){
            index = indexOfCustomer;
            setTextToValues(customerList.get(indexOfCustomer));
            enableItems();
        }else {
            showAlert(Alert.AlertType.INFORMATION,
                    "Information",
                    "User Not Found!",
                    "img/information-48.png");
        }
    }

    //Load data to table
    private void loadTable(){
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        ObservableList<Customer> observableList = FXCollections.observableArrayList();

        observableList.addAll(customerList);

        tblCustomers.setItems(observableList);

    }

    //Set data to textFields
    private void setTextToValues(Customer newValue){
        cmbTitle.setItems(DBConnection.getInstance().getTitles());

        txtCustomerID.setText(newValue.getCustomerID());
        cmbTitle.setValue(newValue.getTitle());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtContact.setText(newValue.getContact());
        txtDateOfBirth.setValue(newValue.getDateOfBirth());
    }

    //Clear all the textFields to null
    private void clearField() {
        txtCustomerID.setText("");
        cmbTitle.setValue(null);
        cmbTitle.setItems(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtContact.setText(null);
        txtDateOfBirth.setValue(null);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setEditable(false);
        txtAddress.setEditable(false);
        txtContact.setEditable(false);
        txtDateOfBirth.setEditable(false);

        txtSearch.setText("");
    }

    //Enable Items
    private void enableItems(){
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        txtName.setEditable(true);
        txtAddress.setEditable(true);
        txtContact.setEditable(true);
        txtDateOfBirth.setEditable(true);
    }
    //Custom Alert
    private void showAlert(Alert.AlertType alertType, String title, String content, String path) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);

        //Set Alert Icon
        Image customIcon = new Image(path);
        ImageView imageView = new ImageView(customIcon);
        alert.getDialogPane().setGraphic(imageView);

        alert.showAndWait();
    }
}
