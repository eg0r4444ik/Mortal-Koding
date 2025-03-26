package ru.vsu.rogachev.config;

import java.util.List;

public class Constants {

    public static final String UNKNOWN_COMMAND_TEXT = "Такой комады не существет, выберите одну из предложенных";

    public static final String NON_ACTIVE_ACCOUNT_MESSAGE =
            "Похоже вы еще не привязали свой аккаунт на сайте Codeforces с этим чатом. " +
                    "Введите свой handle с сайта https://codeforces.com/ и введите код подтверждения, " +
                    "который придет вам на почту, привязанную к вашему аккаунту codeforces. " +
                    "!! Если вы скрыли свою почту на codeforces, сделайте ее публичной !!";

    public static final String OPERATION_NOT_SUPPORTED_YET_MESSAGE =
            "Опция еще находится в разработке и пока недоступна, выберите другую";

    public static final String START_GAME_MESSAGE = "Соревнование началось";

    public static final List<String> BASIC_STATE_BUTTON_TEXTS =
            List.of("Найти дуэль", "Играть с другом", "Посмотреть рейтинг");

    public static final List<String> BASIC_STATE_BUTTON_CALLBACK_DATA =
            List.of("find_game", "play_with_friend", "look_rating");

    public static final List<String> WAIT_CONFIRMATION_CODE_STATE_BUTTON_TEXTS =
            List.of("Отправить код заново", "Ввести другой хэндл");

    public static final List<String> WAIT_CONFIRMATION_CODE_STATE_BUTTON_CALLBACK_DATA =
            List.of("send_again", "change_handle");

    public static final List<String> DURING_THE_GAME_STATE_BUTTON_TEXTS = List.of("Показать оставшееся время");

    public static final List<String> DURING_THE_GAME_STATE_BUTTON_CALLBACK_DATA = List.of("show_timer");

}
