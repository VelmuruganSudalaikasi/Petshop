package com.treasuremount.petshop.Entity;


import com.treasuremount.petshop.Enum.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "bank_details")
@Getter
@Setter
public class BankDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "bank_branch", nullable = false)
    private String bankBranch;

    @Column(name = "ifsc_code", nullable = false, length = 11)
    private String ifscCode;

    @Column(name = "upi_id", nullable = true, unique = true)
    private String upiId;

    @Column(name = "upi_linked_mobile_number", nullable = true, length = 10)
    private String upiLinkedMobileNumber;

}
