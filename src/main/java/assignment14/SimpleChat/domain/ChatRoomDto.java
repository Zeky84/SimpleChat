package assignment14.SimpleChat.domain;

import java.util.*;

public class ChatRoomDto {
    private long room_id;
    private String chatRoomName;

    private List<String> chatUsers = new ArrayList<>();
    private Map<Long, List<String>> messages = new LinkedHashMap<>();

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }



    public Map<Long, List<String>> getMessages() {
        return messages;
    }

    public void setMessages(Map<Long, List<String>> messages) {
        this.messages = messages;
    }

    public List<String> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<String> chatUsers) {
        this.chatUsers = chatUsers;
    }
}
