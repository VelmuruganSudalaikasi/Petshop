package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.Entity.Messages;

import java.util.List;

public interface MessagesService {

    Messages create(Messages d);

    List<Messages> getAll();

    Messages getOne(Long id);

    Messages update(Messages d, Long id);

    void delete(Long id);

}