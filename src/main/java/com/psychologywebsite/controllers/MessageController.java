package com.psychologywebsite.controllers;

import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.message.MessageDto;
import com.psychologywebsite.dtos.message.SendMessageDto;
import com.psychologywebsite.dtos.user.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/messages")
public interface MessageController {
    @PostMapping("")
    MessageDto saveMessage(@RequestBody SendMessageDto messageDto);

    @GetMapping("")
    CommonResponseDto<List<MessageDto>> getMessages(@RequestParam(name = "sender") String sender, @RequestParam(name = "receiver") String receiver);

    @GetMapping("/{sender}")
    CommonResponseDto<List<UserDto>> getMessagesBySender(@PathVariable(name = "sender") String sender);

    @DeleteMapping("/{email}")
    CommonResponseDto<String> deleteMessage(@PathVariable(name = "email") String email);
}
