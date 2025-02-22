package com.treasuremount.petshop.ProductImage.Position;

import com.treasuremount.petshop.Entity.Reviews;
import com.treasuremount.petshop.Service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/public/position")
public class PositionController {

    @Autowired
    private PositionService service;


    @PostMapping("/add")
    public ResponseEntity<Position> createReviews(@RequestBody Position reviews) {
        Position createdReviews = service.create(reviews);
        if (createdReviews != null) {
            return ResponseEntity.ok(createdReviews);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Position>> getAllReviews() {
        List<Position> reviews = service.getAll();

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Position> getReviewById(@PathVariable("id") Long id) {
        Position reviews = service.getOne(id);
        if (reviews != null) {
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Position> updateReviews(@RequestBody Position reviews, @PathVariable("id") Long id) {
        Position updatedReviews = service.update(reviews, id);
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
}