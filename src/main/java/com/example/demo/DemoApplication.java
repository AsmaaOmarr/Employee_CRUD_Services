package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.*;
import com.example.demo.Controller.EmployeeController;
import com.example.demo.Service.EmployeeServices;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.example.demo.Model.Employee;
import com.example.demo.Model.Language;



@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		try {
			EmployeeServices employeeServices = new EmployeeServices();
			EmployeeController employeeController = new EmployeeController(employeeServices);

			//2. Add a new employee
			Employee newEmployee = new Employee(
			"Sam","Jackson",
			1000,"Manager",
			List.of(new Language("Java", 90), new Language("C#", 50), new
			Language("c++",80))
			);
			Employee newEmployee1 = new Employee(
			"John","Adamo",
			2000,"Developer",
			List.of(new Language("Prel", 30), new Language("Java", 65), new
			Language("c++",70))
			);
			employeeServices.addEmployee(newEmployee);
			employeeServices.addEmployee(newEmployee1);

			//  //Get all employees
			//  List<Employee> allEmployees = employeeServices.getAllEmployees();
			//  System.out.println("All Employees:");
			//  for (Employee employee : allEmployees) {
			// 	 System.out.println(employee.getFirstName());
			//  }
 
			//  //Search for an employee
			//  String searchQuery = "Manager";
			//  List<Employee> searchResults = employeeServices.searchEmployee(searchQuery);
			//  System.out.println("Search Results for '" + searchQuery + "':");
			//  for (Employee employee : searchResults) {
			// 	 System.out.println(employee.getFirstName());
			//  }
 
			//  // Delete an employee
			//  int employeeIdToDelete = 2000;
			//  employeeServices.deleteEmployee(employeeIdToDelete);
			//  System.out.println("Employee with ID " + employeeIdToDelete + " deleted.");
 
			//  // Update an employee
			//  int employeeIdToUpdate = 1000;
			//  String newDesignation = "Senior Manager";
			//  employeeServices.updateEmployee(employeeIdToUpdate, newDesignation);
			//  System.out.println("Employee with ID " + employeeIdToUpdate + " updated. New designation: " + newDesignation);
 
			//  // Get Java experts
			 List<Employee> javaExperts = employeeServices.getJavaExperts();
			 System.out.println("Java Experts:");
			 for (Employee employee : javaExperts) {
				 System.out.println(employee.getFirstName());
				}

			//System.out.println("asmaa");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

