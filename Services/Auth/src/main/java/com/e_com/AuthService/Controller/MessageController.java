package com.e_com.AuthService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.AuthService.Service.MessagePublisher;

@RestController
@RequestMapping("/api/auth")
public class MessageController {

    private final MessagePublisher publisher;

    public MessageController(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/send")
    public String send(@RequestParam String msg) {
        publisher.sendMessage(msg);
        return "Sent: " + msg;
    }
}
