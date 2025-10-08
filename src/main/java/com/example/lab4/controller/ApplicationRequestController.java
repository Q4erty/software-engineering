package com.example.lab4.controller;

import com.example.lab4.entity.ApplicationRequest;
import com.example.lab4.service.ApplicationRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/requests")
public class ApplicationRequestController {

    private final ApplicationRequestService service;

    public ApplicationRequestController(ApplicationRequestService service) {
        this.service = service;
    }

    @GetMapping
    public String allRequests(Model model) {
        model.addAttribute("requests", service.getAll());
        return "requests/list";
    }

    @GetMapping("/new")
    public String newRequestForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        return "requests/new";
    }

    @PostMapping
    public String createRequest(@ModelAttribute ApplicationRequest request) {
        service.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        model.addAttribute("request", service.getById(id));
        return "requests/detail";
    }

    @PostMapping("/{id}/process")
    public String processRequest(@PathVariable Long id) {
        ApplicationRequest req = service.getById(id);
        req.setHandled(true);
        service.save(req);
        return "redirect:/requests/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteRequest(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/requests";
    }

    @GetMapping("/handled")
    public String handledRequests(Model model) {
        model.addAttribute("requests", service.getByHandled(true));
        return "requests/list";
    }

    @GetMapping("/pending")
    public String pendingRequests(Model model) {
        model.addAttribute("requests", service.getByHandled(false));
        return "requests/list";
    }
}
