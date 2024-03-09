package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.rogachev.Game;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.repositories.PlayerRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;
import ru.vsu.rogachev.services.WaitingGameService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private WaitingGameService waitingGameService;

    private SubmissionServiceImpl submissionService = new SubmissionServiceImpl();
    private ProblemServiceImpl problemService = new ProblemServiceImpl();

    public Set<Problem> getUserProblems(String handle) throws InterruptedException {
        Set<Problem> res = new HashSet<>();
        List<Submission> submissions = submissionService.getUserSubmissions(handle);
        for(Submission submission : submissions){
            if(submission.getVerdict() == Submission.Verdict.OK){
                res.add(submission.getProblem());
            }
        }

        return res;
    }

    public Set<String> getUserProblemSet(String handle) throws InterruptedException {
        Set<String> res = new HashSet<>();
        Set<Problem> problems = getUserProblems(handle);
        for(Problem problem : problems){
            res.add(problemService.getProblemUrl(problem));
        }

        return res;
    }

    public Player getUser(String handle) throws InterruptedException {
        return connectionManager.getUser(handle);
    }

    public void add(Player player) {
        playerRepository.save(player);
    }

    public void add(String handle, String email, long rating) {
        playerRepository.save(new Player(handle, email, rating));
    }

    public void deleteByHandle(String handle) {
        playerRepository.deleteByHandle(handle);
    }

    public Player getByHandle(String handle) {
        return getByHandle(handle);
    }

    public void setWaitingGame(String handle, long id) {
        Player player = getByHandle(handle);
        player.setWaitingGame(waitingGameService.getById(id));
    }

    public void setGameSession(String handle, long id) {
        Player player = getByHandle(handle);
        player.setGame(gameSessionService.getById(id));
    }
}
