package org.narxoz.lab3part2.controller;

import lombok.RequiredArgsConstructor;
import org.narxoz.lab3part2.entity.Task;
import org.narxoz.lab3part2.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Task task) {
        service.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        service.findById(id).ifPresent(t -> model.addAttribute("task", t));
        return "tasks/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        service.findById(id).ifPresent(t -> model.addAttribute("task", t));
        return "tasks/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/tasks";
    }
}

