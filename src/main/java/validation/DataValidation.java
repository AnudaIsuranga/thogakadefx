package validation;

import model.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DataValidation {

    public boolean isNull(Customer customer){
        return customer.getCustomerID() == null ||
                customer.getTitle() == null ||
                customer.getName() == null ||
                customer.getAddress() == null ||
                customer.getContact() == null ||
                customer.getDateOfBirth() == null;
    }

    public boolean isDuplicateID(List<Customer> customerList, String id){
        //Check Duplicate Customer IDs
        for (Customer customer : customerList){
            if(customer.getCustomerID().equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean validateDOB(String dob) throws ParseException{
        //Set validation format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        simpleDateFormat.setLenient(false);

        //Throws an Exception
        Date dateOfBirth = simpleDateFormat.parse(dob);

        //Check future dates
        return dateOfBirth.after(new Date());

    }

    public boolean validateContact(String contact){
        return contact.length() !=10 && contact.charAt(0) !=0 ;
    }

    public int searchCustomer(List<Customer> customerList, String value){
        value = value.toUpperCase();

        for (Customer customer : customerList){
            if (customer.getCustomerID().equals(value) ||
                    customer.getContact().equals(value)){
                return customerList.indexOf(customer);
            }
        }
        return -1;
    }
}
