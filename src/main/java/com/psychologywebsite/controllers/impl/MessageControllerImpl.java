package com.psychologywebsite.controllers.impl;

import com.psychologywebsite.controllers.MessageController;
import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.message.ContactDto;
import com.psychologywebsite.dtos.message.MessageDto;
import com.psychologywebsite.dtos.message.SendMessageDto;
import com.psychologywebsite.dtos.user.UserDto;
import com.psychologywebsite.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageControllerImpl implements MessageController {
    @Autowired
    MessageService messageService;

    @Override
    public MessageDto saveMessage(SendMessageDto messageDto) {
        return messageService.saveMessage(messageDto);
    }

    @Override
    public CommonResponseDto<List<MessageDto>> getMessages(String sender, String receiver) {
        return messageService.getMessages(new ContactDto(sender, receiver));
    }

    @Override
    public CommonResponseDto<List<UserDto>> getMessagesBySender(String sender) {
        return messageService.getListMessagesBySender(sender);
    }

    @Override
    public CommonResponseDto<String> deleteMessage(String email) {
        return messageService.deleteMessage(email);
    }
}
