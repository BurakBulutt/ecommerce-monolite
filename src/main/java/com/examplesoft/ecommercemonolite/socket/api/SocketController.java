package com.examplesoft.ecommercemonolite.socket.api;

import com.examplesoft.ecommercemonolite.socket.dto.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public MessageDto notifyUsers(@Payload MessageDto message) {
        return message;
    }
}
