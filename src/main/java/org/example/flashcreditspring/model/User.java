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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true)
    private Borrower borrower;

    @Column(nullable = false)
    private String role; // "USER" or "ADMIN"


    public boolean verifyPassword(String sha256HashedPasswordFromFrontend) {
        return BCrypt.checkpw(sha256HashedPasswordFromFrontend, this.password);
    }

    public void removeBasiqId() {
        //if remove BasiqIdThen it will remove all of the borrower detail
        this.basiqId = null;
        if (this.borrower != null) {
            this.borrower = null; // This will trigger orphan removal
        }
    }


}
