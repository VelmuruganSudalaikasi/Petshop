package com.treasuremount.petshop.Testimonial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Testimonial")
@Data
@AllArgsConstructor
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false,length = 2000)
    private String name;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "description", length = 2000)
    @Lob
    private String description;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "activeStatus")
    private Boolean activeStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate")
    private Date modifiedDate;

    public Testimonial() {
    }
}
