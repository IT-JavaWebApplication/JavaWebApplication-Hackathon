package com.example.hakathon1.model;

import jakarta.validation.constraints.*;

public class Employee {
    private String id;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 5, max = 50, message = "Họ tên phải từ 5 đến 50 ký tự")
    private String fullName;

    @NotBlank(message = "Vị trí không được để trống")
    private String position;

    @NotNull(message = "Mức lương không được để trống")
    @Positive(message = "Mức lương phải lớn hơn 0")
    private Double salary;

    private String avatar;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}