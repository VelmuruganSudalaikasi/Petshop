package com.treasuremount.petshop.DTO;


import java.util.Date;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNumber;
    private String password;
    private String confirmPassword;
    private Boolean activeStatus;
    private Integer roleId;

    private Date createdDate;
    private Date modifiedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }



    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
                + ", mobileNumber=" + mobileNumber + ", password=" + password + ", confirmPassword=" + confirmPassword
                + ", activeStatus=" + activeStatus + ", roleId=" + roleId + ", userProfile="
                + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
    }


    public UserDTO(Long id, String firstName, String lastName, String emailId, String mobileNumber, String password,
                   String confirmPassword, Boolean activeStatus, Integer roleId, String userProfile, Date createdDate,
                   Date modifiedDate) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.activeStatus = activeStatus;
        this.roleId = roleId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }


    public UserDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

}
