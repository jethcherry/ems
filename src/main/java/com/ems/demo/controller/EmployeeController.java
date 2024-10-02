package com.ems.demo.controller;

import com.ems.demo.entity.Employee;
import com.ems.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository eRepo;

    @GetMapping("/addEmployeeForm")
    public ModelAndView addEmployeeForm() {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Employee newEmployee = new Employee();
        mav.addObject("employee", newEmployee);
        return mav;
    }

    @GetMapping({"/getEmployees", "/", "/list"})
    public ModelAndView showEmployees() {
        ModelAndView mav = new ModelAndView("list-employees");
        List<Employee> list = eRepo.findAll();
        mav.addObject("employees", list);
        return mav;
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute @Validated Employee employee) {
        eRepo.save(employee);
        return "redirect:/list"; // Redirect to the list after saving
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam UUID employeeId) {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Employee employee = eRepo.findById(employeeId).get();
        mav.addObject("employee", employee);
        return mav;

    }

    @GetMapping("deleteEmployee")
    public String deleteEmployee(@RequestParam UUID employeeId){
        eRepo.deleteById(employeeId);
        return "redirect:/list";
    }


    // Uncomment and use this method if you want to add employees via JSON API
    /*
    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@Validated @RequestBody Employee employee) {
        try {
            Employee savedEmployee = eRepo.save(employee);
            UUID employeeId = savedEmployee.getId();
            return ResponseEntity.ok("Employee was added successfully with Id: " + employeeId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding employee: " + e.getMessage());
        }
    }
    */
}
