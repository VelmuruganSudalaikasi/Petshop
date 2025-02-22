package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User d);

    User findById(Long id);

    List<User> getAll();

    User getOneUserById(Long id);

    User update(User d, Long id);

    Boolean delete(Long id);

     User findByMobileOREmail(String number,String emailId);

   List<User> getAllByUserIds(List<Long> userId);
     String getUserEmailById(Long userId);

   /*  boolean editUser(User v);*/

//    public User getUserByVendorId(Long vendorId);
}
