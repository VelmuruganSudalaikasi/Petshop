package com.treasuremount.petshop.MedicineResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medicineId", insertable = false,updatable = false)
    @JsonIgnore
    @Getter(AccessLevel.NONE) // Prevents getter generation
    @Setter(AccessLevel.NONE) // Prevents setter generation
    private Medicine medicine;

    @Column(name="medicineId")
    private Long medicineId;

    @Lob
    private String activeIngredients;

    @Lob
    private String usageInstructions;

    @Lob
    private String storageInstructions;
    @Lob
    private String warnings;

}
