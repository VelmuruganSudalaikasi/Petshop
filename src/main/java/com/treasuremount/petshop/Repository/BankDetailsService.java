package com.treasuremount.petshop.Repository;

import com.treasuremount.petshop.Entity.BankDetails;

import java.util.List;


public interface BankDetailsService {


        BankDetails create(BankDetails d);

        List<BankDetails> getAll();


        BankDetails update(BankDetails d, Long id);

        void delete(Long id);

    BankDetails getOneById(Long id);
}