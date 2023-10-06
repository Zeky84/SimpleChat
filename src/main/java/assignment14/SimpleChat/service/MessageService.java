package assignment14.SimpleChat.service;

import assignment14.SimpleChat.domain.Message;
import assignment14.SimpleChat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }


    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public void saveAll(List<Message> allMessages) {
        messageRepository.saveAll(allMessages);
    }
}
