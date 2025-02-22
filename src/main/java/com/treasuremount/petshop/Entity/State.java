package com.treasuremount.petshop.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "State", schema = "public")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CountryId", nullable = false,insertable = false,updatable = false)
    @JsonIgnore
    private Country country;

    @Column(name = "countryId")
    private Long CountryId;

    @Column(name = "stateName", nullable = false, length = 128)
    private String stateName;


    public State(Long id, Country country, Long countryId, String stateName) {
        this.id = id;
        this.country = country;
        CountryId = countryId;
        this.stateName = stateName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }*/

    public Long getCountryId() {
        return CountryId;
    }

    public void setCountryId(Long countryId) {
        CountryId = countryId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public State() {
        super();
    }
}
