package com.treasuremount.petshop.Login;

import com.treasuremount.petshop.Entity.User;

public interface LoginService {

    User login(LoginDTO loginDTO);

//	LoginResponse login(String emailId, String password) throws UserNotFoundException;

}
