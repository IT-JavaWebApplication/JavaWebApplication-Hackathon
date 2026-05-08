package com.example.hakathon1.controller;

import com.example.hakathon1.model.Employee;
import com.example.hakathon1.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listEmployees(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("employees", employeeService.findAll(keyword));
        model.addAttribute("keyword", keyword);
        return "employee/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) return "redirect:/employees";
        model.addAttribute("employee", employee);
        return "employee/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               @RequestParam("file") MultipartFile file,
                               HttpServletRequest request,
                               Model model) {
        if (result.hasErrors()) {
            return "employee/form";
        }

        if (!file.isEmpty()) {
            try {
                String uploadsDir = "/resources/uploads/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdirs();
                }

                String orgName = file.getOriginalFilename();
                String newName = UUID.randomUUID() + "_" + orgName;
                String filePath = realPathtoUploads + newName;
                File dest = new File(filePath);
                file.transferTo(dest);

                employee.setAvatar(newName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") String id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}