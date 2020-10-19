package training.project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import training.project.domain.Score;
import training.project.domain.User;
import training.project.dto.ScoreDto;
import training.project.mapper.ScoreMapper;
import training.project.repository.ScoreRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserService userService;
    private final ScoreMapper scoreMapper;

    public String deleteScore(Long scoreId) {
        scoreRepository.deleteById(scoreId);
        return "redirect:/room";
    }

    public String getNewScore(Model model, String error) {
        model.addAttribute("score", new Score());
        model.addAttribute("error", error);

        return "score";
    }

    public String createScore(ScoreDto scoreDto) {

        User user = userService.getCurrentUser();

        Score score = scoreMapper.toScoreWithOwner(scoreDto, user);
        scoreRepository.save(score);

        return "redirect:/room";
    }

    public String findScore(Long scoreId, Model model, String error) {

        model.addAttribute("error", error);
        Optional<Score> score = scoreRepository.findById(scoreId);
        model.addAttribute("score", score.get());

        return "scoreEdit";
    }

    public String editScore(ScoreDto scoreDto, Long scoreId) {

        User user = userService.getCurrentUser();
        scoreRepository.save(scoreMapper.toScoreWithOwnerAndId(scoreDto, user, scoreId));

        return "redirect:/room";
    }
}