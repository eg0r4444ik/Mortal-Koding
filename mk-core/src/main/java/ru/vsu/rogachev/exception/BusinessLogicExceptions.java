package ru.vsu.rogachev.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicExceptions {
    UNKNOWN_COMMAND(-1, "Такой комады не существет, выберите одну из предложенных"),
    OPERATION_NOT_SUPPORTED_YET(-2, "Опция временно недоступна, выберите пожалуйста другую"),
    NON_ACTIVE_ACCOUNT(-3, "Похоже вы еще не привязали свой аккаунт на сайте Codeforces с этим чатом. Введите свой handle с сайта https://codeforces.com/ и введите код подтверждения, который придет вам на почту, привязанную к вашему аккаунту codeforces. !! Если вы скрыли свою почту на codeforces, сделайте ее публичной !!"),
    CODEFORCES_ACCOUNT_NOT_FOUND(-4, "Аккаунт на Codeforces с хэндлом %s не найден, попробуйте ввести хэндл ханово"),
    CODEFORCES_ACCOUNT_EMAIL_IS_BLOCKED(-5, "На акаунте Codeforces %s скрыт адрес электронной почты. Пожалуйста, сделайте его публичным!"),
    USER_NOT_REGISTERED(-6, "Игрок с хэндлом %s не зарегестрирован в боте, попробуйте ввести другой хэндл"),
    USER_NOT_CONFIRM_ACCOUNT(-7, "Пользователь с хэндлом %s еще не подтвердил свою учетную запись"),
    USER_CREATING_THE_GAME(-8, "Пользователь с хэндлом %s в процессе создания игры"),
    USER_WAITING_CONNECTION_TO_ANOTHER_GAME(-9, "Пользователь с хэндлом %s ожидает подключения к другой игре"),
    USER_ALREADY_IN_GAME(-10, "Пользователь с хэндлом %s уже играет"),
    COMMON_LOGIC_EXCEPTION(-11, "Что-то пошло не так, попробуйте еще раз"),
    CODEFORCES_NOT_AVAILABLE(-12, "Codeforces временно недоступен"),
    USER_NOT_IN_GAME(-13, "Пользователь не играет в данный момент")
    ;

    private final Integer code;

    private final String text;


}
