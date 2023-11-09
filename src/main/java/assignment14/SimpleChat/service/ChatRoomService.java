package assignment14.SimpleChat.service;

import assignment14.SimpleChat.domain.ChatRoom;
import assignment14.SimpleChat.domain.ChatRoomDto;
import assignment14.SimpleChat.domain.Message;
import assignment14.SimpleChat.domain.User;
import assignment14.SimpleChat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void saveChatRoom(ChatRoom chatRoom) {chatRoomRepository.save(chatRoom);}

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }

    public void deleteChatRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    public void deleteAll() {
        chatRoomRepository.deleteAll();
    }

    public Optional<ChatRoom> findByChatRoomName(String chatRoomName) {
         return chatRoomRepository.findByChatRoomName(chatRoomName);
    }

    public ChatRoomDto convertFromEntityToJson(Long id) {
        //to send to the front end as a json
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setRoom_id(chatRoom.getRoom_id());
        chatRoomDto.setChatUsers(chatRoom.getUsers().stream().map(User::getUsername).toList());
        chatRoomDto.setChatRoomName(chatRoom.getChatRoomName());
        for(Message message: chatRoom.getMessages()){
            Long messageId = message.getMessage_id();
            String username = message.getUser().getUsername();
            String messageString = message.getStringMessage();
            chatRoomDto.getMessages().put(messageId, List.of(username, messageString));
        }
        return chatRoomDto;
    }

    public void deleteUserChatRoomAssociation(Long user_id, Long room_id) {
        ChatRoom chatRoom = chatRoomRepository.findById(room_id).orElse(null);
        User user = chatRoom.getUsers().stream().filter(u -> u.getUser_id().equals(user_id)).findFirst().orElse(null);
        chatRoom.getUsers().remove(user);
        chatRoomRepository.save(chatRoom);
    }
}

