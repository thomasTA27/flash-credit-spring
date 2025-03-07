package org.example.flashcreditspring.model;


import lombok.Data;

@Data
public class BorrowerDTO {

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String address;
    private String apt;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String mobileNumber;
    private String email;

}
