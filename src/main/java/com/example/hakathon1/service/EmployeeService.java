package com.example.hakathon1.service;

import com.example.hakathon1.model.Employee;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private List<Employee> list = new ArrayList<>();

    public List<Employee> getAll() {
        return list;
    }

    public void save(Employee employee) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(employee.getId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            list.set(index, employee);
        } else {
            list.add(employee);
        }
    }

    public Employee getById(String id) {
        return list.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        list.removeIf(e -> e.getId().equals(id));
    }

    public List<Employee> search(String query) {
        if (query == null || query.isEmpty()) return list;
        String lowerQuery = query.toLowerCase();
        return list.stream()
                .filter(e -> e.getFullName().toLowerCase().contains(lowerQuery) ||
                        e.getPosition().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
}