package com.treasuremount.petshop.Doctor.Veterinarian;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Doctor.Appointment.Appointment;
import com.treasuremount.petshop.Doctor.DoctorScheduleConfig;
import com.treasuremount.petshop.Entity.Country;
import com.treasuremount.petshop.Entity.State;
import com.treasuremount.petshop.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Veterinarian {
    /*
    *  name,
    *  phone_number,
    *  address,
    *   city,
    *   doctor_image -> userImage
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String education;
    private String Specializations;
    private Integer yearsOfExperience;
    private String address;
    private String city;
    private String language;
    private String animalType;

    @OneToOne()
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private User user;
    @Column(name = "userId")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Country country;

    @Column(name = "CountryId")
    private Long CountryId;


    @Lob
    @Column(name = "aboutMe",nullable = true)
    private String aboutMe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StateId", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private com.treasuremount.petshop.Entity.State State;
    @Column(name = "StateId")
    private Long StateId;



    @OneToMany(mappedBy = "veterinarian", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<DoctorScheduleConfig> schedules;


    @Override
    public String toString() {
        return "Veterinarian{" +
                "id=" + id +
                ", education='" + education + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", address='" + address + '\'' +
                ", language='" + language + '\'' +
                ", animalType='" + animalType + '\'' +
                ", schedules=" + schedules +
                '}';
    }
}

//logic find the available slot by getOneBy Id , when booking is happen block the calendar specific timing

