package com.example.loginregister.Controllers;

import com.example.loginregister.Service.EmployeeService;
import com.example.loginregister.model.Employee;
import com.example.loginregister.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class AppController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/log-in")
    public String logIn(){

        return "login";
    }

   @GetMapping("/home")
    public String getHomepage(){

        return "home";
   }
   @GetMapping("/register_employee")
    public String showEmployeeForm(Model model){
      model.addAttribute("employee",new Employee());
      List<Role>listRoles = service.listRoles();
      model.addAttribute("listRoles", listRoles);
       return "registration";
   }


    @PostMapping("/save/employee")
    public String employeeCreate(Employee employee){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        service.saveEmployee(employee);
       return "redirect:/employees";
    }
}
