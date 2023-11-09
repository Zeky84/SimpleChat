package assignment14.SimpleChat.domain;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long user_id;
    private String username;
    private boolean selected;
    private boolean inChatRoom;
    private List<String> messages = new ArrayList<>();

    private String chatRoomName;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isInChatRoom() {
        return inChatRoom;
    }

    public void setInChatRoom(boolean inChatRoom) {
        this.inChatRoom = inChatRoom;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
}
