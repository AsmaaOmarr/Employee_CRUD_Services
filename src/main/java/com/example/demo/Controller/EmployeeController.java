package com.example.demo.Controller;

import com.example.demo.Service.EmployeeServices;
import com.example.demo.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServices employeeService;

    @Autowired
    public EmployeeController(EmployeeServices employeeService) {
        this.employeeService = employeeService;
    }

    public EmployeeController(EmployeeServices employeeService, EmployeeServices employeeService1) {
        this.employeeService = employeeService1;
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/addEmployee")
    public void addEmployee(@RequestBody Employee employee) throws IOException {
        employeeService.addEmployee(employee);
    }

    @GetMapping("/searchEmployee/{idOrDesignation}")
    public List<Employee> searchEmployee(@PathVariable String idOrDesignation) {
        return employeeService.searchEmployee(idOrDesignation);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public void deleteEmployee(@PathVariable int id) throws IOException {
        employeeService.deleteEmployee(id);
    }

    @PatchMapping("/updateEmployee/{id}")
    public void updateEmployee(@PathVariable int id, @RequestParam String newDesignation) throws IOException {
        employeeService.updateEmployee(id, newDesignation);
    }


    @GetMapping("/java-experts")
    public List<Employee> getJavaExperts() {
        return employeeService.getJavaExperts();
    }

}
