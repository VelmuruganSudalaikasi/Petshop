package com.treasuremount.petshop.Login;



import java.util.Optional;

import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepo userRepository;

//	@Autowired
//    private UserDetailsService userDetailsService; // Inject UserDetailsService
//
//    @Override
//    public LoginResponse login(String emailId, String password) throws UserNotFoundException {
//    	String trimmedEmail = emailId.trim();
//        User user = userRepository.findUserByEmailId(emailId)
//            .stream()
//            .findFirst()
//            .orElseThrow(() -> new UserNotFoundException("User not found: "+ trimmedEmail));
//
//        if (isValidUser(user, password) && isAuthorizedUser(user)) {
//            // Load UserDetails for token generation
//            UserDetails userDetails = userDetailsService.loadUserByUsername(emailId);
//
//            // Generate token using UserDetails
////            String token = jwtUtil.generateToken(userDetails);
//
//            Map<String, Object> results = userRepository.findByEmailIdAndPasswordForLogin(emailId, password);
//            return mapToUserResponse(results/**,token**/); // Return the token in the response
//        } else {
//            throw new UserNotFoundException("Invalid credentials");
//        }
//    }
//
//    private LoginResponse mapToUserResponse(Map<String, Object> result/**,String token**/) {
//		LoginResponse response = new LoginResponse();
//		response.setUserId(((Integer) result.get("userId")));
//		response.setEmailId((String) result.get("emailId"));
//		response.setRoleId(result.get("roleId") != null ? ((Number) result.get("roleId")).intValue() : null);
//		response.setActiveStatus(result.get("activeStatus") != null && (Boolean) result.get("activeStatus"));
////		response.setToken(token);
//
//		return response;
//	}
//
//    private boolean isValidUser(User user, String password) {
//        return user != null && user.getPassword() != null && user.getPassword().equals(password);
//    }
//
//    private boolean isAuthorizedUser(User user) {
//        if(user.getRole().getId() == 1 || user.getRole().getId() == 2 || user.getRole().getId() == 3 ){
//            return userRepository.isValidSuperUser(user.getEmailId(),user.getPassword());
//        }
//        return userRepository.isValidSuperUser(user.getEmailId(), user.getPassword());
//    }


    @Override
    public User login(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByEmailIdAndPasswordForLogin(loginDTO.getMobileNumber(),loginDTO.getMobileNumber(),loginDTO.getPassword());
        return user.orElse(null);
    }

}
