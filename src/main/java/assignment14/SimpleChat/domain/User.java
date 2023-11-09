package assignment14.SimpleChat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "users")
@AllArgsConstructor// from lombok
@NoArgsConstructor // from lombok
@Data // from lombok
@EqualsAndHashCode // from lombok
@SuperBuilder // from lombok
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;

    boolean selected = false;
    boolean inChatRoom = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Message> messages = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<ChatRoom> rooms = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                '}';
    }

}
