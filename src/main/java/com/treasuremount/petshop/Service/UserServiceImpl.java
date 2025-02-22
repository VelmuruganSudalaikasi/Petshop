package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.ExceptionHandler.DuplicateUserException;
import com.treasuremount.petshop.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    private UserRepo repository;

    @Autowired
    private EmailService emailService;

    private static final String application_name = "Treasure Mount";
/*
    @Autowired
    BCryptPasswordEncoder encoder;*/

    @Override
    public User create(User d) {

            if(findByMobileOREmail(d.getMobileNumber(),d.getEmailId())!=null){
                System.out.println(repository.findUserByPhoneNumber(d.getMobileNumber(),d.getEmailId()));
                System.out.println("user is already present");


            }

           /* String encoded=encoder.encode(d.getPassword());
            d.setPassword(encoded);*/
           d.setId(null);
            System.out.println("Before Saving the user" + d);

            User user= repository.save(d);
            System.out.println("new user is created" + user);

        // send email for vendor
        if (user.getRoleId()==2){
            vendorRegistrationEmail(user.getEmailId(), user.getFirstName());
        }
        // send email for veterinarian
        if (user.getRoleId()==4){
            veterinarianRegistrationEmail(user.getEmailId(), user.getFirstName());
        }
        // send email for user
        if(user.getRoleId()==3){
            userRegistrationEmail(user.getEmailId(), user.getFirstName());
        }

            return user;

    }
    public User findByMobileOREmail(String number,String emailId){
        Optional<User> response=null;
        boolean flag=(response=repository.findUserByPhoneNumber(number,emailId)).isEmpty();
        if(flag) return null;

        String existingMobileNumber=response.get().getMobileNumber();
        String message="";
        if(existingMobileNumber !=null && existingMobileNumber.equalsIgnoreCase(number)){
            message+="Mobile Number ";
        }
        if(!message.isEmpty()){
            message+=" AND ";
        }
        String existingEmailId=response.get().getEmailId();
        if(existingMobileNumber !=null && existingEmailId.equalsIgnoreCase(emailId)){
            message+="Email Id ";
        }
        throw new DuplicateUserException(message+"Registered Already");


    }
    // Vendor Registration Email

    private void vendorRegistrationEmail(String vendorEmail, String vendorName) {
        String subject = "Welcome to "+application_name+", Vendor!";
        String body = "Dear " + vendorName + ",\n\n" +
                "Thank you for registering as a vendor at "+application_name+". We are thrilled to have you as part of our community.\n\n" +
                "You can now start listing your products and managing your store. If you need any assistance, feel free to reach out to our support team.\n\n" +
                "Best regards,\nThe "+application_name+" Team\n\n" +
                "---\nIf you didn't register an account with us, please ignore this email.";
        emailService.sendEmailNotification(vendorEmail, subject, body);
    }
    // Veterinarian Registration Email

    private void veterinarianRegistrationEmail(String vetEmail, String vetName) {
        String subject = "Welcome to "+application_name+", Veterinarian!";
        String body = "Dear Dr. " + vetName + ",\n\n" +
                "Thank you for registering as a veterinarian at "+application_name+". We are excited to have you as part of our team.\n\n" +
                "You can now start offering consultations and check-ups for pets. If you need any assistance, feel free to reach out to our support team.\n\n" +
                "Best regards,\nThe "+application_name+" Team\n\n" +
                "---\nIf you didn't register an account with us, please ignore this email.";
        emailService.sendEmailNotification(vetEmail, subject, body);
    }
    // User Registration Email

    private void userRegistrationEmail(String userEmail, String userFirstName) {
        String subject = "Welcome to "+application_name+", " + userFirstName + "!";
        String body = "Dear " + userFirstName + ",\n\n" +
                "Welcome to "+application_name+"! We are excited to have you join our community.\n\n" +
                "You can now browse our catalog, buy pets, and even book appointments with veterinarians. If you need any assistance, feel free to reach out to our customer support team.\n\n" +
                "Best regards,\nThe "+application_name+" Team\n\n" +
                "---\nIf you didn't register an account with us, please ignore this email.";
        emailService.sendEmailNotification(userEmail, subject, body);
    }



    public void throwValidMessage(String existing,String current,String message){
        if(existing.equals(current)) {
            throw  new DuplicateUserException(message);
        }
    }

	@Override
	public List<User> getAll() {
		try {
			return repository.findAll();

		} catch (Exception ex) {
			return Collections.emptyList();
		}
	}


/*
    @Override
    public boolean editUser(User v){
        Optional<User> oldVendor=repository.findById(v.getId());
        if(oldVendor.isEmpty()){
            return false;
        }

        create(patchUser(oldVendor.get(),v));
        return true;

    }*/
/*

    public User patchUser( User existingUser, User updatedUser) {
        // Fetch the existing user from the database


        // Update fields only if they are non-null in the updatedUser
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getEmailId() != null) {
            existingUser.setEmailId(updatedUser.getEmailId());
        }

        if (updatedUser.getMobileNumber() != null) {
            existingUser.setMobileNumber(updatedUser.getMobileNumber());
        }

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        if (updatedUser.getConfirmPassword() != null) {
            existingUser.setConfirmPassword(updatedUser.getConfirmPassword());
        }

        if (updatedUser.getActiveStatus() != null) {
            existingUser.setActiveStatus(updatedUser.getActiveStatus());
        }

        if (updatedUser.getRoleId() != null) {
            existingUser.setRoleId(updatedUser.getRoleId());
        }

        if (updatedUser.getCreatedDate() != null) {
            existingUser.setCreatedDate(updatedUser.getCreatedDate());
        }

        if (updatedUser.getModifiedDate() != null) {
            existingUser.setModifiedDate(updatedUser.getModifiedDate());
        }

        // Save the updated user back to the database
        return repository.save(existingUser);
    }
*/



    @Override
    public User getOneUserById(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }



    @Override
    public User update(User d, Long id) {
        try {
            if (repository.existsById(id)) {
                d.setId(id);
                return repository.saveAndFlush(d);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }
    @Override
   public  User findById(Long id){
       return  repository.findById(id).orElse(null);
    }

    @Override
   public List<User> getAllByUserIds(List<Long> userId){
        return repository.findAllById(userId);

    }


    @Override
    public String getUserEmailById(Long userId) {
        // Assuming you have a User entity and a repository for it
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return user.getEmailId();  // Assuming the User entity has an email field
    }


}