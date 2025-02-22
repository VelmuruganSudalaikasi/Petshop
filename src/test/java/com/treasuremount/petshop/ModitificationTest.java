package com.treasuremount.petshop;

import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.ExceptionHandler.DuplicateUserException;
import com.treasuremount.petshop.Repository.UserRepo;
import com.treasuremount.petshop.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;


@SpringBootTest
public class ModitificationTest {



    @Autowired
    UserService userService;

    @Mock
    UserRepo userRepo;





    @Test
    void testUserAddException(){
        User dupmmy_user=null;
        User user=new User();
        user.setId(9L);
        user.setFirstName("dummmy");
        user.setLastName("dummmyLast");
        user.setEmailId("velmurugan@gmail.com");
        user.setMobileNumber("1234567890");
     when(userRepo.findUserByPhoneNumber(user.getMobileNumber(),"velmurugan@gmail.com")).thenReturn(Optional.of(user)

     );
     try{
         userService.create(user);
     } catch (RuntimeException e) {

         Assertions.assertEquals("Mobile Number is Already Registered",e.getMessage());
     }


    }

}
