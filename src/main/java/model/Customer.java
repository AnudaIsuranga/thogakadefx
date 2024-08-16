package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    String customerID;
    String title;
    String name;
    String address;
    String contact;
    LocalDate dateOfBirth;
}
