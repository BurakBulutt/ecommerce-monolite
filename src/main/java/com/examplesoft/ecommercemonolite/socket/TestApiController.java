package com.examplesoft.ecommercemonolite.socket;

import com.examplesoft.ecommercemonolite.socket.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestApiController {
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("send-message")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<Void> sendMessageOnTopic(){
        MessageDto message = new MessageDto();
        message.setTitle("Başlık");
        message.setMessage("Hello World");

        messagingTemplate.convertAndSend("/topic/notifications", message);

        return ResponseEntity.ok().build();
    }
}
