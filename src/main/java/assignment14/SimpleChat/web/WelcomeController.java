package assignment14.SimpleChat.web;

import assignment14.SimpleChat.domain.ChatRoom;
import assignment14.SimpleChat.domain.Message;
import assignment14.SimpleChat.domain.User;
import assignment14.SimpleChat.domain.UserDto;
import assignment14.SimpleChat.repository.ChatRoomRepository;
import assignment14.SimpleChat.repository.UserRepository;
import assignment14.SimpleChat.service.ChatRoomService;
import assignment14.SimpleChat.service.MessageService;
import assignment14.SimpleChat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {

    UserService userService;
    ChatRoomService chatRoomService;
    MessageService messageService;
    User setUser = null; // this is the user that is going to be select, but not active yet. Is going to be active when go to chatroom


    public WelcomeController(UserService userService, ChatRoomService chatRoomService, MessageService messageService, ChatRoomRepository chatRoomRepository) {
        this.userService = userService;
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
    }

    @GetMapping("/welcome/deactivate/{room_id}/user/{user_id}")
    public String deactivateUser(@PathVariable Long user_id, @PathVariable Long room_id) {
        if (userService.findById(user_id) == null) {
            return "redirect:/welcome";
        }
        ChatRoom chatRoom = chatRoomService.findById(room_id);
        User user = userService.findById(user_id);
        user.setActive(false);
        chatRoom.getUsers().remove(user);
        user.getRooms().remove(chatRoom);
        chatRoomService.saveChatRoom(chatRoom);
        userService.saveUser(user);
        chatRoomService.deleteUserChatRoomAssociation(user_id, room_id);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(ModelMap model) {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        List<User> users = userService.findAll();
        model.put("chatRoom", new ChatRoom());
        model.put("chatRooms", chatRooms);// Thymeleaf can handle empty lists.(No need to check for empty list)
        model.put("user", new User());
        model.put("users", users);
        model.put("setUser", setUser);
        return "welcome";
    }


    @PostMapping("/createUser")
    public String saveUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("!!!USERNAME FIELD IS EMPTY!!! NOT ALLOWED");
            return "redirect:/welcome";
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("!!!USERNAME ALREADY EXISTS!!! NOT ALLOWED");
            return "redirect:/welcome";
        }
        userService.saveUser(user);
        return "redirect:/welcome";
    }

    @PostMapping("/createChatRoom")
    public String saveChatRoom(ChatRoom chatRoom) {
        if (chatRoom.getChatRoomName() == null || chatRoom.getChatRoomName().isEmpty()) {
            System.out.println("!!!CHATROOM IS EMPTY!!! NOT ALLOWED");
            return "redirect:/welcome";
        }
        if (chatRoomService.findByChatRoomName(chatRoom.getChatRoomName()).isPresent()) {
            System.out.println("!!!CHATROOM ALREADY EXISTS!!! NOT ALLOWED");
            return "redirect:/welcome";
        }
        chatRoomService.saveChatRoom(chatRoom);
        return "redirect:/welcome";
    }

    @GetMapping("/setUser/{user_id}")
    public String setUser(@PathVariable Long user_id, ModelMap model) {
        if (!userService.findById(user_id).isActive()) {
            setUser = userService.findById(user_id);
            userService.saveUser(setUser);
            model.put("setUser", setUser);
        }
        return "redirect:/welcome";
    }

    @GetMapping("/setChatRoom/{room_id}")
    public String setChatRoom(@PathVariable Long room_id, ModelMap model) {
        ChatRoom chatRoom = chatRoomService.findById(room_id);
        model.put("chatRoom", chatRoom);
        return "redirect:/welcome";
    }

    @GetMapping("chatroom/{room_id}/user_id/{user_id}")
    public String setUserChatRoom(@PathVariable Long room_id, @PathVariable Long user_id, ModelMap model) {
        //to handle error
        if (userService.findById(user_id) == null || chatRoomService.findById(room_id) == null) {
            return "redirect:/chatroom";
        }
        User user = userService.findById(user_id);
        ChatRoom chatRoom = chatRoomService.findById(room_id);
        Message message = new Message();

        //to set user to active once enter the chatroom
        if (user != null && !user.isActive()) {
            user.setActive(true);
            userService.saveUser(user);
        }
        //to set the join table between the user and chatroom
        if (!chatRoom.getUsers().contains(user)) {
            user.getRooms().add(chatRoom);
            userService.saveUser(user);
            chatRoom.getUsers().add(user);
            chatRoomService.saveChatRoom(chatRoom);
            //setting setUser to null because is already in the chatroom and is active
            setUser = null;
        }
        model.put("user", user);
        model.put("chatRoom", chatRoom);
        model.put("message", message);
        return "chatroom";
    }

    @PostMapping("/deleteUser/{user_id}")
    public String deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        setUser = null; // reset the current user
        return "redirect:/welcome";
    }

    @GetMapping("/userExists")
    @ResponseBody
    public Boolean userExists(@RequestParam String username) {
        return userService.findByUsername(username).isPresent();
    }

    @GetMapping("/chatroomExists")
    @ResponseBody
    public Boolean chatroomExists(@RequestParam String chatRoomName) {
        return chatRoomService.findByChatRoomName(chatRoomName).isPresent();
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public List<UserDto> getUsers() {
        return userService.findAllUsersDto();
    }

    @GetMapping("/getChatRooms")
    @ResponseBody
    public List<ChatRoom> getChatRooms() {
        return chatRoomService.findAll();
    }


}
