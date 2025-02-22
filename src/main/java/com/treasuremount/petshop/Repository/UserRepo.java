package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM `user` u WHERE " +
            "( u.mobile_number = :phoneNumber OR u.email_id= :EmailId) " +
            "AND u.password = :password", nativeQuery = true)
    Optional<User> findByEmailIdAndPasswordForLogin(@Param("phoneNumber") String phoneNumber,@Param("EmailId") String emailId , @Param("password") String password);

    @Query(value = "SELECT \n"
            +"     (u.active_status) AS is_active\n"
            + "FROM \n"
            + "    \"user\" u\n"
            + "WHERE \n"
            + "    u.email_id = :emailId \n"
            + "    AND u.password = :password", nativeQuery = true)
    Boolean isValidSuperUser(@Param("emailId") String emailId,@Param("password") String  password);

    @Query(value = "SELECT * FROM user u WHERE u.mobile_number = :phoneNumber OR u.email_id= :EmailId ", nativeQuery = true)
    Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String mobileNumber,@Param("EmailId")String emailId);

    @Query(value = "SELECT u.id AS userId,\n"
            + "       u.email_id AS emailId,\n"
            + "       u.role_id AS roleId,\n"
            + "       u.active_status AS activeStatus\n"
            + "FROM user u\n"
            + "WHERE u.mobile_number = :phoneNumber", nativeQuery = true)
    Map<String, Object> findByMobileNumberAndPasswordForLogin(@Param("phoneNumber") String mobileNumber);



}
