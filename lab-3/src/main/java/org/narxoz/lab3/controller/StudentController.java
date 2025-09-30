package org.narxoz.lab3.controller;

import lombok.RequiredArgsConstructor;
import org.narxoz.lab3.entity.Student;
import org.narxoz.lab3.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", service.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        if (student.getExam() < 0) student.setExam(0);
        if (student.getExam() > 100) student.setExam(100);

        service.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        service.findById(id).ifPresent(st -> model.addAttribute("student", st));
        return "students/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/students";
    }
}
