package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;

public interface ConfirmService {

    void add(ConfirmRequest confirmRequest);

    void add(Long userId, String confirmationCode);

    void add(User user, String confirmationCode);

    ConfirmRequest getById(Long id);

    ConfirmRequest getByUserId(Long id) throws DbDontContainObjectException;

    void deleteById(Long id);

    void deleteByUserId(Long id);

}
