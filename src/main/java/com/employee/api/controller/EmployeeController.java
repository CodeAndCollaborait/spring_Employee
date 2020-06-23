package com.employee.api.controller;

import com.employee.api.exception.ResouceNotFoundException;
import com.employee.api.model.Employee;
import com.employee.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

  @Autowired // Spring Dependency Injection
  EmployeeRepository repository;

  @GetMapping("/")
  public String wellcome() {
    return "Hello from Employee API";
  }

  // List of Employees
  @GetMapping("/employees") // list of all employees
  public List<Employee> getEmployees() {
    return repository.findAll();
  }

  // Employee by id..
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long id)
      throws ResouceNotFoundException {
    // provide employee by id.
    // provide error with information if id does not ...have id.

    Employee employee =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResouceNotFoundException("Employee by this id number is not found " + id));

    return ResponseEntity.ok().body(employee);
  }

  // Create or Add new Employee
  // POST method
  @PostMapping("/employees")
  public Employee saveEmployee(@RequestBody Employee employee) {
    return repository.save(employee);
  }

  // Delete or Remove the employee
  @DeleteMapping("/employees/{id}")
  public Map<String, String> deleteEmployee(@PathVariable(value = "id") long id)
      throws ResouceNotFoundException {
    // Read the db and find the id first.. then delete it
    Employee employee =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResouceNotFoundException("Employee by this id number is not found " + id));

    repository.delete(employee);
    Map<String, String> response = new HashMap<>();
    response.put("Employee is Deleted", "Requested Employee removed " + id);
    return response;
  }

  // Update the Employee Information

  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(
      @PathVariable(value = "id") long id, @RequestBody Employee empDetails)
      throws ResouceNotFoundException {
    // find the emp by id..
    Employee employee =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResouceNotFoundException("Employee by this id number is not found " + id));

    // Update the emp..
    employee.setEmailId(empDetails.getEmailId());
    employee.setLastName(empDetails.getLastName());
    employee.setFirstName(empDetails.getFirstName());
    final Employee updatedEmployee = repository.save(employee);

    return ResponseEntity.ok(updatedEmployee);
  }
}
