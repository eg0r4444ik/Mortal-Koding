package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.Invite;

@Repository
public interface InviteRepository  extends JpaRepository<Invite, Long> {

}
