package org.example;

import java.text.DecimalFormat;
import java.util.*;

public class Manager extends Employee {
    static Map<Integer, Manager> managers = new HashMap<>();
    Department department;

    Manager() {}

    Manager(int id, String name, double salary, Department department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setDecimalSeparatorAlwaysShown(true);
        return  "Manager," +id + ", " + name + ", " + df.format(salary).replace(',', '.');
    }

    public String[] toStringArray() {
        return new String[] {"Manager,", String.valueOf(id), name, String.valueOf(salary), String.valueOf(department.name) };
    }
    public static boolean addNewManager(Manager manager) {
        if (managers.containsKey(manager.id)) {
            return false;
        }
        if(Employee.getEmployees().containsKey(manager.id)) {
            return false;
        }
        managers.put(manager.id, manager);
        return true;
    }

    public static Map<Integer, Manager> getManagers() {
        return managers;
    }


    public Department getDepartment() {
        return department;
    }

}
