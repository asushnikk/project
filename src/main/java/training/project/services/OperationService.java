package training.project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import training.project.domain.Operation;
import training.project.domain.Score;
import training.project.dto.OperationDto;
import training.project.enums.Category;
import training.project.repository.ScoreRepository;

@Service
@AllArgsConstructor
public class OperationService {

    private final ScoreRepository scoreRepository;
    private final CalculationService calculationService;

    public String getOperationsByScore(Model model, Long scoreId, RedirectAttributes redirectAttributes) {

        Score score = scoreRepository.getOne(scoreId);

        model.addAttribute("operations", score.getOperations());
        return "operations";
    }

    public String getNewOperation(Model model, Long scoreId, String error, RedirectAttributes redirectAttributes) {
        model.addAttribute("error", error);

        model.addAttribute("scoreId", scoreId);
        model.addAttribute("operation", new Operation());
        model.addAttribute("categories", Category.values());

        return "operation";
    }

    public String saveOperation(OperationDto operation, Long scoreId, RedirectAttributes redirectAttributes) {

        return calculationService.makeCalculations(operation, scoreRepository.getOne(scoreId), redirectAttributes);
    }
}