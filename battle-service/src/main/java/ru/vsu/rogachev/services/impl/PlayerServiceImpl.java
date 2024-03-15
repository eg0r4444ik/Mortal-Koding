package ru.vsu.rogachev.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.rogachev.codeforces.CodeforcesConnection;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.dto.SubmissionDTO;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.enums.PlayerState;
import ru.vsu.rogachev.repositories.PlayerRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;

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
    private CodeforcesConnection codeforcesConnection;

    private SubmissionServiceImpl submissionService = new SubmissionServiceImpl();
    private ProblemServiceImpl problemService = new ProblemServiceImpl();

    public Set<ProblemDTO> getPlayerProblems(String handle) throws InterruptedException, JsonProcessingException {
        Set<ProblemDTO> res = new HashSet<>();
        List<SubmissionDTO> submissions = submissionService.getPlayerSubmissions(handle);
        for(SubmissionDTO submission : submissions){
            if(submission.getVerdict() == SubmissionDTO.Verdict.OK){
                res.add(submission.getProblem());
            }
        }

        return res;
    }

    public Set<String> getPlayerProblemSet(String handle) throws InterruptedException, JsonProcessingException {
        Set<String> res = new HashSet<>();
        Set<ProblemDTO> problems = getPlayerProblems(handle);
        for(ProblemDTO problem : problems){
            res.add(problemService.getProblemUrl(problem));
        }

        return res;
    }

    public PlayerDTO getPlayer(String handle) throws InterruptedException, JsonProcessingException {
        return codeforcesConnection.getPlayer(handle);
    }

    public void add(Player player) {
        playerRepository.save(player);
    }

    public void add(String handle, String email, long rating, PlayerState state) {
        playerRepository.save(new Player(handle, email, rating, state));
    }

    public void deleteByHandle(String handle) {
        playerRepository.deleteByHandle(handle);
    }

    public Player getByHandle(String handle) {
        return getByHandle(handle);
    }

    public void setGameSession(String handle, long id) {
        Player player = getByHandle(handle);
        player.setGame(gameSessionService.getById(id));
    }
}
