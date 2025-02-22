package com.treasuremount.petshop.Profile;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileImageRepo extends JpaRepository<ProfileImage,Long> {
    Optional<ProfileImage> findByUserId(Long userId);

}
