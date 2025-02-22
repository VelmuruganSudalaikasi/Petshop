package com.treasuremount.petshop.Documents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/public/documents")
public class DocumentsController {

    @Autowired
    private DocumentsService service;


    @PostMapping("/add")
    public ResponseEntity<Documents> createReviews(@RequestBody Documents reviews) {
        Documents createdReviews = service.create(reviews);
        if (createdReviews != null) {
            return ResponseEntity.ok(createdReviews);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Documents>> getAllReviews() {
        List<Documents> reviews = service.getAll();

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Documents> getReviewById(@PathVariable("id") Long id) {
        Documents reviews = service.getOne(id);
        if (reviews != null) {
            return ResponseEntity.ok(reviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Documents> updateReviews(@RequestBody Documents reviews, @PathVariable("id") Long id) {
        Documents updatedReviews = service.update(reviews, id);
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