package com.example.reactcrud.controller;

import com.example.reactcrud.exception.ResourceNotFound;
import com.example.reactcrud.model.Employee;
import com.example.reactcrud.repository.EmployeeRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

//    get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFound("Not found"));
        return  ResponseEntity.ok(employee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee updateEmp){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFound("Not found"));

        employee.setFirstName(updateEmp.getFirstName());
        employee.setLastName(updateEmp.getLastName());
        employee.setEmail(updateEmp.getEmail());
        Employee ue = employeeRepository.save(employee);
        return  ResponseEntity.ok(ue);

    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity< Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFound("Not found"));
        employeeRepository.delete(employee);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",true);
        return ResponseEntity.ok(response);
    }

}
