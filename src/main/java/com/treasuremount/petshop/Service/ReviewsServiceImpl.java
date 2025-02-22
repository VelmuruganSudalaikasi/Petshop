package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.DTO.ReviewsDTO;
import com.treasuremount.petshop.Entity.Reviews;
import com.treasuremount.petshop.Repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepository repository;

    @Override
    public Reviews create(Reviews d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Reviews> getAll(Long productId) {
        try {
            return repository.findByProductId(productId);

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }


    @Override
    public Reviews getOne(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Reviews update(Reviews d, Long id) {
        try {
            if (repository.existsById(id)) {
                d.setId(id);
                return repository.saveAndFlush(d);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReviewsDTO> getAllReviewsC1(
            Long userId,
            Boolean isUser,
            Long productId,
            Boolean isApproved
    ){
        return repository.findByAll(userId,isUser,productId,isApproved);
    }

    public boolean approveReview(Long reviewId, Boolean isApproved) {
        // Check if the review exists
        Optional<Reviews> reviewOpt = repository.findById(reviewId);
        if (reviewOpt.isPresent()) {
            // Update the approval status
            int rowsUpdated = repository.updateReviewApprovalStatus(reviewId, isApproved);
            return rowsUpdated > 0;
        }
        return false;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}