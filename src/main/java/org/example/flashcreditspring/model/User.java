package org.example.flashcreditspring.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;


@Entity
@Table(name = "user")
@Getter
@Setter

public class User {

    @Id
    @Column(name = "phone_num", unique = true, nullable = false)
    private String phoneNum;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String salt;

    private String basiqId;
    //optional field if they have want to borrow money

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Borrower borrower;

    public boolean verifyPassword(String sha256HashedPasswordFromFrontend) {
        return BCrypt.checkpw(sha256HashedPasswordFromFrontend, this.password);
    }


}
