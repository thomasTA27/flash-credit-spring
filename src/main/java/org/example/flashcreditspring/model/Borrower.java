package org.example.flashcreditspring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "borrower")
public class Borrower {

    @Id
    @Column(name = "basiq_user_id", unique = true, nullable = false)
    private String basiqUserId;  // PRIMARY KEY


//    @Column(name = "mobile_number", nullable = false)
//    private String mobileNumber;  // Direct field for mobile_number

    @OneToOne
    @JoinColumn(name = "mobile_number_fk", referencedColumnName = "phone_num")
    @JsonIgnore
    private User user;  // Reference to User using the same column


//    @OneToOne
//    @JoinColumn(name = "mobile_number", referencedColumnName = "phone_num")
//    private User user;

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String address;
    private String apt;
    private String city;
    private String state;

    @Column(name = "postal_code")
    private String postalCode; // Make sure this field is populated

//    private String postalCode;
    private String country;
    private String email;

//    private boolean gender;

//    @Column(name = "mobile_number", unique = true, nullable = false)
//    private String mobileNumber; // Foreign key
}
