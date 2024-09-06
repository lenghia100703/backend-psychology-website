package com.psychologywebsite.services;

import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.message.ContactDto;
import com.psychologywebsite.dtos.message.MessageDto;
import com.psychologywebsite.dtos.message.SendMessageDto;
import com.psychologywebsite.dtos.user.UserDto;

import java.util.List;

public interface MessageService {
    MessageDto saveMessage(SendMessageDto messageDto);

    CommonResponseDto<List<MessageDto>> getMessages(ContactDto contactDto);

    CommonResponseDto<List<UserDto>> getListMessagesBySender(String sender);

    CommonResponseDto<String> deleteMessage(String email);
}
