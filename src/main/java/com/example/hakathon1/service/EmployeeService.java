package com.example.hakathon1.service;

import com.example.hakathon1.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();
    private int idCounter = 1;

    public List<Employee> findAll(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employees;
        }
        String lowerKeyword = keyword.toLowerCase();
        return employees.stream()
                .filter(e -> e.getFullName().toLowerCase().contains(lowerKeyword) ||
                        e.getPosition().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public Employee findById(String id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    public void save(Employee employee) {
        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId("NV" + String.format("%03d", idCounter++));
            employees.add(employee);
        } else {
            Employee existing = findById(employee.getId());
            if (existing != null) {
                existing.setFullName(employee.getFullName());
                existing.setPosition(employee.getPosition());
                existing.setSalary(employee.getSalary());
                if (employee.getAvatar() != null && !employee.getAvatar().isEmpty()) {
                    existing.setAvatar(employee.getAvatar());
                }
            }
        }
    }

    public void delete(String id) {
        employees.removeIf(e -> e.getId().equals(id));
    }
}