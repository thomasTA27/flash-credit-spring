package org.example.flashcreditspring.repository;

import org.example.flashcreditspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

        Optional<User> findByPhoneNum(String phoneNum);

//    @Query("SELECT u FROM User u WHERE u.phone_num = :phoneNum")
//    Optional<User> findByPhoneNum(@Param("phoneNum") String phoneNum);

}
