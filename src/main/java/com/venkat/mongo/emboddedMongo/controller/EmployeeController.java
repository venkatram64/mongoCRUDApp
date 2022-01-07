package com.venkat.mongo.emboddedMongo.controller;

import com.venkat.mongo.emboddedMongo.model.Employee;
import com.venkat.mongo.emboddedMongo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public List<Employee> getAll(){
        return employeeService.getAll();
    }

    @PostMapping("/")
    public String createEmp(@RequestBody Employee emp){
        return employeeService.createEmp(emp);
    }

    @PutMapping("/")
    public String updateEmp(@RequestBody Employee emp){
        return employeeService.updateEmp(emp);
    }
    //localhost:8080/emp/?id=61d583ef45d53a1b5b8e2237
    @DeleteMapping("/")
    public String deleteEmpById(@PathParam("id") String id){
        return employeeService.deleteEmpById(id);
    }
}
