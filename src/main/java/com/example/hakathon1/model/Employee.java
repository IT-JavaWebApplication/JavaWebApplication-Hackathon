package com.example.hakathon1.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Employee {
    private String id;
    private String fullName;
    private String position;
    private Double salary;
    private String avatar;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    private String name;

    @NotBlank(message = "Email không được để trống")
    private String email;
    public Employee() {}

    public Employee(String id, String fullName, String position, Double salary, String avatar) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.salary = salary;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
