package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.Invite;
import ru.vsu.rogachev.repositories.InviteRepository;
import ru.vsu.rogachev.services.InviteService;

@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    @Override
    public void add(Long senderId, Long recipientId) {
        inviteRepository.save(new Invite(senderId, recipientId));
    }

    @Override
    public Invite getById(Long id) {
        return inviteRepository.findById(id).get();
    }

    @Override
    public Invite getBySenderId(Long id) {
        return inviteRepository.findBySenderTelegramId(id).get();
    }

    @Override
    public Invite getByRecipientId(Long id) {
        return inviteRepository.findByRecipientTelegramId(id).get();
    }

    @Override
    public void delete(Long id) {
        inviteRepository.deleteById(id);
    }
}
