package training.project.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import training.project.dto.OperationDto;
import training.project.services.OperationService;

@Controller
@AllArgsConstructor
public class OperationContr {

    private OperationService operationService;

    @PreAuthorize("@mySecurityService.hasScoreUserPermission(authentication, #scoreId)")
    @GetMapping(value = "/{score}/operations")
    public String getOperationsByScore(Model model, @PathVariable("score") Long scoreId,
                                       RedirectAttributes redirectAttributes) {

        return operationService.getOperationsByScore(model, scoreId, redirectAttributes);
    }

    @PreAuthorize("@mySecurityService.hasScoreUserPermission(authentication, #scoreId)")
    @GetMapping(value = "/{score}/operation")
    public String getNewOperation(Model model, @PathVariable("score") Long scoreId,
                                  @RequestParam(name = "error", required = false) String error,
                                  RedirectAttributes redirectAttributes) {

        return operationService.getNewOperation(model, scoreId, error, redirectAttributes);
    }

    @PreAuthorize("@mySecurityService.hasScoreUserPermission(authentication, #scoreId)")
    @PostMapping(value = "/{score}/operation")
    public String saveOperation(@Valid @ModelAttribute OperationDto operation, BindingResult bindingResult,
                                @PathVariable("score") Long scoreId,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", getErrors(bindingResult));
            redirectAttributes.addAttribute("score", scoreId);
            return "redirect:/{score}/operation";
        }

        return operationService.saveOperation(operation, scoreId, redirectAttributes);
    }

    private List<String> getErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

}