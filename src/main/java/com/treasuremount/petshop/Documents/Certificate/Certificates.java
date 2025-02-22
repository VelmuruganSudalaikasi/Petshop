package com.treasuremount.petshop.Documents.Certificate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treasuremount.petshop.Documents.Documents;
import com.treasuremount.petshop.Entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificates", schema = "public")
public class Certificates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "productId", insertable = false,updatable = false)
    @JsonIgnore
    private Product product;

     @Column(name= "productId" )
     private Long productId;

    @ManyToOne
    @JoinColumn(name = "documentId", insertable = false,updatable = false)
    @JsonIgnore
    private Documents documents;

    @Column(name= "documentId" )
    private Long documentId;

    @Column(name = "imageUrl" , length = 1000)
    private String imageUrl;


}
