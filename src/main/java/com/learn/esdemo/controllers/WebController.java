package com.learn.esdemo.controllers;

import com.learn.esdemo.services.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/messages")
public class WebController {

    private final MessageService messageService;

    @Autowired
    public WebController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<String> getMessage() {

        return ok(messageService.getMessage());
    }
}
