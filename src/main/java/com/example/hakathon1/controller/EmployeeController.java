package com.example.hakathon1.controller;

import com.example.hakathon1.model.Employee;
import com.example.hakathon1.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final String UPLOAD_PATH = "D:/uploads/";

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "search", required = false) String search) {
        model.addAttribute("employees", employeeService.search(search));
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        return "form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("employee") Employee employee,
                       BindingResult bindingResult,
                       Model model,
                       @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(UPLOAD_PATH);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_PATH + fileName);
            Files.copy(file.getInputStream(), path);

            employee.setAvatar(fileName);
        } else {
            if (employee.getId() != null && !employee.getId().isEmpty()) {
                Employee old = employeeService.getById(employee.getId());
                if (old != null) {
                    employee.setAvatar(old.getAvatar());
                }
            }
        }

        employeeService.save(employee);

        return "redirect:/employee";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }
}