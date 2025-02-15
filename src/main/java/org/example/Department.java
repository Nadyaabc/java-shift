package org.example;

import java.util.ArrayList;
import java.util.List;

public class Department {
    static ArrayList<Department> departments = new ArrayList<Department>();
    String name;
    List<Employee> employees = new ArrayList<>();
    Manager departmentManager;

    public Department(String name) {
        this.name = name;
        departments.add(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean checkIfExists(String name) {
        for (Department department : departments) {
            if (department.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Department getDepartment(String name) {
        for (Department department : departments) {
            if (department.name.equals(name)) {
                return department;
            }
        }
        return null;
    }

    public void addNewEmployee(Employee employee) {
        employees.add(employee);
    }

    public void sortEmployeeByNameAsc() {
        employees.sort((o1, o2) -> o1.name.toLowerCase().compareTo(o2.name.toLowerCase()));

    }

    public void sortEmployeeByNameDesc() {
        employees.sort((o1, o2) -> o2.name.toLowerCase().compareTo(o1.name.toLowerCase()));
    }

    public void sortEmployeeBySalaryAsc() {
        employees.sort((o1, o2) -> Double.compare(o1.salary, o2.salary));
    }

    public void sortEmployeeBySalaryDesc() {
        employees.sort(((o1, o2) -> Double.compare(o2.salary, o1.salary)));
    }

    public static ArrayList<Department> getDepartments() {
        return departments;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setDepartmentManager(Manager departmentManager) {
        this.departmentManager = departmentManager;
    }

    public double getAverageSalary() {
        double sum = 0;
        int count = 1;
        for (Employee employee : employees) {
            sum += employee.salary;
            count++;
        }
        sum += departmentManager.salary;
        double average = sum / count;
        return Math.round(average * 100.0) / 100.0;
    }

    public int getEmployeeCount() {
        return employees.size() + 1;
    }
}
