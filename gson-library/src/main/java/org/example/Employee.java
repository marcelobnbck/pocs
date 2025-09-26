package org.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

class Employee {
    @Expose
    private String name;

    @Expose
    private int age;

    @Expose
    @SerializedName("job_title")
    private String jobTitle;

    @Expose
    private Address address;

    @Expose
    private List<String> skills;

    @Expose
    private transient double salary;

    @Expose
    private LocalDate hireDate;

    public Employee(String name, int age, String jobTitle, Address address,
                    List<String> skills, double salary, LocalDate hireDate) {
        this.name = name;
        this.age = age;
        this.jobTitle = jobTitle;
        this.address = address;
        this.skills = skills;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + '\'' +
                ", age=" + age +
                ", jobTitle='" + jobTitle + '\'' +
                ", address=" + address +
                ", skills=" + skills +
                ", salary=" + salary +
                ", hireDate=" + hireDate + '}';
    }
}
