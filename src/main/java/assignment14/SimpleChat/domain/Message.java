package assignment14.SimpleChat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "messages")
@AllArgsConstructor// from lombok
@NoArgsConstructor // from lombok
@Data // from lombok
@EqualsAndHashCode // from lombok
@SuperBuilder // from lombok
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    private String stringMessage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", stringMessage='" + stringMessage + '\'' +
                '}';
    }

}
