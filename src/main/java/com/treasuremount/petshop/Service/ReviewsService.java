package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.DTO.ReviewsDTO;
import com.treasuremount.petshop.Entity.Reviews;

import java.util.List;

public interface ReviewsService {

    Reviews create(Reviews d);

     List<Reviews> getAll(Long productId);

     List<ReviewsDTO> getAllReviewsC1(
            Long userId,
            Boolean isUser,
            Long productId,
            Boolean isApproved
    );

   boolean approveReview(Long reviewId, Boolean isApproved);

    Reviews getOne(Long id);

    Reviews update(Reviews d, Long id);

    void delete(Long id);

}
