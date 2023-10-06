package assignment14.SimpleChat.web;

import assignment14.SimpleChat.domain.ChatRoom;
import assignment14.SimpleChat.domain.ChatRoomDto;
import assignment14.SimpleChat.domain.Message;
import assignment14.SimpleChat.domain.User;
import assignment14.SimpleChat.service.ChatRoomService;
import assignment14.SimpleChat.service.MessageService;
import assignment14.SimpleChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping("/chatroom")
public class ChatRoomController {

    ChatRoomService chatRoomService;
    UserService userService;

    MessageService messageService;

    public ChatRoomController(ChatRoomService chatRoomService, UserService userService, MessageService messageService) {
        this.chatRoomService = chatRoomService;
        this.userService = userService;
        this.messageService = messageService;
    }
    @GetMapping("")
    public String handlePageErrors() {
        return "chatroom";
    }


    @PostMapping("/{room_id}/user_id/{user_id}")
    public String saveMessage(@PathVariable Long room_id, @PathVariable Long user_id, Message message) {
        User user = userService.findById(user_id);
        ChatRoom chatRoom = chatRoomService.findById(room_id);
        message.setUser(user);
        user.getMessages().add(message);
        message.setChatRoom(chatRoom);
        messageService.saveMessage(message);
        userService.saveUser(user);
        return "redirect:/chatroom/%d/user_id/%d".formatted(room_id, user_id);
    }

    @GetMapping("/{room_id}/messages")
    @ResponseBody
    public ChatRoomDto getMessagesForChatRoom(@PathVariable Long room_id) {
        ChatRoom chatRoom = chatRoomService.findById(room_id);
        return chatRoomService.findByIdDto(room_id);
    }

}
