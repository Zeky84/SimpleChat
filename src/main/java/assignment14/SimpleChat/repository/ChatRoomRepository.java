package assignment14.SimpleChat.repository;

import assignment14.SimpleChat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatRoomName(String chatRoomName);

    @Modifying
    @Query(value = "DELETE FROM user_chatroom WHERE user_id = ?1 AND chatroom_id = ?2", nativeQuery = true)
    //native query is used to delete from the join table
    void deleteUserChatRoomAssociation(Long userId, Long chatroomId);
}