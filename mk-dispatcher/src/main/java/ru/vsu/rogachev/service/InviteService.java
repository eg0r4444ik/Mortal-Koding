package ru.vsu.rogachev.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entity.Invite;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.repositories.InviteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;

    public void add(@NotNull Invite invite){
        inviteRepository.save(invite);
    }

    public void add(@NotNull List<User> users, @NotNull User inviter){
        inviteRepository.save(new Invite(users, inviter));
    }

    @NotNull
    public List<Invite> getAll(){
        return inviteRepository.findAll();
    }

    @Transactional
    public void delete(@NotNull Invite invite){
        inviteRepository.delete(invite);
    }
    
}
