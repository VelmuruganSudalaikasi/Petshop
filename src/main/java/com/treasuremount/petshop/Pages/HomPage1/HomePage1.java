package com.treasuremount.petshop.Pages.HomPage1;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePage1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String subtitle;
    private String shortDescription;
    private String button;
    private String imageUrl;
    private String redirectUrl;
}
