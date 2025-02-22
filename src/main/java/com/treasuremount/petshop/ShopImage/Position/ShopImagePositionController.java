package com.treasuremount.petshop.ShopImage.Position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/public/ShopImagePosition")
public class ShopImagePositionController {

    @Autowired
    private ShopImagePositionService service;


    @PostMapping("/add")
    public ResponseEntity<ShopImagePosition> createReviews(@RequestBody ShopImagePosition reviews) {
        ShopImagePosition createdReviews = service.create(reviews);
        if (createdReviews != null) {
            return ResponseEntity.ok(createdReviews);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ShopImagePosition>> getAllReviews() {
        List<ShopImagePosition> reviews = service.getAll();

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ShopImagePosition> getReviewById(@PathVariable("id") Long id) {
        ShopImagePosition reviews = service.getOne(id);
        if (reviews != null) {
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShopImagePosition> updateReviews(@RequestBody ShopImagePosition reviews, @PathVariable("id") Long id) {
        ShopImagePosition updatedReviews = service.update(reviews, id);
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