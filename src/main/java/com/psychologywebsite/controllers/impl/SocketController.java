package com.psychologywebsite.controllers.impl;

import com.psychologywebsite.dtos.message.MessageDto;
import com.psychologywebsite.dtos.message.SendMessageDto;
import com.psychologywebsite.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @Autowired
    MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    public MessageDto sendMessage(SendMessageDto sendMessageDto) {
        MessageDto response = messageService.saveMessage(sendMessageDto);

        messagingTemplate.convertAndSendToUser(sendMessageDto.getReceiver(), "/private", sendMessageDto);
        return response;
    }
}
