package com.venkat.mongo.emboddedMongo.service;

import com.venkat.mongo.emboddedMongo.model.Employee;
import com.venkat.mongo.emboddedMongo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll() {
        return repository.getAll();
    }

    public String createEmp(Employee emp) {
        return repository.save(emp);
    }

    public String updateEmp(Employee emp) {
        return repository.update(emp);
    }

    public String deleteEmpById(String id) {
        return repository.delete(id);
    }
}
