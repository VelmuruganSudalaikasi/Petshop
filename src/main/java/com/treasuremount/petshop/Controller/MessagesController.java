package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.Entity.Messages;
import com.treasuremount.petshop.Service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/messages")
public class MessagesController {

    @Autowired
    private MessagesService service;

    @PostMapping("/add")
    public ResponseEntity<Messages> createMessages(@RequestBody Messages message) {
        Messages createdMessage = service.create(message);
        if (createdMessage != null) {
            return ResponseEntity.ok(createdMessage);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Messages>> getAllMessages() {
        List<Messages> messages = service.getAll();
        if (!messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.noContent().build(); // 204 No Content if empty
        }
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Messages> getMessageById(@PathVariable("id") Long id) {
        Messages messages = service.getOne(id);
        if (messages != null) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Messages> updateMessages(@RequestBody Messages message, @PathVariable("id") Long id) {
        Messages updatedMessages = service.update(message, id);
        if (updatedMessages != null) {
            return ResponseEntity.ok(updatedMessages);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
}
