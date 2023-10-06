package assignment14.SimpleChat.repository;

import assignment14.SimpleChat.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @NotNull Optional<User> findById(@NotNull Long user_id);

    Optional<User> findByUsername(String username);
}
