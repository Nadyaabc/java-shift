package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String outputMode = "console";
        String outputFile = "";
        String inputFile = "input.txt";
        String sortBy = null;
        String order = "";
        Map<String, String> arguments = new HashMap<>();

        for (String arg : args) {
            if (arg.contains("=")) {
                String[] parts = arg.split("=", 2);
                arguments.put(parts[0], parts[1]);
            } else {
                arguments.put(arg, "");
            }
        }

        if (arguments.containsKey("--output") || arguments.containsKey("-o")) {
            outputMode = arguments.getOrDefault("--output", arguments.getOrDefault("-o", "console"));
        }
        if (arguments.containsKey("--input")) {
            inputFile = arguments.get("--input");
        }
        if (arguments.containsKey("--path")) {
            outputFile = arguments.get("--path");
        }

        if (arguments.containsKey("--sort") || arguments.containsKey("-s")) {
            sortBy = arguments.getOrDefault("--sort", arguments.getOrDefault("-s", null));
        }

        if (arguments.containsKey("--order") || arguments.containsKey("-o")) {
            order = arguments.getOrDefault("--order", arguments.getOrDefault("-o", null));
        }

        if (order != null && sortBy == null) {
            System.out.println("Ошибка: Необходимо указать параметр сортировки (--sort или -s).");
            return;
        }

        if ("file".equals(outputMode) && (outputFile == null || outputFile.isEmpty())) {
            System.err.println("Ошибка: Нужно указать путь к файлу при использовании --output=file.");
            return;
        }

        DataHandler dataHandler = new DataHandler();

        try {
            dataHandler.processData("input.txt");

            if (sortBy != null) {
                if ("name".equals(sortBy)) {
                    if ("asc".equals(order)) {
                        for (Department department : Department.getDepartments()) {
                            department.sortEmployeeByNameAsc();
                        }
                    } else {
                        for (Department department : Department.getDepartments()) {
                            department.sortEmployeeByNameDesc();
                        }

                    }
                } else if ("salary".equals(sortBy)) {
                    if ("asc".equals(order)) {
                        for (Department department : Department.getDepartments()) {
                            department.sortEmployeeBySalaryAsc();
                        }
                    } else {
                        for (Department department : Department.getDepartments()) {
                            department.sortEmployeeBySalaryDesc();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
        }

        try {
            if (outputMode.equals("console")) {
                dataHandler.consoleOutput();
            } else {
                dataHandler.fileOutput(outputFile);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при выводе данных: " + e.getMessage());
        }
    }
}