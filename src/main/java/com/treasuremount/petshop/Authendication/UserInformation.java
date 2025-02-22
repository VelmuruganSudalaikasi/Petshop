/*
package com.treasuremount.petshop.Authendication;



import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserInformation implements UserDetailsService {

    private final UserRepo repo;

    public UserInformation(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo=repo.findUserByPhoneNumber(username).orElseThrow(
                ()->new UsernameNotFoundException("EmailId not found"));

        return new UserInfoDetails(userInfo.getMobileNumber(),userInfo.getPassword());
    }
}
*/
