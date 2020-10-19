package training.project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import training.project.domain.Score;
import training.project.dto.OperationDto;
import training.project.enums.Category;
import training.project.mapper.OperationMapper;
import training.project.repository.OperationRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CalculationService {

    private final OperationRepository operationRepository;

    private final OperationMapper operationMapper;

    public String makeCalculations(OperationDto operation, Score score, RedirectAttributes redirectAttributes) {

        if (operation.getCategory().equals(Category.ABC)) {
            score.setBalance(score.getBalance() + operation.getAmount());
            operation.setCreated(LocalDateTime.now());
            operation.setScore(score);
            operationRepository.save(operationMapper.toOperation(operation));

            return "redirect:/room";

        } else if (score.getBalance() < operation.getAmount() || score.getLimit() < operation.getAmount()) {
            redirectAttributes.addAttribute("error", "limit!!!");
            return "redirect:/{score}/operation";
        }

        score.setBalance(score.getBalance() - operation.getAmount());
        score.setLimit(score.getLimit() - operation.getAmount());
        operation.setCreated(LocalDateTime.now());
        operation.setScore(score);
        operationRepository.save(operationMapper.toOperation(operation));

        return "redirect:/room";
    }
}