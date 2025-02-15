package org.example;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Employee {
    static Map<Integer, Employee> employees = new HashMap<>();
    int id;
    String name;
    double salary;
    int managerId;

    Employee() {
    }

    Employee(int id, String name, double salary, int managerId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.managerId = managerId;
    }

    public static Map<Integer, Employee> getEmployees() {
        return employees;
    }


    public static boolean addNewEmployee(Employee newEmployee) {
        if (employees.containsKey(newEmployee.id)) {
            return false;
        }
        if(Manager.getManagers().containsKey(newEmployee.id)) {
            return false;
        }
        employees.put(newEmployee.id, newEmployee);
        return true;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setDecimalSeparatorAlwaysShown(true);
        return "Employee," + id + ", " + name + ", " + df.format(salary).replace(',', '.');    }


    public String[] toStringArray() {
        return new String[] {"Employee,", String.valueOf(id), name, String.valueOf(salary), String.valueOf(managerId) };
    }
}
