package org.example;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class DataHandler {

    Set<String[]> incorrectData = new HashSet<>();
    Set<Employee> correctData = new HashSet<>();

    public void processData(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length != 5) {
                addToIncorrectData(data);
                continue;
            }
            switch (data[0]) {
                case "Manager":
                    processManager(data);
                    break;
                case "Employee":
                    processEmployee(data);
                    break;
                default:
                    addToIncorrectData(data);
            }
        }
        findManager(Employee.getEmployees());
    }

    private void processManager(String[] line) {
        int id = Integer.parseInt(line[1].trim());
        String name = line[2].trim();
        double salary = 0;
        try {
            salary = Double.parseDouble(line[3].trim());
            if (salary <= 0) {
                incorrectData.add(line);
                return;
            }
        } catch (NumberFormatException e) {
            incorrectData.add(line);
            return;
        }
        String departmentName = line[4].trim();
        Department department = Department.getDepartment(departmentName);
        if (department == null) {
            department = new Department(departmentName);
        }
        Manager manager = new Manager(id, name, salary, department);
        if (Manager.addNewManager(manager)) {
            correctData.add(manager);
            department.setDepartmentManager(manager);
        } else {
            incorrectData.add(line);
        }
    }

    private void processEmployee(String[] line) {
        int id = Integer.parseInt(line[1].trim());
        String name = line[2].trim();
        double salary = 0;
        int managerId;
        try {
            salary = Double.parseDouble(line[3].trim());
            if (salary <= 0) {
                incorrectData.add(line);
                return;
            }
        } catch (NumberFormatException e) {
            incorrectData.add(line);
            return;
        }
        try {
            managerId = Integer.parseInt(line[4].trim());
        } catch (NumberFormatException e) {
            incorrectData.add(line);
            return;
        }
        Employee employee = new Employee(id, name, salary, managerId);
        if (Employee.addNewEmployee(employee)) {
            correctData.add(employee);
        } else {
            incorrectData.add(line);
        }
    }

    private boolean addToIncorrectData(String[] employee) {
        return incorrectData.add(employee);
    }



    public String getIncorrectData() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Некорректные данные: ").append("\n");
        if (!incorrectData.isEmpty()) {
            for (String[] employee : incorrectData) {
                for (int i = 0; i < employee.length; i++) {
                    sb.append(employee[i]);
                    if (i < employee.length - 1) { // Добавляем запятую, если это не последний элемент
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        }  else sb.append("Отсутствуют").append("\n");
        return sb.toString();

    }

    private void findManager(Map<Integer, Employee> employees) {
        Map<Integer, Manager> managers = Manager.getManagers();
        Iterator<Map.Entry<Integer, Employee>> it = employees.entrySet().iterator();
        while (it.hasNext()) {
            Employee employee = it.next().getValue();
            int employeeManagerId = employee.managerId;
            Manager manager = managers.get(employeeManagerId);
            if (manager == null) {
                correctData.remove(employee);
                incorrectData.add(employee.toStringArray());
            } else {
                Department department = manager.getDepartment();
                department.addNewEmployee(employee);
            }
        }
    }

    public String getFinalData() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Department> departments = Department.getDepartments();
        departments.sort((o1, o2) -> o1.name.compareTo(o2.name));
        for (Department department : departments) {
            List<Employee> employees = department.getEmployees();
            sb.append(department.name).append("\n");
            sb.append(department.departmentManager).append("\n");
            for (Employee employee : employees) {
                sb.append(employee.toString()).append("\n");
            }
            DecimalFormat df = new DecimalFormat("#0.00");
            df.setDecimalSeparatorAlwaysShown(true);
            Double salary = department.getAverageSalary();
            sb.append(department.getEmployeeCount()).append(", ").append(df.format(salary).replace(',', '.')).append("\n");
        }
        sb.append(getIncorrectData()).append("\n");
        return sb.toString();
    }

    public void consoleOutput() {
        System.out.println(getFinalData());
    }

    public void fileOutput(String outputFile) throws IOException {
        File file = new File(outputFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(getFinalData());

        writer.flush();
    }
}
