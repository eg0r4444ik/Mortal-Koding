package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.Invite;
import ru.vsu.rogachev.entities.User;

import java.util.Optional;

@Repository
public interface InviteRepository  extends JpaRepository<Invite, Long> {
    Optional<Invite> findBySenderTelegramId(Long id);
    Optional<Invite> findByRecipientTelegramId(Long id);
}
