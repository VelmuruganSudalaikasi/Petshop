package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.ReviewsDTO;
import com.treasuremount.petshop.Entity.Reviews;
import com.treasuremount.petshop.Service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService service;

    @PostMapping("/add")
    public ResponseEntity<Reviews> createReviews(@RequestBody Reviews reviews) {
        Reviews createdReviews = service.create(reviews);
        if (createdReviews != null) {
            return ResponseEntity.ok(createdReviews);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Reviews>> getAllReviews(
            @RequestParam(required = false, defaultValue = "0") Long productId
    ) {
        List<Reviews> reviews = service.getAll(productId);

            return ResponseEntity.ok(reviews);
    }

    /*
    *  userName
    *
    * */

    @GetMapping("v1/getAll")
    public ResponseEntity<List<ReviewsDTO>> getAllReviewsV1(
            @RequestParam(required = false, defaultValue = "0") Long userId,
            @RequestParam(required = false) Boolean isUser,
            @RequestParam(required = false, defaultValue = "0") Long productId,
            @RequestParam(required = false) Boolean isApproved
    ) {
        List<ReviewsDTO> reviews = service.getAllReviewsC1(userId,isUser,productId,isApproved);

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable("id") Long id) {
        Reviews reviews = service.getOne(id);
        if (reviews != null) {
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reviews> updateReviews(@RequestBody Reviews reviews, @PathVariable("id") Long id) {
        Reviews updatedReviews = service.update(reviews, id);
        if (updatedReviews != null) {
            return ResponseEntity.ok(updatedReviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReviews(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/approve/{reviewId}")
    public ResponseEntity<String> approveReview(
            @PathVariable Long reviewId,
            @RequestParam Boolean isApproved) {
        boolean success = service.approveReview(reviewId, isApproved);
        if (success) {
            String status = isApproved ? "approved" : "disapproved";
            return ResponseEntity.ok("Review has been " + status + " successfully.");
        } else {
            return ResponseEntity.badRequest().body("Review not found or could not be updated.");
        }
    }




}