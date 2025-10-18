package org.narxoz.lab5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public String listRequests(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "list";
    }

    @GetMapping("/new")
    public String newRequestForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        model.addAttribute("courses", requestService.getAllCourses());
        return "form";
    }

    @PostMapping
    public String createRequest(@ModelAttribute ApplicationRequest request, @RequestParam Long courseId) {
        requestService.createRequest(request, courseId);
        return "redirect:/requests";
    }

    @GetMapping("/{id}/process")
    public String processRequestForm(@PathVariable Long id, Model model) {
        ApplicationRequest request = requestService.getRequest(id);
        if (!request.isHandled()) {
            model.addAttribute("request", request);
            model.addAttribute("operators", requestService.getAllOperators());
            return "process";
        }
        return "redirect:/requests";
    }

    @PostMapping("/{id}/assign")
    public String assignOperators(@PathVariable Long id, @RequestParam List<Long> operatorIds) {
        requestService.assignOperators(id, operatorIds);
        return "redirect:/requests";
    }

    @GetMapping("/{id}/operators")
    public String viewOperators(@PathVariable Long id, Model model) {
        model.addAttribute("request", requestService.getRequest(id));
        return "operators";
    }

    @PostMapping("/{requestId}/operators/{operatorId}/delete")
    public String deleteOperator(@PathVariable Long requestId, @PathVariable Long operatorId) {
        requestService.deleteOperatorFromRequest(requestId, operatorId);
        return "redirect:/requests/" + requestId + "/operators";
    }
}
