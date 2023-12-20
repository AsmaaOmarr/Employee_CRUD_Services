package com.example.demo.Service;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Language;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServices {
    private static final String JSON_FILE_PATH = "employees.json";
    private static JSONArray employeeArray;

    public EmployeeServices() throws IOException {
        loadEmployees();
    }

    private void loadEmployees() throws IOException {
        File file = new File(JSON_FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            employeeArray = new JSONArray();
            saveEmployees();
        } else {
            String jsonContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            employeeArray = new JSONArray(jsonContent);
        }
    }

    private void saveEmployees() {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH)) {
            employeeArray.write(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(employeeArray.toString());
            // Iterate through array elements
            if (jsonNode.isArray()) {
                for (JsonNode element : jsonNode) {
                    String firstName = element.get("firstName").asText();
                    String lastName = element.get("lastName").asText();
                    int id = element.get("employeeID").asInt();
                    String designation = element.get("designation").asText();

                    List<Language> languages = new ArrayList<>();
                    JsonNode languageNode = element.get("knownLanguages");
                    if (languageNode != null && languageNode.isArray()) {
                        for (JsonNode language : languageNode) {
                            String languageName = language.get("languageName").asText();
                            int scoreOutof100 = language.get("scoreOutof100").asInt();
                            languages.add(new Language(languageName, scoreOutof100));
                        }
                    }
                    Employee newEmployee = new Employee(firstName,lastName,id,designation,languages);
                    allEmployees.add(newEmployee);
                }
            }
            return allEmployees;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return null;
        }
    }

    public void addEmployee(Employee employee) throws IOException {
        JSONObject employeeJson = new JSONObject(employee);
        employeeArray.put(employeeJson);
        saveEmployees();
    }

    public List<Employee> searchEmployee(String idOrDesignation) {
        List<Employee> searchResults = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(employeeArray.toString());
            // Iterate through array elements
            if (jsonNode.isArray()) {
                for (JsonNode element : jsonNode) {
                    int id = element.get("employeeID").asInt();
                    String designation = element.get("designation").asText();
                    if (String.valueOf(id).equals(idOrDesignation)
                            || designation.equalsIgnoreCase(idOrDesignation)) {
                        String firstName = element.get("firstName").asText();
                        String lastName = element.get("lastName").asText();
                        List<Language> languages = new ArrayList<>();
                        JsonNode languageNode = element.get("knownLanguages");
                        if (languageNode != null && languageNode.isArray()) {
                            for (JsonNode language : languageNode) {
                                String languageName = language.get("languageName").asText();
                                int scoreOutof100 = language.get("scoreOutof100").asInt();
                                languages.add(new Language(languageName, scoreOutof100));
                            }
                        }
                        Employee newEmployee = new Employee(firstName,lastName,id,designation,languages);
                        searchResults.add(newEmployee);
                    }
                }
            }
            return searchResults;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return null;
        }
    }

    public void deleteEmployee(int id) throws IOException {
        int indexToRemove = -1;
        for (int i = 0; i < employeeArray.length(); i++) {
            JSONObject employeeJson = employeeArray.getJSONObject(i);
            int employeeId = employeeJson.getInt("employeeID");
            if (employeeId == id) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove >= 0) {
            employeeArray.remove(indexToRemove);
            saveEmployees();
        }
    }

    public void updateEmployee(int id, String newDesignation) throws IOException {
        for (int i = 0; i < employeeArray.length(); i++) {
            JSONObject employeeJson = employeeArray.getJSONObject(i);
            int employeeId = employeeJson.getInt("employeeID");
            if (employeeId == id) {
                employeeJson.put("designation", newDesignation);
                saveEmployees();
                break;
            }
        }
    }

    public List<Employee> getJavaExperts() {
        // Step 1: Convert JSON to Employee Objects
        List<Employee> allEmployees = employeeArray.toList().stream()
                .map(json -> new ObjectMapper().convertValue(json, Employee.class))
                .collect(Collectors.toList());
    
        // Step 2: Filter Java Experts
        List<Employee> javaExperts = allEmployees.stream()
                .filter(employee -> employee.getKnownLanguages().stream()
                        .anyMatch(language -> "Java".equalsIgnoreCase(language.getLanguageName())
                                && language.getScoreOutof100() > 50))
                .collect(Collectors.toList());
    
        // Step 3: Sort by Java Expertise
        javaExperts.sort(Comparator.comparing(employee -> {
            Optional<Language> javaLanguage = employee.getKnownLanguages().stream()
                    .filter(language -> "Java".equalsIgnoreCase(language.getLanguageName()))
                    .findFirst();
            return javaLanguage.orElse(new Language("", 0)).getScoreOutof100();
        }));
        // Step 4: Return the Result
        return javaExperts;
    }
    
}
