package assignment14.SimpleChat.service;

import assignment14.SimpleChat.domain.ChatRoom;
import assignment14.SimpleChat.domain.Message;
import assignment14.SimpleChat.domain.User;
import assignment14.SimpleChat.domain.UserDto;
import assignment14.SimpleChat.repository.ChatRoomRepository;
import assignment14.SimpleChat.repository.MessageRepository;
import assignment14.SimpleChat.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    MessageRepository messageRepository;
    ChatRoomRepository chatRoomRepository;


    public UserService(UserRepository userRepository, MessageRepository messageRepository,
                       ChatRoomRepository chatRoomRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


    public User findById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void deleteUser(Long user_id) {
        //Only user with no messages while not active on chat room can be deleted
        User user = userRepository.findById(user_id).orElse(null);
        assert user != null;
        if (!user.isActive()) {
            for (ChatRoom chatRoom : user.getRooms()) {
                chatRoom.getUsers().remove(user);
                chatRoomRepository.save(chatRoom);
            }
            for (Message message : user.getMessages()) {
                messageRepository.delete(message);
            }

            userRepository.deleteById(user_id);
        }
        if (userRepository.findAll().isEmpty()) {
            chatRoomRepository.deleteAll();
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    

}
