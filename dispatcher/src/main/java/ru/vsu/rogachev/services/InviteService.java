package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.Invite;

public interface InviteService {
    void add(Long senderId, Long recipientId);

    Invite getById(Long id);

    Invite getBySenderId(Long id);

    Invite getByRecipientId(Long id);

    void delete(Long id);
}
