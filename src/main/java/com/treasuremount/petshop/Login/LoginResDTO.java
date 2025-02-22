package com.treasuremount.petshop.Login;



public class LoginResDTO {

    private Integer userId;
    private String emailId;
    private Integer roleId;
    private Boolean activeStatus;
    private String token;

    public LoginResDTO() {
    }

    @Override
    public String toString() {
        return "LoginResDTO{" +
                "userId=" + userId +
                ", emailId='" + emailId + '\'' +
                ", roleId=" + roleId +
                ", activeStatus=" + activeStatus +
                ", token='" + token + '\'' +
                '}';
    }


    public LoginResDTO(Integer userId, String emailId, Integer roleId, Boolean activeStatus, String token) {
        this.userId = userId;
        this.emailId = emailId;
        this.roleId = roleId;
        this.activeStatus = activeStatus;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

