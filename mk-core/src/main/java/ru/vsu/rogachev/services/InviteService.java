package ru.vsu.rogachev.services;

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

    public void add(@NotNull User user, @NotNull User inviter){
        inviteRepository.save(new Invite(user, inviter));
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
