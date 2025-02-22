package com.treasuremount.petshop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Blog")
@Data
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "userId")
    private Long userId;


    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "CategoryId",referencedColumnName = "id",insertable = false,updatable = false)
    @JsonIgnore
    private Category category;

    @Column(name="CategoryId")
    private Long CategoryId;

    @Column(name = "heading", nullable = false, length = 255)
    private String heading;

    @Column(name = "activeStatus")
    private Boolean activeStatus;


    @Column(name = "shortDescription", nullable = false, length = 255)
    private String shortDescription;

    @Column(name = "Description", columnDefinition = "TEXT", nullable = false)
    @Lob
    private String Description;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_at")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    public Blog(){}

}
