package training.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import training.project.dto.ScoreDto;
import training.project.services.ScoreService;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PreAuthorize("@mySecurityService.hasScoreOwnerPermission(principal, #scoreId)")
    @RequestMapping(path = "score/{score}/delete")
    public String deleteScore(@PathVariable("score") Long scoreId) {

        return scoreService.deleteScore(scoreId);
    }

    @GetMapping(path = "/score")
    public String getNewScore(Model model, @RequestParam(name = "error", required = false) String error) {

        return scoreService.getNewScore(model, error);
    }

    @PostMapping(path = "/score")
    public String createScore(@Valid @ModelAttribute("score") ScoreDto scoreDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", getErrors(bindingResult));
            return "redirect:/score";
        }

        return scoreService.createScore(scoreDto);
    }

    @PreAuthorize("@mySecurityService.hasScoreOwnerPermission(principal, #scoreId)")
    @GetMapping(path = "score/{score}")
    public String findScore(@PathVariable("score") Long scoreId, Model model,
                             @RequestParam(required = false) String error) {

        return scoreService.findScore(scoreId, model, error);
    }

    @PreAuthorize("@mySecurityService.hasScoreOwnerPermission(principal, #scoreId)")
    @PostMapping(path = "score/{score}/edit")
    public String editScore(@Valid @ModelAttribute("score") ScoreDto scoreDto, BindingResult bindingResult,
                             @PathVariable("score") Long scoreId,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", getErrors(bindingResult));
            redirectAttributes.addAttribute("scoreId", scoreId);
            return "redirect:/score/{scoreId}";
        }

        return scoreService.editScore(scoreDto, scoreId);
    }

    private List<String> getErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}