package ru.vsu.rogachev.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.connections.CodeforcesConnection;
import ru.vsu.rogachev.connections.MailConnection;
import ru.vsu.rogachev.dto.GameDTO;
import ru.vsu.rogachev.dto.GameInfoDTO;
import ru.vsu.rogachev.dto.UserDTO;
import ru.vsu.rogachev.dto.enums.InfoType;
import ru.vsu.rogachev.entities.Invite;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.kafka.KafkaProducer;
import ru.vsu.rogachev.services.InviteService;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.utils.MessageUtils;

import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j
public class UpdateController {

    private TelegramBot telegramBot;

    private final MessageUtils messageUtils;

    private final KafkaProducer producer;
    
    private final UserService userService;

    private final CodeforcesConnection codeforcesConnection;
    
    private final MailConnection mailConnection;
    
    private final InviteService inviteService;
    
    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void printGameInfo(GameInfoDTO gameInfo){
        try {
            User player1 = userService.getUserByCodeforcesUsername(gameInfo.getHandles().get(0));
            User player2 = userService.getUserByCodeforcesUsername(gameInfo.getHandles().get(1));

            if(gameInfo.getType() == InfoType.STARTED){
                userService.setState(userService.getUserByCodeforcesUsername(gameInfo.getHandles().get(0)), UserState.DURING_THE_GAME);
                userService.setState(userService.getUserByCodeforcesUsername(gameInfo.getHandles().get(1)), UserState.DURING_THE_GAME);

                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(player1.getTelegramId(),
                        messageUtils.startGameMessage()));
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(player1.getTelegramId(),
                        messageUtils.getGameTasks(gameInfo.getTasksUrls())));

                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(player2.getTelegramId(),
                        messageUtils.startGameMessage()));
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(player2.getTelegramId(),
                        messageUtils.getGameTasks(gameInfo.getTasksUrls())));
            }else if(gameInfo.getType() == InfoType.FINISHED){
                userService.setState(userService.getUserByCodeforcesUsername(gameInfo.getHandles().get(0)), UserState.BASIC_STATE);
                userService.setState(userService.getUserByCodeforcesUsername(gameInfo.getHandles().get(1)), UserState.BASIC_STATE);

                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(player1.getTelegramId(),
                        messageUtils.endGameMessage()));
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithTable(player1.getTelegramId(),
                        gameInfo.getPoints().get(0), gameInfo.getPoints().get(1), player1.getCodeforcesUsername(),
                        player2.getCodeforcesUsername()));

                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(player2.getTelegramId(),
                        messageUtils.endGameMessage()));
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithTable(player2.getTelegramId(),
                        gameInfo.getPoints().get(0), gameInfo.getPoints().get(1), player1.getCodeforcesUsername(),
                        player2.getCodeforcesUsername()));
            }else if(gameInfo.getType() == InfoType.IN_PROGRESS){
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithTable(player1.getTelegramId(),
                            gameInfo.getPoints().get(0), gameInfo.getPoints().get(1), player1.getCodeforcesUsername(),
                            player2.getCodeforcesUsername()));
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithTable(player2.getTelegramId(),
                        gameInfo.getPoints().get(0), gameInfo.getPoints().get(1), player1.getCodeforcesUsername(),
                        player2.getCodeforcesUsername()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void processUpdate(Update update){
        if(update == null){
            log.error("Received update is null");
            return;
        }

        if(update.getMessage() != null || update.hasCallbackQuery()){
            distributeMessagesByType(update);
        }else{
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
        String text = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
        long chatId = message.getChatId();
        try {
            User user = getUser(message);
            if(user.getState() == UserState.WAIT_FOR_HANDLE_STATE){
                processWaitingHandleState(user, chatId, text);
            }else if(user.getState() == UserState.WAIT_CONFIRMATION_CODE_STATE){
                processWaitingCodeState(user, chatId, text);
            }else if(user.getState() == UserState.DURING_THE_GAME){
                processDuringTheGameState(user, chatId, text);
            }else if(user.getState() == UserState.BASIC_STATE){
                processBasicState(user, chatId, text);
            }else if(user.getState() == UserState.WAIT_OPPONENT_HANDLE_STATE){
                processOpponentHandleState(user, chatId, text);
            }else if(user.getState() == UserState.WAIT_OPPONENT_CONNECTION_STATE){
                processOpponentConnectionState(user, chatId, text);
            }
        }catch (DbDontContainObjectException e){
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(message.getChatId(), "Что-то пошло не так"));
        }
    }

    private void processWaitingHandleState(User user, long chatId, String handle){
        try {
            if(userService.existsByCodeforcesUsername(handle)){
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "С этим хэндлом уже" +
                        " зарегистрировался другой пользователь. Введите другой хэндл."));
            }else {
                UserDTO userDTO = codeforcesConnection.getUser(handle);
                if(userDTO.getEmail() == null){
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Сделайте почту публичной!"));
                }else {
                    userService.setEmail(user, userDTO.getEmail());
                    userService.setCodeforcesUsername(user, userDTO.getHandle());
                    userService.setState(user, UserState.WAIT_CONFIRMATION_CODE_STATE);
                    producer.sendEmail(user.getEmail());
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "На почту был отправлен код подтверждения"));
                }
            }
        }catch (Exception e){
            userService.setEmail(user, null);
            userService.setCodeforcesUsername(user, null);
            userService.setState(user, UserState.WAIT_FOR_HANDLE_STATE);
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Похоже такого пользователя не существует" +
                    "на Codeforces или его электронная почта скрыта. Попробуйте еще раз!"));
        }
    }

    private void processWaitingCodeState(User user, long chatId, String code){
        try {
            if (code.equals("send_again")) {
                producer.sendEmail(user.getEmail());
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "На почту был отправлен код подтверждения"));
            } else if (code.equals("change_handle")) {
                userService.setState(user, UserState.WAIT_FOR_HANDLE_STATE);
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Введите свой хэндл с сайта Codeforces"));
            } else {
                boolean result = mailConnection.checkCode(user.getEmail(), code);
                if(result){
                    userService.setState(user, UserState.BASIC_STATE);
                    userService.setActive(user, true);
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(chatId, "Вы успешно привязали аккаунт Codeforces с хэндлом '"
                            + user.getCodeforcesUsername() + "' к чату! Теперь вы можете соревноваться с другими участниками, друзьями и " +
                            "отслеживать свой рейтинг!", List.of("Найти дуэль", "Играть с другом", "Посмотреть рейтинг"),
                            List.of("find_game", "play_with_friend", "look_rating")));
                }else{
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(chatId,
                            "Вы ввели неверный код, попробуйте снова или " +
                            "запросите повторную отправку кода нажав на кнопку", List.of("Отправить код заново", "Ввести другой хэндл"),
                            List.of("send_again", "change_handle")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void processBasicState(User user, long chatId, String text){
        if(text.equals("/start")){
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId,
                    "Привет! Это бот для дуэлей по спортивному программированию. Привяжите свой аккаунт на codeforces к этому боту, " +
                            "соревнуйтесь с друзьями и другими пользователями и повышайте свой рейтинг!"));
            return;
        }
        if(user.getIsActive()){
            if(text.equals("find_game")){
                sendBasicStateMessage(chatId, "Опция еще находится в разработке и пока недоступна, выберите другую.");
            }else if(text.equals("play_with_friend")){
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Введите хэндл оппонента с сайта codeforces"));
                userService.setState(user, UserState.WAIT_OPPONENT_HANDLE_STATE);
            }else if(text.equals("look_rating")){
                sendBasicStateMessage(chatId, "Ваш рейтинг: " + user.getRating());
            }else if(text.equals("agree_game")){
                try {
                    Invite invite = inviteService.getByRecipientId(user.getTelegramId());
                    User opponent = userService.getUserByTelegramId(invite.getSenderTelegramId());
                    sendBasicStateMessage(opponent.getTelegramId(), "Ваш соперник согласился");
                    producer.startGame(new GameDTO(user.getCodeforcesUsername(), 2, 1200000,5,
                            List.of(opponent.getCodeforcesUsername())));
                    inviteService.delete(invite.getId());
                } catch (DbDontContainObjectException e) {
                    e.printStackTrace();
                }
            }else if(text.equals("refuse_game")){
                try {
                    Invite invite = inviteService.getByRecipientId(user.getTelegramId());
                    User opponent = userService.getUserByTelegramId(invite.getSenderTelegramId());
                    userService.setState(opponent, UserState.BASIC_STATE);
                    sendBasicStateMessage(opponent.getTelegramId(), "Ваш соперник отказался от игры");
                    inviteService.delete(invite.getId());
                } catch (DbDontContainObjectException e) {
                    e.printStackTrace();
                }
            }else{
                sendBasicStateMessage(chatId, "Такой команды не существует, выберите одну из предложенных.");
            }
        }else{
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId,
                    "Похоже вы еще не привязали свой аккаунт на сайте Codeforces с этим чатом. Введите свой handle " +
                            "с сайта https://codeforces.com/ и введите код подтверждения, который придет вам на почту," +
                            "привязанную к вашему аккаунту codeforces. !! Если вы скрыли свою почту на codeforces, " +
                            "сделайте ее публичной !!"));
            userService.setState(user, UserState.WAIT_FOR_HANDLE_STATE);
        }
    }

    private void processDuringTheGameState(User user, long chatId, String text){
        if(text.equals("show_timer")) {
            //todo запрашивать из battle-service оставшееся время
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(chatId, text,
                    List.of("Показать оставшееся время"),
                    List.of("show_timer")));
        }else{
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId,
                    "Такой команды не существует, выберите одну из предложенных."));
        }
    }

    private void processOpponentHandleState(User user, long chatId, String text){
        try {
            User opponent = userService.getUserByCodeforcesUsername(text);
            if(!userService.existsByCodeforcesUsername(text) || !opponent.getIsActive()){
                userService.setState(user, UserState.BASIC_STATE);
                sendBasicStateMessage(chatId, "Указанный пользователь не зарегистрировался" +
                        "в этом боте или не привязал свой аккаунт Codeforces к боту.");
            }else {
                userService.setState(user, UserState.WAIT_OPPONENT_CONNECTION_STATE);
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(opponent.getTelegramId(),
                        "Пользователь " + user.getUsername() + " с ником на Codeforces " + user.getCodeforcesUsername() +
                        "вызвал вас на дуэль!", List.of("Согласиться", "Отказаться"), List.of("agree_game", "refuse_game")));
                inviteService.add(user.getTelegramId(), opponent.getTelegramId());
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(chatId,
                        "Ждем ответа пользователя...", List.of("Отменить"), List.of("exit")));
            }
        }catch (Exception e){
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Похоже такого пользователя не существует " +
                    "на Codeforces или его электронная почта скрыта. Попробуйте еще раз!"));
        }
    }

    private void processOpponentConnectionState(User user, long chatId, String text){
        if(text.equals("exit")){
            userService.setState(user, UserState.BASIC_STATE);
            sendBasicStateMessage(chatId, "Ожидание подключения игрока завершено!");
        }
    }

    private void sendBasicStateMessage(long chatId, String text){
        telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(chatId, text,
                List.of("Найти дуэль", "Играть с другом", "Посмотреть рейтинг"),
                List.of("find_game", "play_with_friend", "look_rating")));
    }

    private User getUser(Message message) throws DbDontContainObjectException {
        long telegramId = message.getChatId();
        String firstName = message.getFrom().getFirstName();
        String lastName = message.getFrom().getLastName();
        String username = message.getFrom().getUserName();
        if (!userService.existsByTelegramId(telegramId)) {
            userService.addUser(telegramId, firstName, lastName, username);
        }
        return userService.getUserByTelegramId(telegramId);
    }

}
