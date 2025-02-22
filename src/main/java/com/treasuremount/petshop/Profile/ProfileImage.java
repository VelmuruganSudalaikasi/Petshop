package com.treasuremount.petshop.Profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false,updatable = false)
    @JsonIgnore
    private User user;

    @Column(name= "userId" )
    private Long userId;


    @Column(name = "imageUrl" , length = 1000)
    private String imageUrl;




}
