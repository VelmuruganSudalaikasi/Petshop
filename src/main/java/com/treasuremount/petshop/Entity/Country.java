package com.treasuremount.petshop.Entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Country", schema = "public")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "countryName", nullable = false, length = 128)
    private String countryName;

    @Column(name = "activeStatus")
    private Boolean activeStatus;

    public Country(Long id, String countryName, Boolean activeStatus) {
        this.id = id;
        this.countryName = countryName;
        this.activeStatus = activeStatus;
    }

    public Country(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    // Keep existing methods...

    // Add equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return id != null ? id.equals(country.id) : country.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}