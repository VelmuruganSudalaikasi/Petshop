package com.treasuremount.petshop.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="SubCategory", schema="public" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)/**SEQUENCE, generator = "user_seq")
 @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1, initialValue = 1)**/
 @Column(name = "id", nullable = false)
 private Long id;

 @Column(name = "name", nullable = false, length = 128)
 private String name;


 @Column(name = "activeStatus")
 private Boolean activeStatus;

 @Column(name = "categoryId")
 private Long categoryId;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "categoryId", referencedColumnName = "id", insertable = false, updatable = false)
 @JsonIgnore
 private Category category;


 private String imageUrl;

 }
