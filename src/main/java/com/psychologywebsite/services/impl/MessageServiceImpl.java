package com.psychologywebsite.services.impl;

import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.message.ContactDto;
import com.psychologywebsite.dtos.message.MessageDto;
import com.psychologywebsite.dtos.message.SendMessageDto;
import com.psychologywebsite.dtos.user.UserDto;
import com.psychologywebsite.entities.MessageEntity;
import com.psychologywebsite.entities.UserEntity;
import com.psychologywebsite.enums.AuthProvider;
import com.psychologywebsite.enums.Role;
import com.psychologywebsite.repositories.MessageRepository;
import com.psychologywebsite.repositories.UserRepository;
import com.psychologywebsite.services.MessageService;
import com.psychologywebsite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Value("${default.guest-avatar}")
    String defaultAvatar;

    @Override
    public MessageDto saveMessage(SendMessageDto messageDto) {
        MessageEntity message = new MessageEntity();

        message.setSender(messageDto.getSender());
        message.setReceiver(messageDto.getReceiver());
        message.setContent(messageDto.getContent());
        message.setUsernameSender(messageDto.getUsernameSender());
        message.setUsernameReceiver(messageDto.getUsernameReceiver());
        message.setCreatedAt(new Date(System.currentTimeMillis()));
        message.setCreatedBy(message.getSender());
        if (userService.findByEmail(messageDto.getReceiver()) == null) {
            UserEntity receiver = new UserEntity();
            receiver.setEmail(messageDto.getReceiver());
            receiver.setUsername(messageDto.getUsernameReceiver());
            receiver.setAvatar(defaultAvatar);
            receiver.setRole(Role.CUSTOMER);
            receiver.setProvider(AuthProvider.LOCAL);
            userRepository.save(receiver);
        } else {
            UserEntity receiver = userService.findByEmail(messageDto.getReceiver());
            receiver.setUsername(messageDto.getUsernameReceiver());
            userRepository.save(receiver);
        }

        if (userService.findByEmail(messageDto.getSender()) == null) {
            UserEntity sender = new UserEntity();
            sender.setEmail(messageDto.getSender());
            sender.setUsername(messageDto.getUsernameSender());
            sender.setAvatar(defaultAvatar);
            sender.setRole(Role.CUSTOMER);
            sender.setProvider(AuthProvider.LOCAL);
            userRepository.save(sender);
        } else {
            UserEntity sender = userService.findByEmail(messageDto.getSender());
            sender.setUsername(messageDto.getUsernameSender());
            userRepository.save(sender);
        }

        return new MessageDto(messageRepository.save(message));
    }

    @Override
    public CommonResponseDto<List<MessageDto>> getMessages(ContactDto contactDto) {
        List<MessageEntity> messages = messageRepository.findMessagesBySenderAndReceiver(contactDto.getSender(), contactDto.getReceiver());

        return new CommonResponseDto<>(messages.stream().map(MessageDto::new).toList());
    }

    @Override
    public CommonResponseDto<List<UserDto>> getListMessagesBySender(String sender) {
        List<UserDto> entities = messageRepository.findDistinctReceiversBySender(sender);

        return new CommonResponseDto<>(entities);
    }

    @Override
    public CommonResponseDto<String> deleteMessage(String email) {
        messageRepository.deleteMessagesBetweenAdminAndUser("baobianquycuong@gmail.com", email);

        return new CommonResponseDto<>("Deleted successfully");
    }
}
